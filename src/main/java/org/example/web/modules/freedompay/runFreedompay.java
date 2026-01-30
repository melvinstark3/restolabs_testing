package org.example.web.modules.freedompay;

import org.example.web.commonUtils.payment.guestOrder;
import org.example.web.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runFreedompay extends browserSetup {

    public runFreedompay() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
