package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class applyRestoLoyalty extends browserSetup{
    public applyRestoLoyalty(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"openLoyaltyPopUp\"]"))).click();
        WebElement loyaltyInputField = driver.findElement(By.id("simple-coupon"));
        loyaltyInputField.clear();
        loyaltyInputField.sendKeys(readProperty("loyaltyValue"));
        driver.findElement(By.xpath("//button[@data-testid=\"loyaltySubmit\"]")).click();
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"tip__remove text-xs font-semibold text-red-600 bg-red-100 py-0.5 px-2 hover:bg-app-gray-100 transition-all duration-300 rounded-full ng-star-inserted\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(@data-testid, 'Loyalty Points')]")));
            System.out.println("CASE 31: FAIL: Loyalty Points has been applied for the Order");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE 31: FAIL: Loyalty Points application was Unsuccessful");
        }
    }
}
