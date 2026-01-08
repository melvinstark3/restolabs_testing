package org.example;
import org.example.core.browserSetup;
import org.example.core.consoleCapturer;
import org.example.core.emailReport;
import org.example.core.moduleSelector;

public class Main extends browserSetup {

    public static void main(String[] args) {
        consoleCapturer capturer = new consoleCapturer(System.out);
        System.setOut(capturer);
        String screenshotPath = null;
        String automationStatus = "PENDING";
        try {
            if (args.length < 2) {
                System.err.println("Error: Missing Module and/or Server");
                System.err.println("Please Pass Arguments in the Format of \"module server\"");
                System.err.println("Example: -Dexec.args=\"stripe prod\"");
                System.exit(1);
            }

            String currentModule = args[0];
            String server = args[1];

            for (int i = 0; i < args.length; i++) {
                System.out.println("args[" + i + "] = [" + args[i] + "]");
            }

            System.setProperty("module", currentModule);

            moduleSelector module = new moduleSelector();
            module.useServer(server);
            module.defineModule(currentModule);
            automationStatus = "PASS";
        } catch (Exception e) {
        System.out.println("\nAutomation Stopped Due to an Error");
        System.out.println("Error Message: " + e.getMessage());
        automationStatus = "FAIL";
        screenshotPath = takeScreenshot();
        e.printStackTrace();
    } finally {
        emailReport.send(automationStatus, capturer.getCapturedLogs(),screenshotPath);
    }
    }
}
