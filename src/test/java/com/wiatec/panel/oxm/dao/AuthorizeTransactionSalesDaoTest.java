package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionSalesInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthorizeTransactionSalesDaoTest {

    @Autowired
    private AuthorizeTransactionSalesDao authorizeTransactionSalesDao;

    @Test
    public void insertOne() {
        AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo = new AuthorizeTransactionSalesInfo();
        authorizeTransactionSalesInfo.setSalesId(1);
        authorizeTransactionSalesInfo.setCardNumber("234234234");
        authorizeTransactionSalesInfo.setExpirationDate("sdfs");
        authorizeTransactionSalesInfo.setSecurityKey("sdf");
        authorizeTransactionSalesInfo.setTransactionId("23423423");
        authorizeTransactionSalesInfo.setAmount(4324F);
        authorizeTransactionSalesInfo.setAuthCode("sdf");
        authorizeTransactionSalesInfo.setStatus("approved");
        authorizeTransactionSalesInfo.setGoldCategory("s1");
        authorizeTransactionSalesInfo.setGoldPay(1000F);
        authorizeTransactionSalesInfo.setActivateCategory("am1");
        authorizeTransactionSalesInfo.setActivatePay(12F);
        authorizeTransactionSalesDao.insertOne(authorizeTransactionSalesInfo);
    }

    @Test
    public void selectAll() {
        List<AuthorizeTransactionSalesInfo> authorizeTransactionSalesInfoList =
                authorizeTransactionSalesDao.selectAll();
        System.out.println(authorizeTransactionSalesInfoList);
    }
}