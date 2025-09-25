package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class applyPartialPoints extends browserSetup{
    public applyPartialPoints() throws InterruptedException {
        try{
            String deliveryFee = driver.findElement(By.xpath("//h6[@data-testid=\"Delivery Fee\"]")).getText();
            System.out.println("CASE 11: PASS: Delivery Fee Displayed at Checkout is " + deliveryFee);
        }catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE 11: FAIL: No Delivery Fee Found!");
        }
        driver.findElement(By.xpath("//button[normalize-space()='Use Points']")).click();
        System.out.println("Entering Como Points after deducting Tax Amount!");
        WebElement comoPointsField = driver.findElement(By.id("simple-coupon"));
        comoPointsField.clear();
        comoPointsField.sendKeys(readProperty("partialPointsValue"));
        driver.findElement(By.xpath("//button[@class=\"text-white absolute top-0 right-0 font-medium rounded-xl text-sm px-4 py-2 w-[25%] h-[50px] capitalize\"]")).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("OnlinePaymentMode")+"\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
    }
}
