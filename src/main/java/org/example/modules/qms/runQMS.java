package org.example.modules.qms;

import org.example.core.browserSetup;

public class runQMS extends browserSetup{
    public runQMS() throws InterruptedException {
        invokeBrowser();
        new qmsOrders();
        quitBrowser();
    }
}