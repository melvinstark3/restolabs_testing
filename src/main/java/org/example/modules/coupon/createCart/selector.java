package org.example.modules.coupon.createCart;

import org.example.core.browserSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class selector extends browserSetup {
    public selector(String orderMode, String Location) throws InterruptedException {
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
            String homeDeliveryTextField = "typeahead-prevent-manual-entry";
            driver.findElement(By.id(homeDeliveryTextField)).clear();
            Thread.sleep(3000);
            driver.findElement(By.id(homeDeliveryTextField)).sendKeys(readProperty("homeDeliveryAddress"));
            //We Need to Clear some Letters if Copy&Pasting/SendingKeys etc. to the Search Field for
            //Location Suggestions to Show up. We can remove this logic, Once Dev team fixes this up.
            for (int backspaceLetters=0;backspaceLetters<4;backspaceLetters++){
                driver.findElement(By.id(homeDeliveryTextField)).sendKeys(Keys.BACK_SPACE);
                Thread.sleep(500);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ngb-typeahead-0")));
            Thread.sleep(5000);
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
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        }
//        try{
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
//        } catch (TimeoutException ignored){}
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[normalize-space()='" + Location + "']"))).click();
        js.executeScript("window.scrollBy(0,2000)", "");
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
        } catch (NoSuchElementException | TimeoutException ignored) {}
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")));
        driver.findElement(By.xpath("(//button[@data-testid=\"chooserContinue\"])[2]")).click();
        try {
            driver.findElement(By.xpath("//button[@data-testid=\"guestLogin\"]")).click();
            System.out.println("Login Prompt was displayed! Continuing at Guest.");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Login Prompt was not Displayed! Continuing to Menu");
        }
    }
}
