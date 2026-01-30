package org.example.web.modules.authorize;

import org.example.web.commonUtils.payment.guestOrder;
import org.example.web.commonUtils.payment.loginOrder;
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
