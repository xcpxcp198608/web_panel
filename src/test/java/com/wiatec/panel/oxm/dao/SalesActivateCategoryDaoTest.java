package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SalesActivateCategoryDaoTest {

    @Autowired
    private SalesActivateCategoryDao salesActivateCategoryDao;

    @Test
    public void selectAll() {
        List<SalesActivateCategoryInfo> salesActivateCategoryInfoList = salesActivateCategoryDao.selectAll();
        System.out.println(salesActivateCategoryInfoList);
    }

    @Test
    public void selectOneByCategory() {
        SalesActivateCategoryInfo salesActivateCategoryInfo = salesActivateCategoryDao.selectOneByCategory("AM1");
        System.out.println(salesActivateCategoryInfo);
    }
}