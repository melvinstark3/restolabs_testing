package org.example.web.modules.cardconnect;

import org.example.web.commonUtils.payment.guestOrder;
import org.example.web.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runCardconnect extends browserSetup {
    public runCardconnect() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
