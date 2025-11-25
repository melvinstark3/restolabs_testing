package org.example;
import org.example.core.browserSetup;
import org.example.core.moduleSelector;

public class Main extends browserSetup {

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.err.println("Error: Please provide a module name (stripe / authorize)");
            System.exit(1);
        }
        String currentModule = args[0];
        System.setProperty("module", currentModule);
        moduleSelector module = new moduleSelector();
        module.defineModule(currentModule);
    }
}
