package com.wiatec.panel.oxm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthorizeTransactionRentalDaoTest {

    @Autowired
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;

    @Test
    public void countTotalCommissionByDealerId() {
        float dealer = authorizeTransactionRentalDao.countTotalCommissionByDealerId(12);
        System.out.println(dealer);
    }

    @Test
    public void countTotalCommissionBySalesId() {
        float sales = authorizeTransactionRentalDao.countTotalCommissionBySalesId(1);
        System.out.println(sales);
    }
}