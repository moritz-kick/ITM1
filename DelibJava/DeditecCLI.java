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
            DeditecController controller = new DeditecController();
            if ("set-output".equals(command)) {
                if (args.length != 3) {
                    output = "{\"error\":\"Usage: set-output <channel> <state>\"}";
                } else {
                    int channel = Integer.parseInt(args[1]);
                    int state = Integer.parseInt(args[2]);
                    controller.setDigitalOutput(channel, state);
                    output = "{\"status\":\"Output " + channel + " set to " + state + "\"}";
                }
            } else if ("toggle-all".equals(command)) {
                controller.toggleAllOutputs();
                output = "{\"status\":\"All outputs toggled\"}";
            } else if ("all-off".equals(command)) {
                controller.allOutputsOff();
                output = "{\"status\":\"All outputs turned off\"}";
            } else if ("get-inputs".equals(command)) {
                int[] inputs = controller.getAllDigitalInputs();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < inputs.length; i++) {
                    sb.append(inputs[i]);
                    if (i < inputs.length - 1) sb.append(",");
                }
                sb.append("]");
                output = "{\"inputs\":" + sb.toString() + "}";
            } else {
                output = "{\"error\":\"Unknown command: " + command + "\"}";
            }
            controller.close();
        } catch (Exception e) {
            output = "{\"error\":\"" + e.getMessage() + "\"}";
        }
        System.out.println(output);
    }
}
