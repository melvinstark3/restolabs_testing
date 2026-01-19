package org.example.modules.coupon;
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
                System.out.println("CASE PASS: Non-Whitelisted User is Unable to Use alloted Coupon");
            }
        } catch (NoSuchElementException | TimeoutException ignored){}
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"BLACKLISTUSERCOUPON\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Blacklist User Coupon")){
                System.out.println("CASE PASS: Non-Blacklisted User is able to Apply the Coupon");
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"placeOrder\"]"))).click();
            try {
                wait = new WebDriverWait(driver, 60);
                String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
                System.out.println("Order with Coupon Placed Successfully");
            } catch (NoSuchElementException | TimeoutException t) {
                System.out.println("Order with Coupon FAILED!");
            }
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE FAILED: Non-Blacklisted User is also Unable to Apply the Coupon");
        }
    }
}
