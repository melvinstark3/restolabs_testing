package org.example.web.modules.coupon;

import org.example.web.commonUtils.checkPreAppliedCoupon;
import org.example.web.commonUtils.guestCheckout;
import org.example.core.browserSetup;
import org.example.web.modules.coupon.createCart.addMenuItem;
import org.example.web.modules.coupon.createCart.cartHeader;
import org.example.web.modules.coupon.createCart.selector;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class verifyWithSubtotalMinimumOrderValue extends browserSetup {
    public void checkValidation() throws InterruptedException {
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"VERIFYWITHSUBTOTALMINIMUMORDERVALUE\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try {
            wait = new WebDriverWait(driver, 10);
            String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
            System.out.println("Validation Displayed : " + couponValidation);
            if (couponValidation.contains("You have not reached the minimum order total for this coupon.")){
                System.out.println("CASE PASS: Coupon was not Applied without reaching Set Subtotal");
            }
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"failure__popup fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 ng-star-inserted\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Close Coupon Popup\"]"))).click();
        } catch (NoSuchElementException | TimeoutException e){
            try{
                String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
                System.out.println("Applied Coupon Name is " + appliedCouponName);
                if (appliedCouponName.equalsIgnoreCase("Coupon - Verify With Subtotal, Minimum Order Value")){
                    System.out.println("CASE Fail: Coupon was Applied even without reaching Set Subtotal");
                }
            } catch (NoSuchElementException | TimeoutException ignored){}
        }
    }

    public void verifyCoupon() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-testid=\"goBackfromCart\"]"))).click();
        new addMenuItem("Normal Item");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"VERIFYWITHSUBTOTALMINIMUMORDERVALUE\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Verify With Subtotal, Minimum Order Value")){
                System.out.println("CASE PASS: Coupon was Applied with Subtotal Over Set Minimum Order Value");
            }
            new placeOrder();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE FAIL: Coupon was NOT Applied even with Subtotal Over Set Minimum Order Value");
        }
    }
}
