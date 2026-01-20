package org.example.modules.coupon;

import org.example.commonUtils.checkPreAppliedCoupon;
import org.example.commonUtils.guestCheckout;
import org.example.core.browserSetup;
import org.example.modules.coupon.createCart.addMenuItem;
import org.example.modules.coupon.createCart.cartHeader;
import org.example.modules.coupon.createCart.selector;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class totalOfMatchingRequireAllProductsToMatchWhitelistItems extends browserSetup {
    public void checkValidation() throws InterruptedException {
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Normal Item");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"TOTALMATCHINGREQUIREPRODUCTSMATCHWHITELISTITEMS\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try {
            wait = new WebDriverWait(driver, 10);
            String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
            System.out.println("Validation Displayed : " + couponValidation);
            if (couponValidation.contains("You do not have all applicable products in your cart.")){
                System.out.println("CASE PASS: Coupon was not Applied without All Required Products in Cart");
            }
        } catch (NoSuchElementException | TimeoutException e){
            try{
                String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
                System.out.println("Applied Coupon Name is " + appliedCouponName);
                if (appliedCouponName.equalsIgnoreCase("Coupon - Total of Matching, Require All Products to Match, Whitelist Items")){
                    System.out.println("CASE Fail: Coupon was Applied even without having All Required Products in Cart");
                }
            } catch (NoSuchElementException | TimeoutException ignored){}
        }
    }

    public void verifyCoupon() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-testid=\"goBackfromCart\"]"))).click();
        new addMenuItem("Normal Item 2");
        new cartHeader();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"TOTALMATCHINGREQUIREPRODUCTSMATCHWHITELISTITEMS\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Total of Matching, Require All Products to Match, Whitelist Items")){
                System.out.println("CASE PASS: Coupon was Applied with All Required Products from Whitelisted Items List");
            }
        } catch (NoSuchElementException | TimeoutException ignored){}
        new placeOrder();
    }
}
