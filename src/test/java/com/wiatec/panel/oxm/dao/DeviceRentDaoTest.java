package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceRentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DeviceRentDaoTest {

    @Autowired
    private DeviceRentDao deviceRentDao;

    @Test
    public void countOne() {
    }

    @Test
    public void selectSalesIdByMac() {
    }

    @Test
    public void selectOneByMac() {
    }

    @Test
    public void selectAll() {
        List<DeviceRentInfo> deviceRentInfoList = deviceRentDao.selectAll();
        System.out.println(deviceRentInfoList);
    }

    @Test
    public void insertOne() {
    }

    @Test
    public void updateRented() {
    }

    @Test
    public void countNoRentedBySalesId() {
    }
}