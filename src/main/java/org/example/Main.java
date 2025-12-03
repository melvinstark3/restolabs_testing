package org.example;

public class Main extends browserSetup{

    public static void main(String[] args) throws InterruptedException {
        consoleCapturer capturer = new consoleCapturer(System.out);
        System.setOut(capturer);
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
       } catch (Exception e) {
            System.out.println("\nAutomation Stopped Due to an Error");
            System.out.println("Error Message: " + e.getMessage());
            e.printStackTrace();
       } finally {
           emailReport.send(capturer.getCapturedLogs());
       }
    }
}