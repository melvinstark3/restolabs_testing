package org.example.core;
import org.apache.commons.text.WordUtils;

public class moduleSelector {
    public void defineModule(String currentModule, String platform){
        try {
            String className =
                            "org.example."
                            + platform.toLowerCase()
                            + ".modules."
                            + currentModule.toLowerCase()
                            + ".run"
                            + WordUtils.capitalizeFully(currentModule);

            System.out.println("Running Module Class: " + className);

            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException e) {
            System.err.println("No such module exists for platform: "
                    + platform + " and module: " + currentModule);
            e.printStackTrace();
            System.exit(1);

        } catch (Exception e) {
            throw new RuntimeException("Error executing module: " + currentModule, e);
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
            String fullPath = "org.example.web.modules." + module + "." + className;
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