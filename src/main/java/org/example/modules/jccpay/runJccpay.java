package org.example.modules.jccpay;
import org.example.commonUtils.payment.guestOrder;
import org.example.commonUtils.payment.loginOrder;
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
