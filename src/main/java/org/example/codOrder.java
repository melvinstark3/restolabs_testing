package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.List;

public class codOrder extends browserSetup{
    public codOrder() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to(readProperty("GuestURL"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String Location = readProperty("GuestLocation");
        new createCart("Pick Up",Location,loggedIn, readProperty("codOrderTime"),"");
        wait = new WebDriverWait(driver, 30);
        System.out.println("Entering Customer Details");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\"first_name\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\"first_name\"]")).sendKeys(readProperty("GuestFirstName"));
        driver.findElement(By.xpath("//input[@data-testid=\"last_name\"]")).sendKeys(readProperty("GuestLastName"));
        driver.findElement(By.xpath("//input[@data-testid=\"phone\"]")).sendKeys(readProperty("GuestPhoneNumber"));
        driver.findElement(By.name("email")).sendKeys(readProperty("GuestEmail"));
        driver.findElement(By.xpath("//button[@class=\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\"]")).click();
        // Stale Element Exception if Trying Implicit Wait
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("\"//button[@class=\\\"primary_button w-full text-base font-semibold p-3 px-5 mr-3 rounded-2xl border capitalize text-white ng-star-inserted\\\"]\"")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        js.executeScript("window.scrollBy(0,2000)", "");
        System.out.println("CASE 3: For Guest Order: ");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        Thread.sleep(2000);
        // The System automatically selects back the PaymentMode0 regardless of click, We can remove sleep once dev team fixes this
        try{
            wait = new WebDriverWait(driver, 3);
            if (driver.findElement(By.id("policy")).isSelected()) {
                System.out.println("Privacy Policy and Terms & Conditions are Already Accepted");
            } else {
                driver.findElement(By.id("policy")).click();
                System.out.println("Privacy Policy and Terms & Conditions Accepted");
            }
        }
        catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Privacy Policy and Terms and Conditions Checkbox is Not Displayed");
        }
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"orderTotal\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")));
        driver.findElement(By.xpath("//input[@data-testid=\""+readProperty("CODPaymentMode")+"\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        try {
            wait = new WebDriverWait(driver, 60);
            String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath)));
            System.out.println("CASE 3: PASS: Guest Order was Successful");
            System.out.println("CASE 10: PASS: Place an Order with " + readProperty("codOrderTime") + "Timing");
            System.out.println("CASE 12: PASS: Cash on Delivery Payment was Successful");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("CASE 3: FAIL: Guest Order wasn't Posted in Time. Please Check the Case Manually");
            System.out.println("CASE 10: FAIL: " + readProperty("codOrderTime") + " Order wasn't Posted in Time");
            System.out.println("CASE 12: FAIL: Cash on Delivery Order wasn't Posted in Time");
        }

    }
}
