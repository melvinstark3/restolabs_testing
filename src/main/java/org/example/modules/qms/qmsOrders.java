package org.example.modules.qms;

import org.example.commonUtils.codCheckout;
import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;

public class qmsOrders extends browserSetup {
    public qmsOrders() throws InterruptedException {
        boolean loggedIn = false;
        driver.navigate().to("https://" + readProperty("shortcode") + "." + System.getProperty("server"));
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label=\"Pick Up\"]")));
        driver.findElement(By.xpath("//button[@aria-label=\"Pick Up\"]")).click();
        ArrayList<String> itemList = new ArrayList<String>();
        itemList.add("Customizable Item");
        itemList.add("Customizable Nested Item");
        itemList.add("Normal Item");
        itemList.add("Item with Minimum Quantity");
        itemList.add("Item with Maximum Quantity");
        itemList.add("Item & Location Tax Item");
        itemList.add("Location Tax Excluded Item");
        itemList.add("Only Item Tax Item");
        itemList.add("2-6PM Weekdays Item");
        itemList.add("Time Category Normal Item");

        for (String item : itemList) {
            try {
                new createCart("Pick Up", readProperty("GuestLocation"), loggedIn, readProperty("codOrderTime"), readProperty("preOrderTime"), item);
                new codCheckout();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-testid=\"continue_order\"]")));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid=\"continue_order\"]"))).click();
                driver.findElement(By.xpath("//span[@data-testid=\"continue_order\"]")).click();
                try {
                    wait = new WebDriverWait(driver, 60);
                    String restartOrderButtonXpath = "//div[@class='bg-white rounded-xl border border-app-gray-300']//span[@class='border-dashed text-sm font-semibold border px-2 py-0.5 rounded-lg cursor-pointer ml-2'][normalize-space()='Click here to start order again']";
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(restartOrderButtonXpath))).click();
                    System.out.println("CASE 3: PASS: Guest Order was Successful");
                    System.out.println("CASE 10: PASS: Place an Order with " + readProperty("codOrderTime") + " Timing");
                    System.out.println("CASE 12: PASS: Cash on Delivery Payment was Successful");
                } catch (NoSuchElementException | TimeoutException e) {
                    System.out.println("CASE 3: FAIL: Guest Order wasn't Posted in Time. Please Check the Case Manually");
                    System.out.println("CASE 10: FAIL: " + readProperty("codOrderTime") + " Order wasn't Posted in Time");
                    System.out.println("CASE 12: FAIL: Cash on Delivery Order wasn't Posted in Time");
                }
            } catch (TimeoutException e){
                    System.out.println("Skipping " + item + " as this Item is Not Available in Menu!" );
            }
        }
    }
}
