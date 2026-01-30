package org.example.web.modules.cod;

import org.example.core.browserSetup;
import org.example.web.commonUtils.guestCheckout;
import org.example.web.commonUtils.payment.createCart;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class runCod extends browserSetup {
    public runCod() throws InterruptedException {
        invokeBrowser();
        boolean loggedIn = false;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        new createCart(readProperty("GuestLocation"),readProperty("guestOrderItem"),loggedIn);
        new guestCheckout();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
        driver.findElement(By.xpath("//span[@data-testid=\"continue_order\"]")).click();
        try {
            wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pl-1']")));
            System.out.println("PASS: Cash On Delivery Order Placed Successfully");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("FAIL: Cash On Delivery Order Failed");
        }
        quitBrowser();
    }
}
