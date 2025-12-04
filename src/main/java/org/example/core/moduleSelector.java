package org.example.core;
import org.example.modules.authorize.runAuthorize;
import org.example.modules.cardconnect.runCardconnect;
import org.example.modules.cod.runCOD;
import org.example.modules.qms.runQMS;
import org.example.modules.square.runSquare;
import org.example.modules.stripe.runStripe;

public class moduleSelector {
    public void defineModule(String currentModule) throws InterruptedException {
        if (currentModule.equalsIgnoreCase("authorize")){
            new runAuthorize();
        } else if (currentModule.equalsIgnoreCase("stripe")) {
            new runStripe();
        } else if (currentModule.equalsIgnoreCase("cod")) {
            new runCOD();
        } else if (currentModule.equalsIgnoreCase("qms")) {
            new runQMS();
        } else if (currentModule.equalsIgnoreCase("cardconnect")) {
            new runCardconnect();
        } else if (currentModule.equalsIgnoreCase("square")){
            new runSquare();
        } else {
            System.out.println("No Such Module Exists! Re-try with Correct Module name!");
        }
    }

    public <T> T currentModuleClass(String className) {
        try {
            String module = System.getProperty("module");
            String fullPath = "org.example.modules." + module + "." + className;
            Class<?> clazz = Class.forName(fullPath);
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could Not Create Class: " + className);
        }
    }
}