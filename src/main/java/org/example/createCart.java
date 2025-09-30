package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.List;

public class createCart extends browserSetup{

    public createCart(String orderMode, String Location, boolean loggedIn, String orderTime, String time, String itemName) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
        if (orderMode.equalsIgnoreCase("Home Delivery")){
            try {
                // The Button Class keeps changing, Use Contains method on Second Button if displayed
                WebElement editAddressButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[contains(@class, 'mode__change__button transition-all duration-300 hover:scale-100 scale-[0.85]')])[2]")));
                //Use JS Executor for Edit Address SVG till Dev Team doesn't add a Proper CssSelector
                js.executeScript("arguments[0].click();", editAddressButton);
                System.out.println("Editing the Locked Home Delivery Field");
            } catch (TimeoutException e){
                System.out.println("Home Delivery Field is Empty!");
            }
            driver.findElement(By.id("typeahead-prevent-manual-entry")).clear();
            driver.findElement(By.id("typeahead-prevent-manual-entry")).sendKeys(readProperty("homeDeliveryAddress"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ngb-typeahead-0")));
            List<WebElement> addressList = driver.findElements(By.id("ngb-typeahead-0"));
            for (WebElement e:addressList){
                //Right Now even with Same Text, it's choosing the Suggestion After Same Text but as Addresses might
                // Differ from Suggestion List we are choosing the Contains method as leaving it as it is.
                // We Can Change this later if needed or List Suggestions will be displayed better with Css Selectors from Dev Team
                if (e.getText().contains(readProperty("homeDeliveryAddress"))){
                    e.click();
                    break;
                }
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
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
        if(orderTime.equalsIgnoreCase("asap")){
            driver.findElement(By.id("asapbtn")).click();
        }
        else{
            driver.findElement(By.id("laterbtn")).click();
            WebElement selectorTime = driver.findElement(By.xpath("//select[@aria-label=\"Select Time\"]"));
            Select selectorTimeDropDown = new Select(selectorTime);
            selectorTimeDropDown.selectByValue(time);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"item__text h-full\"]")));
        WebElement itemToOrder = driver.findElement(By.xpath("//h4[normalize-space()='"+itemName+"']"));
        js.executeScript("arguments[0].scrollIntoView();", itemToOrder);
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='"+itemName+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+itemName+"']")).click();

        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).clear();
            driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
            System.out.println("CASE 27: Passing Item Comment for Customizable Item");
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Adding non Customisable Item to the Cart.");
        }
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0,10)", "");
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Cart Popup Appeared Automatically. Proceeding to Checkout");
        }
        catch (NoSuchElementException | TimeoutException e){
            driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
            driver.findElement(By.xpath("//button[@data-testid=\"goToCheckout_desktop\"]")).click();
            System.out.println("Proceeding to Checkout");
        }
        wait = new WebDriverWait(driver, 30);
        if (loggedIn) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label=\"Select Payment Methods\"]")));
        }
    }
}
