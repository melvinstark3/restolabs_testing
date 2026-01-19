package org.example.modules.coupon;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class automaticLocationSpecificSetPriceCheapestItem extends browserSetup {

    public void checkValidation() throws InterruptedException {
        new selector("Dine In",readProperty("couponLocation"));
        new addMenuItem("Cheap Item 1");
        new cartHeader();
        new guestCheckout();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Automatic LocationSpecific SetPrice CheapestItem")){
                System.out.println("CASE FAILED: Automatic Coupon was Applied Automatically on a Separate Location");
            }
        } catch (NoSuchElementException | TimeoutException ignored){}
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        driver.findElement(By.xpath("//input[@data-testid=\"couponInput\"]")).sendKeys(readProperty("automaticCouponCode"));
        driver.findElement(By.xpath("//button[@data-testid=\"applyCoupon\"]")).click();
        try {
            wait = new WebDriverWait(driver, 10);
            String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
            if (couponValidation.contains("This coupon code is invalid or has expired.")){
                System.out.println("CASE PASS: Location Specific Coupon was not Applied on a Separate Location Manually");
            }
        } catch (NoSuchElementException | TimeoutException e){
            try{
                String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
                System.out.println("Applied Coupon Name is " + appliedCouponName);
                if (appliedCouponName.equalsIgnoreCase("Coupon - Automatic LocationSpecific SetPrice CheapestItem")){
                    System.out.println("CASE FAIL: Location Specific Coupon was Applied on a Separate Location Manually.");
                }
            } catch (NoSuchElementException | TimeoutException ignored){}
        }
        wait = new WebDriverWait(driver, 30);
    }

    public void verifyCoupon() throws InterruptedException {
        new orderMode("Dine In");
        new selector("Dine In", readProperty("automaticCouponLocation"));
        new addMenuItem("Cheap Item 1");
        new addMenuItem("Cheap Item 2");
        new addMenuItem("Cheap Item 3");
        new cartHeader();
        new guestCheckout();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        try{
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - Automatic LocationSpecific SetPrice CheapestItem")){
                System.out.println("CASE PASS: Location Specific Coupon was Applied Automatically");
            }
        } catch (NoSuchElementException | TimeoutException ignored){}

        List<WebElement> cartItemPrices = driver.findElements(By.xpath("//p[@class=\"hidden md:block ml-4 text-sm font-semibold text-app-gray-500\"]"));

        List<Double> cartItemsPrices = new ArrayList<>();

        for (WebElement items : cartItemPrices) {
            double price = Double.parseDouble(items.getText().trim().substring(1));
            cartItemsPrices.add(price);
        }
        Collections.sort(cartItemsPrices);

        double cheapestItemPrice1 = cartItemsPrices.get(0);
        double cheapestItemPrice2 = cartItemsPrices.get(1);

        double expectedPriceAfterDiscount = 9.00;

        double discountedItemPrice1 = cheapestItemPrice1 - expectedPriceAfterDiscount;
        if(discountedItemPrice1 <= 0){
            discountedItemPrice1=cheapestItemPrice1;
        }
        double discountedItemPrice2 = cheapestItemPrice2 - expectedPriceAfterDiscount;
        if(discountedItemPrice2 <= 0){
            discountedItemPrice2=cheapestItemPrice1;
        }

        double totalExpectedDiscount = discountedItemPrice1+discountedItemPrice2;
        double displayedDiscount = Double.parseDouble(driver.findElement(By.xpath("//h6[contains(@data-testid,'Coupon')]")).getText().substring(2));
        System.out.println("Expected Discount : " + totalExpectedDiscount);
        System.out.println("Displayed Discount : " + displayedDiscount);
        if (totalExpectedDiscount==displayedDiscount){
            System.out.println("CASE Passed: Set Price Coupon on 2 Cheapest Items Calculated Successfully");
        } else {
            System.out.println("CASE Failed: Set Price Coupon on 2 Cheapest Items was not Calculated Successfully");
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
