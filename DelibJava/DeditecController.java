package DelibJava;

public class DeditecController {
    private int handle;

    public DeditecController() throws Exception {
        // Modul öffnen (Beispiel: BS-USB2, Modul-ID 0x002D)
        handle = DelibJNI64.DapiOpenModule(0x002D, 0);
        if (handle <= 0) {
            throw new Exception("Modul konnte nicht geöffnet werden, Fehlercode: " + DelibJNI64.DapiGetLastError());
        }
    }

    public void close() {
        DelibJNI64.DapiCloseModule(handle);
    }

    // Liefert die Anzahl der Digitalausgänge zurück
    public int getDigitalOutputConfig() throws Exception {
        int config = DelibJNI64.DapiSpecialCommand(
            handle,
            DelibJNI64.DAPI_SPECIAL_CMD_GET_MODULE_CONFIG,
            DelibJNI64.DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DO, 0, 0);
        return config;
    }

    // Setzt einen digitalen Ausgang auf den gewünschten Zustand (0 = aus, 1 = an)
    public void setDigitalOutput(int channel, int state) throws Exception {
        DelibJNI64.DapiDOSet1(handle, channel, state);
        int error = DelibJNI64.DapiGetLastError();
        if (error != 0) {
            throw new Exception("Fehler beim Setzen von Output " + channel + ": Fehlercode " + error);
        }
    }

    // Liest den aktuellen Zustand eines digitalen Ausgangs aus
    public int getDigitalOutput(int channel) throws Exception {
        return DelibJNI64.DapiDOReadback32(handle, channel);
    }

    // Schaltet einen Ausgang um (an -> aus oder aus -> an)
    public void toggleDigitalOutput(int channel) throws Exception {
        int currentState = getDigitalOutput(channel);
        int newState = (currentState == 0) ? 1 : 0;
        setDigitalOutput(channel, newState);
    }

    // Schaltet alle Ausgänge (von 0 bis config-1) um
    public void toggleAllOutputs() throws Exception {
        int config = getDigitalOutputConfig();
        for (int channel = 0; channel < config; channel++) {
            toggleDigitalOutput(channel);
        }
    }

    // Schaltet alle Ausgänge aus
    public void allOutputsOff() throws Exception {
        int config = getDigitalOutputConfig();
        for (int channel = 0; channel < config; channel++) {
            setDigitalOutput(channel, 0);
        }
    }

    // Liest einen digitalen Eingang aus
    public int getDigitalInput(int channel) throws Exception {
        return DelibJNI64.DapiDIGet1(handle, channel);
    }

    // Liest alle digitalen Eingänge (hier beispielhaft 16 Kanäle) aus
    public int[] getAllDigitalInputs() throws Exception {
        int[] inputs = new int[16];
        for (int channel = 0; channel < inputs.length; channel++) {
            inputs[channel] = getDigitalInput(channel);
        }
        return inputs;
    }
}
