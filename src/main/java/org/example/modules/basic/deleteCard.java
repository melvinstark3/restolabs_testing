package org.example.modules.basic;

import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class deleteCard extends org.example.commonUtils.payment.deleteCard {
    public void deleteCard(){
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class=\"card__number\"]")));
        String maskedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
        String cardEndingNumber = maskedCardNumber.substring(maskedCardNumber.length() - 4);
        System.out.print("Attempting to Delete Card Ending with : " + cardEndingNumber);
        String tempExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
        System.out.println(" with Details :" + tempExpiry);

        //Delete Card Action
        driver.findElement(By.xpath("//i[@class=\"fa fa-trash\"]")).click();
        String deleteConfirmation = driver.switchTo().alert().getText();
        System.out.println("User is being asked: " + deleteConfirmation);
        System.out.println("Accepting the Alert!");
        driver.switchTo().alert().accept();
        try {
            String recheckedCardNumber = driver.findElement(By.xpath("//p[@class=\"card__number\"]")).getText();
            String recheckedExpiry = driver.findElement(By.xpath("//p[@class=\"expiry__date\"]")).getText();
            if (Objects.equals(recheckedCardNumber, maskedCardNumber) && Objects.equals(recheckedExpiry, maskedCardNumber)) {
                System.out.println("CASE 6: FAIL: WARNING! Matching Card Details were found after Delete Attempt");
            } else {
                System.out.println("CASE 6: PASS: Saved Card was Deleted");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("ERROR! No Saved Cards were found. Please Verify the Payment Flow");
        }

    }
}
