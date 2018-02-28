package com.wiatec.panel.authorize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthorizeTransactionTest {

    
    @Test
    public void charge() {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber("4111111111111111");
        authorizeTransactionInfo.setExpirationDate("1223");
        authorizeTransactionInfo.setSecurityKey("123");
        authorizeTransactionInfo.setAmount(100F);
        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo);
        System.out.println(charge);
    }

    @Test
    public void charge1() {
    }

    @Test
    public void authorize() {
    }

    @Test
    public void refund() {
    }
}