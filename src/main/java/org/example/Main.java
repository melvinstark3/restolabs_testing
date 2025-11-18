package org.example;

public class Main extends browserSetup{

    static StringBuilder buildLogs = new StringBuilder();

    public static void log(String message) {
        System.out.println(message);
        buildLogs.append(message).append("\n");
    }

    public static void main(String[] args) throws InterruptedException {
        String status = "INCOMPLETE";
        try{
           invokeBrowser();
           new codOrder();
           new pickupOrder();
           new dineInOrder();
           new homeDeliveryOrder();
           new comboOrder();
           quitBrowser();
           invokeBrowser();
           new loyaltyOrder();
           quitBrowser();
           status = "PASS";
       } catch (Exception e) {
           status = "FAIL";
           log("\nError Occurred: \n" + e.getMessage());
           // Add stack trace to logs
           for (StackTraceElement ste : e.getStackTrace()) {
               buildLogs.append(ste.toString()).append("\n");
           }
       } finally {
           emailReport.send(status, buildLogs.toString());
       }



    }
}