package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DevicePCPInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DevicePCPDaoTest {

    @Autowired
    private DevicePCPDao devicePCPDao;

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
        List<DevicePCPInfo> devicePCPInfoList = devicePCPDao.selectAll();
        System.out.println(devicePCPInfoList);
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

    @Test
    public void bathUpdateDeviceToSpecialSales() {
        String[] macs = {"5C:41:E7:00:9A:E2", "5C:41:E7:00:98:1C"};
        devicePCPDao.bathUpdateDeviceToSpecialSales(macs, 1, 1, 1);
    }
}