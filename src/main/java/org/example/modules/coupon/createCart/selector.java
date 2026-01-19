package org.example.modules.coupon.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class selector extends browserSetup {
    public selector(String Location) throws InterruptedException {
        wait = new WebDriverWait(driver, 30);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']")));
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        System.out.println("Selected Location : " + Location);
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Continuing to Menu");
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
    }
}
