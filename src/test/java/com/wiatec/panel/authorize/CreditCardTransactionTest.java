package com.wiatec.panel.authorize;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTransactionTest {
    @Test
    public void pay() throws Exception {
        AuthorizePayInfo authorizePayInfo = new AuthorizePayInfo();
        authorizePayInfo.setCardNumber("4111111111111111");
        authorizePayInfo.setExpirationDate(1220);
        authorizePayInfo.setAmount(99.00f);
        CreditCardTransaction.pay(authorizePayInfo);
    }

}