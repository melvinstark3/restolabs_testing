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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;

public class hiddenDateSpecificCoupon extends browserSetup {
    public hiddenDateSpecificCoupon() throws InterruptedException {
        new orderMode("Dine In");
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Normal Item");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        boolean couponValidationDisplayed = false;
        System.out.println("Applying Coupon");
        String currentDate = String.valueOf(LocalDate.now());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        driver.findElement(By.xpath("//input[@data-testid=\"couponInput\"]")).sendKeys(readProperty("hiddenDateSpecificCouponCode"));
        driver.findElement(By.xpath("//button[@data-testid=\"applyCoupon\"]")).click();
        try{
            wait = new WebDriverWait(driver, 10);
            WebElement couponValidationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]")));
            couponValidationDisplayed = couponValidationElement.isDisplayed();
            String couponValidationString = couponValidationElement.getText();
            System.out.println("Validation Displayed : " + couponValidationString);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"failure__popup fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 ng-star-inserted\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Close Coupon Popup\"]"))).click();

        } catch (NoSuchElementException | TimeoutException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@data-testid=\"Subtotal\"]")));
            String appliedCouponName = driver.findElement(By.xpath("//h6[contains(@data-testid,'Coupon')]")).getText();
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            new placeOrder();
        }
        if (currentDate.contains("-12-25")){
            if (couponValidationDisplayed){
                System.out.println("CASE FAIL: User is Unable to Apply Coupon on Valid Calender Date");
            } else {
                System.out.println("CASE PASS: User is able to Use Coupon on a Valid Calender Date");
            }
        } else {
            if (couponValidationDisplayed){
                System.out.println("CASE PASS: User is Unable to Apply Coupon on an Invalid Calender Date");
            } else {
                System.out.println("CASE FAIL: User is able to Use Coupon on an Invalid Calender Date");
            }
        }
    }
}
