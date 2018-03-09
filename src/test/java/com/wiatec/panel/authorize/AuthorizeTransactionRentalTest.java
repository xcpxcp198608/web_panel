package com.wiatec.panel.authorize;

import org.junit.Test;

public class AuthorizeTransactionRentalTest {

    @Test
    public void charge() throws Exception {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4147202271733634");
        authorizeTransactionInfo.setExpirationDate("0819");
        authorizeTransactionInfo.setAmount(0.99f);
        authorizeTransactionInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo, "fsdf");
        System.out.println(charge);
    }

    @Test
    public void authorize() {

    }

    @Test
    public void refund() {

    }
}