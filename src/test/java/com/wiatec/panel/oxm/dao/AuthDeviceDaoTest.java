package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthDeviceInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthDeviceDaoTest {

    @Autowired
    private AuthDeviceDao authDeviceDao;

    @Test
    public void countOne() {
        int i = authDeviceDao.countOne(new AuthDeviceInfo("ldevice", "ldevice"));
        System.out.println(i);
    }

    @Test
    public void selectOne() {
        AuthDeviceInfo ldevice = authDeviceDao.selectOne(new AuthDeviceInfo("ldevice"));
        System.out.println(ldevice);
    }
}