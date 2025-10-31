package org.example;

public class Main extends browserSetup{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        new codOrder();
        quitBrowser();
    }
}