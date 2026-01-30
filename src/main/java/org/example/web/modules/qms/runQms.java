package org.example.web.modules.qms;

import org.example.core.browserSetup;

public class runQms extends browserSetup{
    public runQms() throws InterruptedException {
        invokeBrowser();
        new qmsOrders();
        quitBrowser();
    }
}