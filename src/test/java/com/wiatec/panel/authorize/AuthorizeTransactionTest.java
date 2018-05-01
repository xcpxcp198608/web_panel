package com.wiatec.panel.authorize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthorizeTransactionTest {

    /**
     * PS: change environment (/Users/xuchengpeng/IdeaProjects/panel/out/test/classes/authorize.xml)
     *     to product and change xml path to local
     */
    @Test
    public void charge() throws Exception {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
//        authorizeTransactionInfo.setCardNumber("4168340001318104");
//        authorizeTransactionInfo.setExpirationDate("1121");
//        authorizeTransactionInfo.setSecurityKey("606");
        authorizeTransactionInfo.setAmount(172.79F);
        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo,
                "c6b707c73a75dd21");
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