package org.example.modules.basic;
import org.example.core.browserSetup;

public class runBasic extends browserSetup {

    public runBasic() throws InterruptedException {
       invokeBrowser();
       new codOrder();
       new pickupOrder();
       new dineInOrder();
       new homeDeliveryOrder();
       new comboOrder();
       quitBrowser();
       invokeBrowser();
       new loyaltyOrder();
       quitBrowser();
    }
}