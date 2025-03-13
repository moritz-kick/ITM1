package DelibJava;

public class DeditecPinController {

    private DeditecController controller;

    public DeditecPinController() throws Exception {
        controller = new DeditecController();
    }

    // === Digital Outputs (Pins 0 bis 15) ===
    public void setPin0Output(int state) throws Exception { controller.setDigitalOutput(0, state); }
    public int getPin0OutputStatus() throws Exception { return controller.getDigitalOutput(0); }
    public void togglePin0Output() throws Exception { controller.toggleDigitalOutput(0); }

    public void setPin1Output(int state) throws Exception { controller.setDigitalOutput(1, state); }
    public int getPin1OutputStatus() throws Exception { return controller.getDigitalOutput(1); }
    public void togglePin1Output() throws Exception { controller.toggleDigitalOutput(1); }

    public void setPin2Output(int state) throws Exception { controller.setDigitalOutput(2, state); }
    public int getPin2OutputStatus() throws Exception { return controller.getDigitalOutput(2); }
    public void togglePin2Output() throws Exception { controller.toggleDigitalOutput(2); }

    public void setPin3Output(int state) throws Exception { controller.setDigitalOutput(3, state); }
    public int getPin3OutputStatus() throws Exception { return controller.getDigitalOutput(3); }
    public void togglePin3Output() throws Exception { controller.toggleDigitalOutput(3); }

    public void setPin4Output(int state) throws Exception { controller.setDigitalOutput(4, state); }
    public int getPin4OutputStatus() throws Exception { return controller.getDigitalOutput(4); }
    public void togglePin4Output() throws Exception { controller.toggleDigitalOutput(4); }

    public void setPin5Output(int state) throws Exception { controller.setDigitalOutput(5, state); }
    public int getPin5OutputStatus() throws Exception { return controller.getDigitalOutput(5); }
    public void togglePin5Output() throws Exception { controller.toggleDigitalOutput(5); }

    public void setPin6Output(int state) throws Exception { controller.setDigitalOutput(6, state); }
    public int getPin6OutputStatus() throws Exception { return controller.getDigitalOutput(6); }
    public void togglePin6Output() throws Exception { controller.toggleDigitalOutput(6); }

    public void setPin7Output(int state) throws Exception { controller.setDigitalOutput(7, state); }
    public int getPin7OutputStatus() throws Exception { return controller.getDigitalOutput(7); }
    public void togglePin7Output() throws Exception { controller.toggleDigitalOutput(7); }

    public void setPin8Output(int state) throws Exception { controller.setDigitalOutput(8, state); }
    public int getPin8OutputStatus() throws Exception { return controller.getDigitalOutput(8); }
    public void togglePin8Output() throws Exception { controller.toggleDigitalOutput(8); }

    public void setPin9Output(int state) throws Exception { controller.setDigitalOutput(9, state); }
    public int getPin9OutputStatus() throws Exception { return controller.getDigitalOutput(9); }
    public void togglePin9Output() throws Exception { controller.toggleDigitalOutput(9); }

    public void setPin10Output(int state) throws Exception { controller.setDigitalOutput(10, state); }
    public int getPin10OutputStatus() throws Exception { return controller.getDigitalOutput(10); }
    public void togglePin10Output() throws Exception { controller.toggleDigitalOutput(10); }

    public void setPin11Output(int state) throws Exception { controller.setDigitalOutput(11, state); }
    public int getPin11OutputStatus() throws Exception { return controller.getDigitalOutput(11); }
    public void togglePin11Output() throws Exception { controller.toggleDigitalOutput(11); }

    public void setPin12Output(int state) throws Exception { controller.setDigitalOutput(12, state); }
    public int getPin12OutputStatus() throws Exception { return controller.getDigitalOutput(12); }
    public void togglePin12Output() throws Exception { controller.toggleDigitalOutput(12); }

    public void setPin13Output(int state) throws Exception { controller.setDigitalOutput(13, state); }
    public int getPin13OutputStatus() throws Exception { return controller.getDigitalOutput(13); }
    public void togglePin13Output() throws Exception { controller.toggleDigitalOutput(13); }

    public void setPin14Output(int state) throws Exception { controller.setDigitalOutput(14, state); }
    public int getPin14OutputStatus() throws Exception { return controller.getDigitalOutput(14); }
    public void togglePin14Output() throws Exception { controller.toggleDigitalOutput(14); }

    public void setPin15Output(int state) throws Exception { controller.setDigitalOutput(15, state); }
    public int getPin15OutputStatus() throws Exception { return controller.getDigitalOutput(15); }
    public void togglePin15Output() throws Exception { controller.toggleDigitalOutput(15); }

    // === Digital Inputs (Pins 0 bis 15) ===
    public int getPin0InputStatus() throws Exception { return controller.getDigitalInput(0); }
    public int getPin1InputStatus() throws Exception { return controller.getDigitalInput(1); }
    public int getPin2InputStatus() throws Exception { return controller.getDigitalInput(2); }
    public int getPin3InputStatus() throws Exception { return controller.getDigitalInput(3); }
    public int getPin4InputStatus() throws Exception { return controller.getDigitalInput(4); }
    public int getPin5InputStatus() throws Exception { return controller.getDigitalInput(5); }
    public int getPin6InputStatus() throws Exception { return controller.getDigitalInput(6); }
    public int getPin7InputStatus() throws Exception { return controller.getDigitalInput(7); }
    public int getPin8InputStatus() throws Exception { return controller.getDigitalInput(8); }
    public int getPin9InputStatus() throws Exception { return controller.getDigitalInput(9); }
    public int getPin10InputStatus() throws Exception { return controller.getDigitalInput(10); }
    public int getPin11InputStatus() throws Exception { return controller.getDigitalInput(11); }
    public int getPin12InputStatus() throws Exception { return controller.getDigitalInput(12); }
    public int getPin13InputStatus() throws Exception { return controller.getDigitalInput(13); }
    public int getPin14InputStatus() throws Exception { return controller.getDigitalInput(14); }
    public int getPin15InputStatus() throws Exception { return controller.getDigitalInput(15); }

    public void close() {
        controller.close();
    }
}
