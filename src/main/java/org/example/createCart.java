package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createCart extends browserSetup{

    public createCart(String orderMode, String Location, boolean loggedIn, String orderTime, String time, String item) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        if(orderMode.equalsIgnoreCase("Pick Up")){
            try{
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("geocoder")));
                // EXPERIMENTAL: Right now there is no Robust or Sure way to find when the Current location has been fetched
                // Upon Further Investigation it was found that a green Marker with image Xpath Mentioned below
                // "//img[@src="https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png"]"
                // is displayed when Current Location has been fetched but as there no proper way to check for its visibility Hence for now
                // We are using 10 seconds of Sleep to Give the API time to fetch Location. We can also experiment with a JS Executor but
                // for now we are going ahead with this Experimental Approach until Further Discussion. Till Then, It's Recommended to
                // Not Use Pickup Map Feature when Running the Automation Suite
                Thread.sleep(10000);
                WebElement location = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']")));
                js.executeScript("arguments[0].scrollIntoView();", location);
                location.click();
                driver.findElement(By.xpath("//button[@class=\"px-3 py-1 font-bold text-white border-2 text-xs md:text-sm rounded-md w-full select-button\"]")).click();
                System.out.println("Confirming Location");
            } catch (NoSuchElementException | TimeoutException e){
                System.out.println("Selecting Pickup Location from the List");
            }
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
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
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"guestLogin\"]")).click();
            System.out.println("Login Prompt was displayed! Continuing at Guest.");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Login Prompt was not Displayed! Continuing to Menu");
        }
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"item_title_html\"]")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class=\"item_title_html\"]")));
        //h5 is being used for Superb List View & h4 is being used for Superb
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='"+item+"']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h4[normalize-space()='"+item+"']")));
        driver.findElement(By.xpath("//h4[normalize-space()='"+item+"']")).click();
        wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@tooltip=\"Copy Link\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).clear();
            driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Adding non Customisable Item to the Cart.");
        }
        wait = new WebDriverWait(driver, 30);
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
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"guestLogin\"]")).click();
            System.out.println("Login Prompt was displayed! Continuing at Guest.");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Login Prompt was not Displayed! Continuing to Checkout");
        }
        wait = new WebDriverWait(driver, 30);
    }
}
