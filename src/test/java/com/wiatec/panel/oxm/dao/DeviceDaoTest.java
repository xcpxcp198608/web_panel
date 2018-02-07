package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DeviceDaoTest {

    @Autowired
    private DeviceDao deviceDao;

    @Test
    public void selectAll() {
        List<DeviceInfo> deviceInfoList = deviceDao.selectAllWithEnable(0);
        System.out.println(deviceInfoList);
    }

    @Test
    public void insertOne() {
        int i = deviceDao.insertOne(new DeviceInfo("12:12:12:12:12:13"));
        System.out.println(i);
    }

    @Test
    public void updateOneToEnable() {
        int i = deviceDao.updateOneToEnable("12:12:12:12:12:12", "");
        System.out.println(i);
    }

    @Test
    public void bathInsert() {
        String [] macs = {"12:12:12:12:12:15", "12:12:12:12:12:16", "12:12:12:12:12:17"};
        int i = deviceDao.bathInsert(macs);
        System.out.println(i);
    }

}