package org.example.modules.stripe;

import org.example.commonUtils.payment.loginOrder;
import org.example.commonUtils.payment.guestOrder;
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
