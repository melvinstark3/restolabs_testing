package org.example.commonUtils.payment;

import org.example.core.browserSetup;

public abstract class paymentsHelper extends browserSetup{

    public void cardDetailsInput(String cardNumber) throws InterruptedException{};

    public void gatewayPageCancellation(){};

    public void sharedURLGateway(boolean loggedIn) throws InterruptedException{};
}
