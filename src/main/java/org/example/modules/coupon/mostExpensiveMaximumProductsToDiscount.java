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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class mostExpensiveMaximumProductsToDiscount extends browserSetup {
    public mostExpensiveMaximumProductsToDiscount() throws InterruptedException {
        new orderMode("Dine In");
        new selector("Dine In", readProperty("couponLocation"));
        new addMenuItem("Expensive Item 1");
        new addMenuItem("Expensive Item 2");
        new addMenuItem("Expensive Item 3");
        new cartHeader();
        new guestCheckout();
        new checkPreAppliedCoupon();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"MOSTEXPENSIVEMAXIMUMPRODUCTSTODISCOUNT\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));

        try{
            try {
                wait = new WebDriverWait(driver, 10);
                String couponValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class=\"text-center text-lg font-bold text-app-gray-900 first-letter:capitalize lowercase px-4\"]"))).getText();
                System.out.println("Validation Displayed : " + couponValidation);
            } catch (NoSuchElementException | TimeoutException ignored){}
            String appliedCouponName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid,'Coupon')]"))).getAttribute("data-testid");
            System.out.println("Applied Coupon Name is " + appliedCouponName);
            if (appliedCouponName.equalsIgnoreCase("Coupon - MostExpensive, Maximum Products To Discount")){
                System.out.println("CASE PASS: Most Expensive and Maximum Products To Discount Coupon was Applied Automatically");
            }

            List<WebElement> cartItemPrices = driver.findElements(By.xpath("//p[@class=\"hidden md:block ml-4 text-sm font-semibold text-app-gray-500\"]"));

            List<Double> cartItemsPrices = new ArrayList<>();

            for (WebElement items : cartItemPrices) {
                double price = Double.parseDouble(items.getText().trim().substring(1));
                cartItemsPrices.add(price);
            }
            cartItemsPrices.sort(Comparator.reverseOrder());

            double expensiveItemPrice1 = cartItemsPrices.get(0);
            double expensiveItemPrice2 = cartItemsPrices.get(1);

            double discountrate = 0.10;
            double discountedItemPrice1 = expensiveItemPrice1*discountrate;
            double discountedItemPrice2 = expensiveItemPrice2*discountrate;

            double totalExpectedDiscount = discountedItemPrice1+discountedItemPrice2;
            double displayedDiscount = Double.parseDouble(driver.findElement(By.xpath("//h6[contains(@data-testid,'Coupon')]")).getText().substring(2));
            System.out.println("Expected Discount : " + totalExpectedDiscount);
            System.out.println("Displayed Discount : " + displayedDiscount);
            if (totalExpectedDiscount==displayedDiscount){
                System.out.println("CASE Passed: Set Price Coupon on 2 Expensive Items Calculated Successfully");
            } else {
                System.out.println("CASE Failed: Set Price Coupon on 2 Expensive Items was not Calculated Successfully");
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
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("ERROR: Something Went Wrong with Coupon Application!");
        }
    }

}
