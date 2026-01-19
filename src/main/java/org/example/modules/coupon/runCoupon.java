package org.example.modules.coupon;

import org.example.commonUtils.checkPreAppliedCoupon;
import org.example.commonUtils.guestCheckout;
import org.example.commonUtils.payment.createCart;
import org.example.core.browserSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class runCoupon extends browserSetup {
    public runCoupon() throws InterruptedException {
        invokeBrowser();
        new orderMode("Dine In");
        new fixedDiscountTimeSpecific();

        new automaticLocationSpecificSetPriceCheapestItem().checkValidation();
        new automaticLocationSpecificSetPriceCheapestItem().verifyCoupon();

        new percentModeSpecificTaxExemptDeliveryFees().checkValidation();
        new percentModeSpecificTaxExemptDeliveryFees().verifyCoupon();

        new deliveryFeesOnly();

        new totalOfMatchingRequireAllProductsToMatchWhitelistItems().checkValidation();
        new totalOfMatchingRequireAllProductsToMatchWhitelistItems().verifyCoupon();

        new allMatchingProductsMaxDiscountAmountMOQ().checkValidation();
        new allMatchingProductsMaxDiscountAmountMOQ().verifyCoupon();

        new mostExpensiveMaximumProductsToDiscount();

    }
}
