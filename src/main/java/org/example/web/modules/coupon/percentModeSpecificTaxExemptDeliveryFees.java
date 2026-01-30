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

public class percentModeSpecificTaxExemptDeliveryFees extends browserSetup {

    public void checkValidation() throws InterruptedException {
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Normal Item");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        driver.findElement(By.xpath("//input[@data-testid=\"couponInput\"]")).sendKeys("PERCENTMODESPECIFICTAXEXEMPTDELIVERYFEES");
        driver.findElement(By.xpath("//button[@data-testid=\"applyCoupon\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try {
            wait = new WebDriverWait(driver, 10);
            String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
            System.out.println("Validation Displayed : " + couponValidation);
            if (couponValidation.contains("This coupon code is invalid or has expired.")){
                System.out.println("CASE PASS: Home Delivery Mode Specific Coupon was not Applied on a Different Mode Manually");
            }
        } catch (NoSuchElementException | TimeoutException e){
            try{
                String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
                System.out.println("Applied Coupon Name is " + appliedCouponName);
                if (appliedCouponName.equalsIgnoreCase("Coupon - Percent, Mode Specific, Tax Exempt, Delivery Fees")){
                    System.out.println("CASE Fail: Home Delivery Mode Specific Coupon was Applied on Different Mode");
                }
            } catch (NoSuchElementException | TimeoutException ignored){}
        }
    }

    public void verifyCoupon() throws InterruptedException {
        new orderMode("Home Delivery");
        new selector("Home Delivery", readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new addMenuItem("Cheap Item 2");
        new addMenuItem("Cheap Item 3");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"PERCENTMODESPECIFICTAXEXEMPTDELIVERYFEES\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try {
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Percent, Mode Specific, Tax Exempt, Delivery Fees")) {
                System.out.println("CASE PASS: Percent, Mode Specific, Tax Exempt, Delivery Fees Coupon was Applied!");
            }
            double expectedDiscountRate = 0.10;
            double subtotal = Double.parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@data-testid=\"Subtotal\"]"))).getText().substring(1));
            double deliveryFees = 0;
            try {
                deliveryFees = Double.parseDouble(driver.findElement(By.xpath("//h6[@data-testid=\"Delivery Fee\"]")).getText().substring(2));
            } catch (NoSuchElementException e) {
                System.out.println("WARNING! Can't Verify Delivery Fees as it was not Displayed at Checkout");
            }
            double expectedDiscountValue = (subtotal + deliveryFees)*expectedDiscountRate;
            double dispalyedDiscountValue = Double.parseDouble(driver.findElement(By.xpath("//h6[contains(@data-testid,'Coupon')]")).getText().substring(2));
            System.out.println("Expected Discount : " + expectedDiscountValue);
            System.out.println("Displayed Discount : " + dispalyedDiscountValue);
            if (expectedDiscountValue==dispalyedDiscountValue){
                System.out.println("CASE PASSED: Discount Calculated for Percent including Delivery Fees was Calculated Correctly");
            } else {
                System.out.println("CASE FAILED: Discount Calculated for Percent including Delivery Fees was NOT Calculated Correctly");
            }
        } catch (NoSuchElementException | TimeoutException ignored) {}
        new placeOrder();
    }
}
