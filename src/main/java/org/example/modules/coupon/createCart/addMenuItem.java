package org.example.modules.coupon.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class addMenuItem extends browserSetup {
    public addMenuItem(String itemName) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));

        String themeTag;
        //h5 is being used for Superb List View & h4 is being used for Superb
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='"+itemName+"']")));
            themeTag="h4";
        } catch (TimeoutException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='"+itemName+"']")));
            themeTag="h5";
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//"+themeTag+"[normalize-space()='"+itemName+"']")));
        driver.findElement(By.xpath("//"+themeTag+"[normalize-space()='"+itemName+"']")).click();

        wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).clear();
            driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Adding non Customisable Item to the Cart.");
        }
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,10)", "");
    }
}
