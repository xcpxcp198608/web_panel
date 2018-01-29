package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AuthRegisterUserDaoTest {

    @Autowired
    private AuthRegisterUserDao authRegisterUserDao;

    @Test
    public void countByUsername() {
    }

    @Test
    public void countByEmail() {
    }

    @Test
    public void countByMac() {
    }

    @Test
    public void saveOneUser() {
    }

    @Test
    public void updateEmailStatus() {
    }

    @Test
    public void countByUsernameAndPassword() {
    }

    @Test
    public void selectOneByToken() {
    }

    @Test
    public void selectOneByUsername() {
    }

    @Test
    public void selectOneByUsernameAndMac() {
    }

    @Test
    public void selectOneByUsernameAndEmail() {
    }

    @Test
    public void updateLocation() {
    }

    @Test
    public void updateToken() {
    }

    @Test
    public void updatePassword() {
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void updateEmailStatusById() {
    }

    @Test
    public void updateStatusById() {
    }

    @Test
    public void deleteOneById() {
    }

    @Test
    public void selectExpiresTimeById() {
    }

    @Test
    public void selectOneById() {
    }

    @Test
    public void updateLevelById() {
    }

    @Test
    public void selectAllLevelDistribution() {
        LevelDistributionInfo levelDistributionInfo = authRegisterUserDao.selectAllLevelDistribution();
        System.out.println(levelDistributionInfo);
    }

    @Test
    public void selectLevelOfYear() {
    }

    @Test
    public void selectVolumeOfYear() {
    }

    @Test
    public void selectVolumeOfMonth() {
    }
}