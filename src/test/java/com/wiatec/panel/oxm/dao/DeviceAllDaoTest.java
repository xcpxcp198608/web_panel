package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DeviceAllDaoTest {

    @Autowired
    private DeviceAllDao deviceAllDao;

    @Test
    public void selectAll() {
        List<DeviceAllInfo> deviceAllInfoList = deviceAllDao.selectAll(100);
        System.out.println(deviceAllInfoList);
    }
}