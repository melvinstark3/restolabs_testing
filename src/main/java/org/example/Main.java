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
        try {
            if (args.length == 0) {
                System.err.println("Error: Please provide a module name");
                System.exit(1);
            }
            String currentModule = args[0];
            System.setProperty("module", currentModule);
            moduleSelector module = new moduleSelector();
            module.defineModule(currentModule);
        } catch (Exception e) {
        System.out.println("\nAutomation Stopped Due to an Error");
        System.out.println("Error Message: " + e.getMessage());
        screenshotPath = takeScreenshot();
        e.printStackTrace();
    } finally {
        emailReport.send(capturer.getCapturedLogs(),screenshotPath);
    }
    }
}
