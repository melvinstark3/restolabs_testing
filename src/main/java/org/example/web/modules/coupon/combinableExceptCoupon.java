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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class combinableExceptCoupon extends browserSetup {
    public combinableExceptCoupon() throws InterruptedException {
        new orderMode("Dine In");
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Normal Item");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        System.out.println("Applying Coupon");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"COMBINABLEEXCEPTCOUPON2\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"COMBINABLEEXCEPTCOUPON1\"]"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            try {
                wait = new WebDriverWait(driver, 10);
                String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
                System.out.println("Validation Displayed : " + couponValidation);
                if (couponValidation.contains("This coupon combination is not allowed. Please remove the previous coupon.\n")){
                    System.out.println("CASE PASS: Coupon was not Allowed on Selected Coupon in Exception List");
                }
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"failure__popup fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 ng-star-inserted\"]")));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Close Coupon Popup\"]"))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"COMBINABLECOUPON1\"]"))).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                try{
                    wait = new WebDriverWait(driver, 10);
                    couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
                    System.out.println("Validation Displayed : " + couponValidation);
                    if (couponValidation.contains("This coupon combination is not allowed. Please remove the previous coupon.\n")){
                        System.out.println("CASE FAIL: Coupon was not Allowed on Coupon that is not mentioned in Exception list");
                    }
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"failure__popup fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50 ng-star-inserted\"]")));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label=\"Close Coupon Popup\"]"))).click();
                } catch (TimeoutException e){
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]")));
                    List<WebElement> appliedCoupons = driver.findElements(By.xpath("//h6[contains(@data-testid,'Coupon')]"));
                    System.out.println("CASE: Coupon Combination: Names of Simultaneously Applied Coupons are as Follows: ");
                    for (WebElement coupon: appliedCoupons){
                        String displayedCoupon = coupon.getAttribute("data-testid");
                        System.out.println(displayedCoupon);
                    }
                    new placeOrder();
                }


            } catch (NoSuchElementException | TimeoutException e){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]")));
                List<WebElement> appliedCoupons = driver.findElements(By.xpath("//h6[contains(@data-testid,'Coupon')]"));
                System.out.println("Names of Simultaneously Applied Coupons are as Follows: ");
                for (WebElement coupon: appliedCoupons){
                    String displayedCoupon = coupon.getAttribute("data-testid");
                    System.out.println(displayedCoupon);
                    if (displayedCoupon.equalsIgnoreCase("Coupon - Combinable Coupon 1")){
                        System.out.println("CASE FAIL: Coupon was Allowed on a Coupon in Exception List ");
                    }
                }
            }
        } catch (NoSuchElementException | TimeoutException ignored){}

    }
}
