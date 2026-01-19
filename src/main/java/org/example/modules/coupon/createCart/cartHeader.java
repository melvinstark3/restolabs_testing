package org.example.modules.coupon.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

public class cartHeader extends browserSetup {
    public cartHeader(){
//        try {
//            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
//            System.out.println("Cart Popup Appeared Automatically. Proceeding to Checkout");
//        }
//        catch (NoSuchElementException | TimeoutException e){
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Proceeding to Checkout");
//        }
        wait = new WebDriverWait(driver, 30);
    }
}
