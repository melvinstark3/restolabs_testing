package org.example.web.modules.checkoutfi;

import org.example.web.commonUtils.payment.guestOrder;
import org.example.web.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runCheckoutfi extends browserSetup {
    public runCheckoutfi() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
