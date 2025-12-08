package org.example.modules.freedompay;

import org.example.commonUtils.payment.guestOrder;
import org.example.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runFreedompay extends browserSetup {

    public runFreedompay() throws InterruptedException {
//        invokeBrowser();
//        new guestOrder();
//        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
