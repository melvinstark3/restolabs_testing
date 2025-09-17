package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class applyCoupon extends browserSetup{
    public applyCoupon(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"viewCoupons\"]"))).click();
        String couponsXpath = "//span[@class=\"flex-shrink-0 py-2 px-4 text-sm text-black font-semibold rounded-xl truncate max-w-[150px]\"]";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(couponsXpath)));
        List<WebElement> couponsDisplayed = driver.findElements(By.xpath(couponsXpath));
        for(WebElement e : couponsDisplayed){
            if(e.getText().equalsIgnoreCase(readProperty("couponToApply"))){
                e.click();
                System.out.println("Applying Coupon " + e.getText());
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"coupon__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Coupon')]")));
    }
}
