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
     *
     *     /Users/xuchengpeng/IdeaProjects/panel/src/main/java/com/wiatec/panel/authorize/AuthorizeTransaction.java
     *     change path to local
     */
    @Test
    public void charge() throws Exception {
//        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
//        authorizeTransactionInfo.setCardNumber("4789620302122803");
//        authorizeTransactionInfo.setExpirationDate("0321");
//        authorizeTransactionInfo.setSecurityKey("927");
//        authorizeTransactionInfo.setAmount(172.79F);
//        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo,
//                "123131313");
//        System.out.println(charge);

        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
//        authorizeTransactionInfo.setCardNumber("4254215852683852");
//        authorizeTransactionInfo.setExpirationDate("0719");
//        authorizeTransactionInfo.setSecurityKey("665");
//        authorizeTransactionInfo.setAmount(32.39F);
        AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo,
                "123131313");
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