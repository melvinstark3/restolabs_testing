package org.example.web.commonUtils;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class checkPreAppliedCoupon extends browserSetup {
    public checkPreAppliedCoupon(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
            boolean couponPreApplied = driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).isDisplayed();
            if (couponPreApplied){
                System.out.println(driver.findElement(By.xpath("//h6[contains(@data-testid, 'Coupon')]")).getAttribute("data-testid") + " is Already Applied.");
                driver.findElement(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")).click();
                System.out.println("WARNING! Pre Applied Coupon was found at Checkout! Trying to Remove it!");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]")));
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
                    System.out.println("ERROR! Unable to Remove Pre-Applied Coupon");
                } catch (NoSuchElementException | TimeoutException e){
                    System.out.println("Pre-Applied Coupon was Removed! Proceeding!");
                }
            }
        } catch (NoSuchElementException ignored){}
    }
}
