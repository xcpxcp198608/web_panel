package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CommissionCategoryDaoTest {

    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    @Test
    public void selectAllByDistributor() {
    }

    @Test
    public void selectOne() {
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne("M2");
        System.out.println(commissionCategoryInfo);
    }
}