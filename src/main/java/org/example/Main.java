package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class Main extends browserSetup{
    public static void main(String[] args) throws InterruptedException {
        invokeBrowser();
        boolean pickupMode=false;
        boolean homeDeliveryMode=false;
        boolean dineInMode=false;
        driver.navigate().to(readProperty("storeURL"));
        try{
            pickupMode = driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).isDisplayed();
        } catch (NoSuchElementException e){
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
        }
        if (homeDeliveryMode) {
            new homeDeliveryOrder();
        }
        if (dineInMode) {
            new dineInOrder();
        }
        quitBrowser();
    }
}