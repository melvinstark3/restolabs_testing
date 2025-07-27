package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class cardDetailsInput extends browserSetup{
    public cardDetailsInput(String cardNumber){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2000)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_mainPage_txt_CardNumber")));
        String OrderIDonGatewayPage = driver.findElement(By.id("ctl00_mainPage_lbl_WelcomeText")).getText();
        System.out.println("Entering Card Details for " + OrderIDonGatewayPage);

        driver.findElement(By.id("ctl00_mainPage_txt_CardNumber")).sendKeys(cardNumber);

        WebElement expMonth = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationMonth"));
        Select expMonthDropdown = new Select(expMonth);
        expMonthDropdown.selectByVisibleText(readProperty("expiryMonth"));

        WebElement expYear = driver.findElement(By.id("ctl00_mainPage_ddl_ExpirationYear"));
        Select expYearDropdown = new Select(expYear);
        expYearDropdown.selectByVisibleText(readProperty("expiryYear"));
        driver.findElement(By.id("txt_CVV")).sendKeys(readProperty("cvv"));
    }

}
