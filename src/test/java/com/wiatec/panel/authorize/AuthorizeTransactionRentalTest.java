package com.wiatec.panel.authorize;

import org.junit.Test;

public class AuthorizeTransactionRentalTest {

    @Test
    public void charge() {
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setCardNumber("4147202271733634");
        authorizeTransactionRentalInfo.setExpirationDate("0819");
        authorizeTransactionRentalInfo.setAmount(0.99f);
        authorizeTransactionRentalInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionRentalInfo pay = new AuthorizeTransactionRental().charge(authorizeTransactionRentalInfo);
        System.out.println(pay);
    }

    @Test
    public void authorize() {
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setCardNumber("4147202271733634");
        authorizeTransactionRentalInfo.setExpirationDate("0819");
        authorizeTransactionRentalInfo.setAmount(0.99f);
        authorizeTransactionRentalInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionRentalInfo pay = new AuthorizeTransactionRental().authorize(authorizeTransactionRentalInfo);
        System.out.println(pay);
    }

    @Test
    public void refund() {
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setCardNumber("4147202271733634");
        authorizeTransactionRentalInfo.setExpirationDate("0819");
        authorizeTransactionRentalInfo.setAmount(0.99f);
        authorizeTransactionRentalInfo.setTransactionId("40479497411");
        authorizeTransactionRentalInfo.setTransactionId(System.currentTimeMillis()+"");
        AuthorizeTransactionRentalInfo pay = new AuthorizeTransactionRental().refund(authorizeTransactionRentalInfo);
        System.out.println(pay);
    }
}