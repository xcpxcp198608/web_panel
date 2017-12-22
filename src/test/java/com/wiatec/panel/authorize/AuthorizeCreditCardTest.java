package com.wiatec.panel.authorize;

import org.junit.Test;

public class AuthorizeCreditCardTest {
    @Test
    public void pay() throws Exception {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4111111111111111");
        authorizeTransactionInfo.setExpirationDate(1220);
        authorizeTransactionInfo.setAmount(99.00f);
        AuthorizeTransactionInfo pay = AuthorizeCreditCard.pay(authorizeTransactionInfo);
        System.out.println(pay);
    }

}