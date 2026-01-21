package org.example.modules.coupon;

import org.example.core.browserSetup;

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

        new allMatchingProductsMaxDiscountAmountOnlyCountApplicableMOQ().checkValidation();
        new allMatchingProductsMaxDiscountAmountOnlyCountApplicableMOQ().verifyCoupon();

        new mostExpensiveMaximumProductsToDiscount();

        new verifyWithSubtotalMinimumOrderValue().checkValidation();
        new verifyWithSubtotalMinimumOrderValue().verifyCoupon();

        new verifyWithOrderTotalMinimumOrderValue().checkValidation();
        new verifyWithOrderTotalMinimumOrderValue().verifyCoupon();

        new wednesdayOnlyCoupon();

        new exceptWednesdayRestrictedCoupon();

        new oneMinuteCoupon();

        new whitelistedUserCoupon();

        new hiddenDateSpecificCoupon();

        quitBrowser();

    }
}
