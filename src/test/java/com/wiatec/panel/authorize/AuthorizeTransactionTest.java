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
     * @throws Exception
     */
    @Test
    public void charge() throws Exception {
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
//        authorizeTransactionInfo.setCardNumber("411111111111111111111");
//        authorizeTransactionInfo.setExpirationDate("0319");
//        authorizeTransactionInfo.setSecurityKey("167");
        authorizeTransactionInfo.setAmount(32.39F);
        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo, "91ec5551fcf0727f");
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