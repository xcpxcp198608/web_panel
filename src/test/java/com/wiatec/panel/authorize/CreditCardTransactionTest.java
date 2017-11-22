package com.wiatec.panel.authorize;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreditCardTransactionTest {
    @Test
    public void pay() throws Exception {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.pay(99.00f);
    }

}