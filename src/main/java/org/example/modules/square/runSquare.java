package org.example.modules.square;

import org.example.commonUtils.payment.guestOrder;
import org.example.commonUtils.payment.loginOrder;
import org.example.core.browserSetup;

public class runSquare extends browserSetup {

    public runSquare() throws InterruptedException {
        invokeBrowser();
        new guestOrder();
        quitBrowser();
        invokeBrowser();
        new loginOrder();
        quitBrowser();
    }
}
