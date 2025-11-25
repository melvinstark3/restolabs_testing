package org.example.modules.authorize;

import org.example.commonUtils.payment.guestOrder;
import org.example.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runAuthorize extends browserSetup {
    public runAuthorize() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
