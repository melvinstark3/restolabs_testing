package org.example.core;
import org.example.modules.authorize.runAuthorize;
import org.example.modules.stripe.runStripe;

public class moduleSelector {
    public void defineModule(String currentModule) throws InterruptedException {
        if (currentModule.equalsIgnoreCase("authorize")){
            new runAuthorize();
        } else if (currentModule.equalsIgnoreCase("stripe")) {
            new runStripe();
        } else {
            System.out.println("No Such Module Exists! Re-try with Correct Module name!");
        }
    }

    public <T> T currentModuleClass(String className) {
        try {
            String module = System.getProperty("module");
            if (module == null) throw new RuntimeException("Module not set!");
            String fullPath = "org.example.modules." + module + "." + className;
            System.out.println("FULL PATH DEFINED IS " + fullPath);
            Class<?> clazz = Class.forName(fullPath);
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create class: " + className);
        }
    }
}