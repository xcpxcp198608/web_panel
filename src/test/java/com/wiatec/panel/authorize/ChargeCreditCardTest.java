package com.wiatec.panel.authorize;

import org.junit.Test;

public class ChargeCreditCardTest {
    @Test
    public void pay() throws Exception {

        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4111111111111111");
        authorizeTransactionInfo.setExpirationDate(1220);
        authorizeTransactionInfo.setAmount(99.00f);
        AuthorizeTransactionInfo pay = ChargeCreditCard.pay(authorizeTransactionInfo);
        System.out.println(pay);
    }

}