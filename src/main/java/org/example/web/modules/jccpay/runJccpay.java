package org.example.web.modules.jccpay;
import org.example.web.commonUtils.payment.guestOrder;
import org.example.web.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runJccpay extends browserSetup {
    public runJccpay() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
