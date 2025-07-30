package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

public class defaultSaveCardCheckbox extends browserSetup{

    public defaultSaveCardCheckbox() {
        try {
            WebElement checkbox = driver.findElement(By.name("saveCard"));
            String saveCardStatement = driver.findElement(By.xpath("//div[@class=\"Switch_label__gc1yK\"]")).getText();

            if (checkbox.isSelected()) {
                System.out.println("TC_39: PASS - Save Card is Enabled by Default");
                System.out.println("Save Card Statement is '" + saveCardStatement + "'");
                if (saveCardStatement.equals("Save this card for future payments")) {
                    System.out.println();
                }
            } else {
                System.out.println("TC_39: FAIL - Save Card is not Enabled By Default");
                driver.findElement(By.xpath("//div[@class=\"Switch_label__gc1yK\"]")).click();
                System.out.println("Enabling Saved Card Toggle");
            }

        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Save Card Checkbox is not Displayed");
        }
    }

}
