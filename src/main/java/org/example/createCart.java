package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createCart extends browserSetup{

    public createCart(String orderMode, String Location, boolean loggedIn, String orderTime, String time) throws InterruptedException {
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
            driver.findElement(By.id(homeDeliveryTextField)).sendKeys(readProperty("homeDeliveryAddress"));
            //We Need to Clear some Letters if Copy&Pasting/SendingKeys etc. to the Search Field for
            //Location Suggestions to Show up. We can remove this logic, Once Dev team fixes this up.
            for (int backspaceLetters=0;backspaceLetters<2;backspaceLetters++){
                driver.findElement(By.id(homeDeliveryTextField)).sendKeys(Keys.BACK_SPACE);
            }
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
        try {
            wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"Yes\"]")));
            //For Some reason even after Completed, We get Cart reset Popup, Handle it with Yes for now.
            driver.findElement(By.xpath("//button[@data-testid=\"Yes\"]")).click();
            System.out.println("Checking for Minimum Order Amount");
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Checking for Minimum Order Amount");
        }
        js.executeScript("window.scrollBy(0,2000)", "");
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[@data-testid=\"deliveryTitle\"]")));
        double minimumOrderAmount = 0;
        try {
            List<WebElement> restoInfo = driver.findElements(By.xpath("//div[@class=\"text-sm ng-star-inserted\"]"));
            for(WebElement e:restoInfo ){
                if(e.getText().contains("Minimum Order Amount")){
                    String regex = "[\\$£€₹]\\s*(\\d+(?:\\.\\d+)?)";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(e.getText());
                    if (matcher.find()) {
                        minimumOrderAmount = Integer.parseInt((matcher.group(1)));
                        System.out.println("Minimum Order Amount Set For Business is " + minimumOrderAmount);
                    }
                }
            }
            System.out.println("Choosing " + orderMode + " Order Timing");
        } catch (NoSuchElementException | TimeoutException e){
            System.out.println("Choosing " + orderMode + " Order Timing");
        }

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
        List<WebElement> priceList = driver.findElements(By.xpath("//h5[@class=\"item__price bg-app-gray-100 w-[65px] text-center rounded-full text-base font-bold ng-star-inserted\"]"));
        double minValue = Double.MAX_VALUE;
        int expectedItemQuantity = 1;
        boolean repeatItem = false;
        int minIndex = 0;
        for (int i = 1; i < priceList.size(); i++) {
            String text = priceList.get(i).getText().trim().substring(1); // Trim and remove first char
            double value = Double.parseDouble(text); // Convert to number
            if (value < minValue) {
                minValue = value;
                minIndex = i+1;
            }
        }
        if(minValue<minimumOrderAmount){
            repeatItem=true;
            if (minimumOrderAmount%minValue !=0){
                expectedItemQuantity = (int) (minimumOrderAmount/minValue+1);
            } else {
                expectedItemQuantity = (int) (minimumOrderAmount/minValue);
            }
        }
        System.out.println("Adding Cheapest Item to Cart Priced at " + driver.findElement(By.xpath("(//h5[@class=\"item__price bg-app-gray-100 w-[65px] text-center rounded-full text-base font-bold ng-star-inserted\"])[" + minIndex + "]")).getText());
        driver.findElement(By.xpath("(//h5[@class=\"item__price bg-app-gray-100 w-[65px] text-center rounded-full text-base font-bold ng-star-inserted\"])[" + minIndex + "]")).click();

        wait = new WebDriverWait(driver, 3);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"addToCart\"]")));
            js.executeScript("window.scrollBy(0,2000)", "");
            if (repeatItem){
                for (int i = 1; i < expectedItemQuantity; i++) {
                    driver.findElement(By.xpath("//button[@class=\"hover:bg-app-gray-200 rounded-full p-[3px] flex-shrink-0\"][2]")).click();
                }
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))).clear();
            driver.findElement(By.id("message")).sendKeys(readProperty("itemComment"));
            driver.findElement(By.xpath("//button[@data-testid=\"addToCart\"]")).click();
        }
        catch (NoSuchElementException | TimeoutException e){
            System.out.println("Adding non Customisable Item to the Cart.");
            if (repeatItem){
                driver.findElement(By.xpath("//a[@id=\"cart-header\"]")).click();
                for (int i = 1; i < expectedItemQuantity; i++){
                    driver.findElement(By.xpath("(//h5[@class=\"item__price bg-app-gray-100 w-[65px] text-center rounded-full text-base font-bold ng-star-inserted\"])[" + minIndex + "]")).click();
                }
            }
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
        if (loggedIn) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label=\"Select Payment Methods\"]")));
        }
    }
}
