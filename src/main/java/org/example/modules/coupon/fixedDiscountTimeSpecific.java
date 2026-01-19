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

public class fixedDiscountTimeSpecific extends browserSetup {
    public fixedDiscountTimeSpecific() throws InterruptedException {
        new selector(readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"FIXED DISCOUNT TIME SPECIFIC\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        String expectedDiscountAmount = "10.00";
        try{
            String discountAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getText().substring(2);
            if (discountAmount.equalsIgnoreCase(expectedDiscountAmount)){
                System.out.println("CASE: Fixed Discount and Time Specific Coupon was Applied Successfully");
            } else {
                System.out.println("CASE FAIL: Time Specific Coupon worked but Fixed Discount Value did not Match");
            }
        } catch (NoSuchElementException e){
            System.out.println("CASE FAILED: Fixed Discount and Time Specific Coupon was not Applied");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"placeOrder\"]"))).click();
        try {
            wait = new WebDriverWait(driver, 60);
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
            System.out.println("Order with Coupon Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Order with Coupon FAILED!");
        }
    }
}
