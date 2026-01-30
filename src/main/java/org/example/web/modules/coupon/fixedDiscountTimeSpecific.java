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

public class fixedDiscountTimeSpecific extends browserSetup {
    public fixedDiscountTimeSpecific() throws InterruptedException {
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"FIXED DISCOUNT TIME SPECIFIC\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        String expectedDiscountAmount = "10.00";
        try{
            String discountAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getText().substring(2);
            System.out.println("Expected Discount : " + expectedDiscountAmount);
            System.out.println("Displayed Discount : " + discountAmount);
            if (discountAmount.equalsIgnoreCase(expectedDiscountAmount)){
                System.out.println("CASE: Fixed Discount and Time Specific Coupon was Applied Successfully");
            } else {
                System.out.println("CASE FAIL: Time Specific Coupon worked but Fixed Discount Value did not Match");
            }
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE FAILED: Fixed Discount and Time Specific Coupon was not Applied");
        }
        new placeOrder();
    }
}
