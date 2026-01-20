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

public class deliveryFeesOnly extends browserSetup {
    public deliveryFeesOnly() throws InterruptedException {
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"DELIVERYFEEONLY\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));

        try {
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Delivery Fee Only")) {
                System.out.println("CASE PASS: Delivery Fees Only Coupon was Applied!");
            }
            double expectedDiscountRate = 0.50;
            double deliveryFees = 0;
            try {
                deliveryFees = Double.parseDouble(driver.findElement(By.xpath("//h6[@data-testid=\"Delivery Fee\"]")).getText());
            } catch (NoSuchElementException e) {
                System.out.println("WARNING! Can't Verify Delivery Fees as it was not Displayed at Checkout");
            }
            double expectedDiscountValue = deliveryFees*expectedDiscountRate;
            double dispalyedDiscountValue = Double.parseDouble(driver.findElement(By.xpath("//h6[contains(@data-testid,'Coupon')]")).getText().substring(2));
            System.out.println("Expected Discount : " + expectedDiscountValue);
            System.out.println("Displayed Discount : " + dispalyedDiscountValue);
            if (expectedDiscountValue==dispalyedDiscountValue){
                System.out.println("CASE PASSED: Discount Calculated for Delivery Fees Only was Calculated Correctly");
            } else {
                System.out.println("CASE FAILED: Discount Calculated for Percent including Delivery Fees Only was NOT Calculated Correctly");
            }
        } catch (NoSuchElementException | TimeoutException ignored) {}

        new placeOrder();
    }
}
