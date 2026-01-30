package org.example.web.modules.restoclient;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class runRestoclient extends browserSetup {
    public runRestoclient() throws InterruptedException {
        invokeBrowser();
        boolean pickupMode=false;
        boolean homeDeliveryMode=false;
        boolean dineInMode=false;
        wait = new WebDriverWait(driver, 30);
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        try{
            pickupMode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]"))).isDisplayed();
        } catch (TimeoutException e){
            System.out.println("Pickup Order will be skipped as Mode is not displayed!");
        }
        try{
            homeDeliveryMode = driver.findElement(By.xpath("//button[@aria-label=\"Home Delivery\"]")).isDisplayed();
        } catch (NoSuchElementException e){
            System.out.println("Home Delivery Order will be skipped as Mode is not displayed!");
        }
        try{
            dineInMode = driver.findElement(By.xpath("//button[@aria-label=\"Home Delivery\"]")).isDisplayed();
        } catch (NoSuchElementException e){
            System.out.println("Dine In Order will be skipped as Mode is not displayed!");
        }
        if (pickupMode) {
            new pickupOrder();
            quitBrowser();
        }
        if (homeDeliveryMode) {
            invokeBrowser();
            new homeDeliveryOrder();
        }
        if (dineInMode) {
            new dineInOrder();
        }
        quitBrowser();
    }
}