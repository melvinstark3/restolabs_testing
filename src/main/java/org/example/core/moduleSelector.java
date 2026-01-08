package org.example.core;
import org.example.modules.authorize.runAuthorize;
import org.example.modules.basic.runBasic;
import org.example.modules.cardconnect.runCardconnect;
import org.example.modules.checkoutfi.runCheckoutfi;
import org.example.modules.cod.runCOD;
import org.example.modules.freedompay.runFreedompay;
import org.example.modules.jccpay.runJccpay;
import org.example.modules.qms.runQMS;
import org.example.modules.restoclient.runRestoclient;
import org.example.modules.square.runSquare;
import org.example.modules.stripe.runStripe;
import org.example.modules.worldpayexp.runWorldPayExp;

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
        } else if (currentModule.equalsIgnoreCase("freedompay")){
            new runFreedompay();
        } else if (currentModule.equalsIgnoreCase("worldpayexp")){
            new runWorldPayExp();
        } else if (currentModule.equalsIgnoreCase("checkoutfi")) {
            new runCheckoutfi();
        } else if (currentModule.equalsIgnoreCase("jccpay")) {
            new runJccpay();
        } else if (currentModule.equalsIgnoreCase("basic")) {
            new runBasic();
        } else if (currentModule.equalsIgnoreCase("restoclient")) {
            new runRestoclient();
        } else {
            System.out.println("No Such Module Exists! Re-try with Correct Module name!");
        }
    }

    public void useServer(String server){
        if (server.equalsIgnoreCase("qa")) {
            System.setProperty("server", "qa-order.online");
        } else if (server.equalsIgnoreCase("prod")) {
            System.setProperty("server", "onlineordering.io");
        } else {
            System.err.println("Invalid Server: " + server + ". Available Servers: QA, Prod");
            System.exit(1);
        }
        System.out.println("Server Set is " + System.getProperty("server"));
    }

        public static <T> T currentModuleClass(String className, Class<T> defaultClass) {
            String module = System.getProperty("module");
            String fullPath = "org.example.modules." + module + "." + className;
            try {
                Class<?> clazz = Class.forName(fullPath);
                return (T) clazz.getDeclaredConstructor().newInstance();

            } catch (ClassNotFoundException e) {
                System.out.println(">> Override not found for " + className + ". Using Default.");
                try {
                    return defaultClass.getDeclaredConstructor().newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException("Fatal: Could not create Default Class: " + defaultClass.getName(), ex);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading module class: " + fullPath, e);
            }
        }
    }