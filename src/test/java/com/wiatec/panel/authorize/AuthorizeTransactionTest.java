package com.wiatec.panel.authorize;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorizeTransactionTest {

    @Test
    public void charge() {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4147202271733634");
        authorizeTransactionInfo.setExpirationDate("0819");
        authorizeTransactionInfo.setAmount(0.99f);
        authorizeTransactionInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionInfo pay = AuthorizeTransaction.charge(authorizeTransactionInfo);
        System.out.println(pay);
    }

    @Test
    public void authorize() {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4147202271733634");
        authorizeTransactionInfo.setExpirationDate("0819");
        authorizeTransactionInfo.setAmount(0.99f);
        authorizeTransactionInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionInfo pay = AuthorizeTransaction.authorize(authorizeTransactionInfo);
        System.out.println(pay);
    }

    @Test
    public void refund() {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4147202271733634");
        authorizeTransactionInfo.setExpirationDate("0819");
        authorizeTransactionInfo.setAmount(0.99f);
        authorizeTransactionInfo.setTransactionId("");
        authorizeTransactionInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionInfo pay = AuthorizeTransaction.refund(authorizeTransactionInfo);
        System.out.println(pay);
    }
}