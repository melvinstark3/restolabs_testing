package org.example.web.modules.stripe;

import org.example.web.commonUtils.payment.loginOrder;
import org.example.web.commonUtils.payment.guestOrder;
import org.example.core.browserSetup;

public class runStripe extends browserSetup {
    public runStripe() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
