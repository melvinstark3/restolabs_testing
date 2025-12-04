package org.example.modules.cardconnect;

import org.example.commonUtils.payment.guestOrder;
import org.example.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runCardconnect extends browserSetup {
    public runCardconnect() throws InterruptedException {
//        invokeBrowser();
//        new guestOrder();
//        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
