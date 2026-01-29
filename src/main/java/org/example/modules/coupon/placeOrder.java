package org.example.modules.coupon;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class placeOrder extends browserSetup {
    public placeOrder(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        String selectedPaymentMode = driver.findElement(By.xpath("//input[contains(@aria-label,'Selected')]")).getAttribute("data-testid");
        System.out.println("Selected Payment Mode is " + selectedPaymentMode);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            wait = new WebDriverWait(driver, 5);
            WebElement farAddressValidation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@class=\"warning-info font-bold text-lg text-app-gray-500 text-center ng-star-inserted\"]")));
            System.out.println("Validation Displayed : \"" + farAddressValidation.getText() + "\"");
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
            System.out.println("\nContinuing Order with Validation Prompt!");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("No Validation Prompt was Displayed!");
        }

        try {
            wait = new WebDriverWait(driver, 60);
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
            System.out.println("Order with Coupon Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Order with Coupon FAILED!");
        }
    }
}
