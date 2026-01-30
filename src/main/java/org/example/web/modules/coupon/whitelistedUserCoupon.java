package org.example.web.modules.coupon;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class whitelistedUserCoupon extends browserSetup {
    public whitelistedUserCoupon() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"WHITELISTUSERCOUPON\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try {
            wait = new WebDriverWait(driver, 10);
            String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
            System.out.println("Validation Displayed : " + couponValidation);
            if (couponValidation.contains("Your user ID is not allowed to use this coupon.")){
                System.out.println("CASE PASS: Non-Whitelisted User is Unable to Use allotted Coupon");
            }
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"failure__popup fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 ng-star-inserted\"]")));
        } catch (NoSuchElementException | TimeoutException e){
            try{
                String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
                System.out.println("Applied Coupon Name is " + appliedCouponName);
                if (appliedCouponName.equalsIgnoreCase("Coupon - Whitelist User Coupon")){
                    System.out.println("CASE FAIL: Non-Whitelisted User is able to Apply the Coupon");
                }
                new placeOrder();
            } catch (NoSuchElementException | TimeoutException ignored){}
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"BLACKLISTUSERCOUPON\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Blacklist User Coupon")){
                System.out.println("CASE PASS: Non-Blacklisted User is able to Apply the Coupon");
            }
            new placeOrder();
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE FAILED: Non-Blacklisted User is Unable to Apply the Coupon");
        }
    }
}
