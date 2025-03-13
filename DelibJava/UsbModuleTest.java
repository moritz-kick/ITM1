package DelibJava;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import DelibJava.DelibJNI64;


public class UsbModuleTest {
    public static void main(String[] args) {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            // Modul öffnen (BS-USB2 = 0x002D)
            int handle = DelibJNI64.DapiOpenModule(0x002D, 0);
            if (handle <= 0) {
                System.err.println("Fehler: Modul konnte nicht geöffnet werden!");
                checkError(console);
                return;
            }
            System.out.printf("Modulhandle = 0x%x\n", handle);

            // Modul-Konfiguration abrufen (Anzahl der Digitalausgänge)
            int config = DelibJNI64.DapiSpecialCommand(
                    handle,
                    DelibJNI64.DAPI_SPECIAL_CMD_GET_MODULE_CONFIG,
                    DelibJNI64.DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DO, 0, 0);
            checkError(console);
            System.out.printf("Anzahl der Digitalausgänge: %d\n", config);
            System.out.println("Drücken Sie Enter, um fortzufahren.");
            console.readLine();

            // Digitalen Ausgang 0 ein- und ausschalten
            toggleDigitalOutput(handle, 0, console);

            // Modul schließen
            DelibJNI64.DapiCloseModule(handle);
            System.out.println("Modulverbindung geschlossen. Programmende.");
        } catch (Exception e) {
            System.err.println("Unerwarteter Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Prüft auf Fehler und gibt ggf. den Fehlercode aus
    private static void checkError(BufferedReader console) throws Exception {
        int error = DelibJNI64.DapiGetLastError();
        if (error != 0) { // DAPI_ERR_NONE ist nicht definiert, daher direkt auf 0 prüfen
            System.err.printf("Error - Code: 0x%x\n", error);
            System.err.println("Drücken Sie Enter zum Beenden.");
            console.readLine();
            System.exit(1);
        }
    }

    // Digitalen Ausgang umschalten
    private static void toggleDigitalOutput(int handle, int channel, BufferedReader console) throws Exception {
        DelibJNI64.DapiDOSet1(handle, channel, 1);
        checkError(console);
        System.out.println("Digitaler Ausgang " + channel + " wurde eingeschaltet.");
        console.readLine();

        DelibJNI64.DapiDOSet1(handle, channel, 0);
        checkError(console);
        System.out.println("Digitaler Ausgang " + channel + " wurde ausgeschaltet.");
        console.readLine();
    }
}
