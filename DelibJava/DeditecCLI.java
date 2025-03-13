package DelibJava;

public class DeditecCLI {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("{\"error\":\"No command provided\"}");
            return;
        }
        String command = args[0];
        String output = "";
        try {
            // Bestehende Befehle
            if ("set-output".equals(command)) {
                if (args.length != 3) {
                    output = "{\"error\":\"Usage: set-output <channel> <state>\"}";
                } else {
                    int channel = Integer.parseInt(args[1]);
                    int state = Integer.parseInt(args[2]);
                    DeditecController controller = new DeditecController();
                    controller.setDigitalOutput(channel, state);
                    output = "{\"status\":\"Output " + channel + " set to " + state + "\"}";
                    controller.close();
                }
            } else if ("toggle-all".equals(command)) {
                DeditecController controller = new DeditecController();
                controller.toggleAllOutputs();
                output = "{\"status\":\"All outputs toggled\"}";
                controller.close();
            } else if ("all-off".equals(command)) {
                DeditecController controller = new DeditecController();
                controller.allOutputsOff();
                output = "{\"status\":\"All outputs turned off\"}";
                controller.close();
            } else if ("get-inputs".equals(command)) {
                DeditecController controller = new DeditecController();
                int[] inputs = controller.getAllDigitalInputs();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < inputs.length; i++) {
                    sb.append(inputs[i]);
                    if (i < inputs.length - 1) sb.append(",");
                }
                sb.append("]");
                output = "{\"inputs\":" + sb.toString() + "}";
                controller.close();
            }
            // Neue Befehle über den PinController
            else if ("set-pin".equals(command)) {
                if (args.length != 3) {
                    output = "{\"error\":\"Usage: set-pin <pin> <state>\"}";
                } else {
                    int pin = Integer.parseInt(args[1]);
                    int state = Integer.parseInt(args[2]);
                    DeditecPinController pinController = new DeditecPinController();
                    switch(pin) {
                        case 0: pinController.setPin0Output(state); break;
                        case 1: pinController.setPin1Output(state); break;
                        case 2: pinController.setPin2Output(state); break;
                        case 3: pinController.setPin3Output(state); break;
                        case 4: pinController.setPin4Output(state); break;
                        case 5: pinController.setPin5Output(state); break;
                        case 6: pinController.setPin6Output(state); break;
                        case 7: pinController.setPin7Output(state); break;
                        case 8: pinController.setPin8Output(state); break;
                        case 9: pinController.setPin9Output(state); break;
                        case 10: pinController.setPin10Output(state); break;
                        case 11: pinController.setPin11Output(state); break;
                        case 12: pinController.setPin12Output(state); break;
                        case 13: pinController.setPin13Output(state); break;
                        case 14: pinController.setPin14Output(state); break;
                        case 15: pinController.setPin15Output(state); break;
                        default: output = "{\"error\":\"Invalid pin\"}"; break;
                    }
                    if (output.isEmpty()) {
                        output = "{\"status\":\"Pin " + pin + " set to " + state + "\"}";
                    }
                    pinController.close();
                }
            } else if ("get-pin-output".equals(command)) {
                if (args.length != 2) {
                    output = "{\"error\":\"Usage: get-pin-output <pin>\"}";
                } else {
                    int pin = Integer.parseInt(args[1]);
                    DeditecPinController pinController = new DeditecPinController();
                    int state = -1;
                    switch(pin) {
                        case 0: state = pinController.getPin0OutputStatus(); break;
                        case 1: state = pinController.getPin1OutputStatus(); break;
                        case 2: state = pinController.getPin2OutputStatus(); break;
                        case 3: state = pinController.getPin3OutputStatus(); break;
                        case 4: state = pinController.getPin4OutputStatus(); break;
                        case 5: state = pinController.getPin5OutputStatus(); break;
                        case 6: state = pinController.getPin6OutputStatus(); break;
                        case 7: state = pinController.getPin7OutputStatus(); break;
                        case 8: state = pinController.getPin8OutputStatus(); break;
                        case 9: state = pinController.getPin9OutputStatus(); break;
                        case 10: state = pinController.getPin10OutputStatus(); break;
                        case 11: state = pinController.getPin11OutputStatus(); break;
                        case 12: state = pinController.getPin12OutputStatus(); break;
                        case 13: state = pinController.getPin13OutputStatus(); break;
                        case 14: state = pinController.getPin14OutputStatus(); break;
                        case 15: state = pinController.getPin15OutputStatus(); break;
                        default: output = "{\"error\":\"Invalid pin\"}"; break;
                    }
                    if (output.isEmpty()) {
                        output = "{\"status\":\"Pin " + pin + " output status: " + state + "\"}";
                    }
                    pinController.close();
                }
            } else if ("toggle-pin".equals(command)) {
                if (args.length != 2) {
                    output = "{\"error\":\"Usage: toggle-pin <pin>\"}";
                } else {
                    int pin = Integer.parseInt(args[1]);
                    DeditecPinController pinController = new DeditecPinController();
                    switch(pin) {
                        case 0: pinController.togglePin0Output(); break;
                        case 1: pinController.togglePin1Output(); break;
                        case 2: pinController.togglePin2Output(); break;
                        case 3: pinController.togglePin3Output(); break;
                        case 4: pinController.togglePin4Output(); break;
                        case 5: pinController.togglePin5Output(); break;
                        case 6: pinController.togglePin6Output(); break;
                        case 7: pinController.togglePin7Output(); break;
                        case 8: pinController.togglePin8Output(); break;
                        case 9: pinController.togglePin9Output(); break;
                        case 10: pinController.togglePin10Output(); break;
                        case 11: pinController.togglePin11Output(); break;
                        case 12: pinController.togglePin12Output(); break;
                        case 13: pinController.togglePin13Output(); break;
                        case 14: pinController.togglePin14Output(); break;
                        case 15: pinController.togglePin15Output(); break;
                        default: output = "{\"error\":\"Invalid pin\"}"; break;
                    }
                    if (output.isEmpty()) {
                        output = "{\"status\":\"Pin " + args[1] + " toggled\"}";
                    }
                    pinController.close();
                }
            } else if ("get-pin-input".equals(command)) {
                if (args.length != 2) {
                    output = "{\"error\":\"Usage: get-pin-input <pin>\"}";
                } else {
                    int pin = Integer.parseInt(args[1]);
                    DeditecPinController pinController = new DeditecPinController();
                    int state = -1;
                    switch(pin) {
                        case 0: state = pinController.getPin0InputStatus(); break;
                        case 1: state = pinController.getPin1InputStatus(); break;
                        case 2: state = pinController.getPin2InputStatus(); break;
                        case 3: state = pinController.getPin3InputStatus(); break;
                        case 4: state = pinController.getPin4InputStatus(); break;
                        case 5: state = pinController.getPin5InputStatus(); break;
                        case 6: state = pinController.getPin6InputStatus(); break;
                        case 7: state = pinController.getPin7InputStatus(); break;
                        case 8: state = pinController.getPin8InputStatus(); break;
                        case 9: state = pinController.getPin9InputStatus(); break;
                        case 10: state = pinController.getPin10InputStatus(); break;
                        case 11: state = pinController.getPin11InputStatus(); break;
                        case 12: state = pinController.getPin12InputStatus(); break;
                        case 13: state = pinController.getPin13InputStatus(); break;
                        case 14: state = pinController.getPin14InputStatus(); break;
                        case 15: state = pinController.getPin15InputStatus(); break;
                        default: output = "{\"error\":\"Invalid pin\"}"; break;
                    }
                    if (output.isEmpty()) {
                        output = "{\"status\":\"Pin " + pin + " input status: " + state + "\"}";
                    }
                    pinController.close();
                }
            } else {
                output = "{\"error\":\"Unknown command: " + command + "\"}";
            }
        } catch (Exception e) {
            output = "{\"error\":\"" + e.getMessage() + "\"}";
        }
        System.out.println(output);
    }
}
