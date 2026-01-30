package org.example.web.modules.jccpay;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class matchAmount extends org.example.web.commonUtils.payment.matchAmount {
    public void matchAmount(String checkoutOrderTotal) {
        wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"Payment amount\"]")));
        String gatewayOrderTotal = driver.findElement(By.xpath("//span[@aria-label=\"Payment amount\"]")).getText();
        if(Objects.equals(checkoutOrderTotal, gatewayOrderTotal)){
            System.out.println("TC 56, 30: PASS: Checkout Order Total and Currency ("+ checkoutOrderTotal +") Matches with Gateway " + gatewayOrderTotal);
        }
        else {
            System.out.println("TC 56, 30: FAIL: Checkout Order Total and Currency ("+ checkoutOrderTotal +") doesn't Match with Gateway " + gatewayOrderTotal);
        }
    }
}