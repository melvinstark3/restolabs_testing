package org.example.modules.coupon;

import org.example.commonUtils.checkPreAppliedCoupon;
import org.example.commonUtils.guestCheckout;
import org.example.commonUtils.payment.createCart;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class runCoupon extends browserSetup {
    public runCoupon() throws InterruptedException {
        invokeBrowser();
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();

        new fixedDiscountTimeSpecific();







    }
}
