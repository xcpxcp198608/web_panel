package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.common.utils.ExcelExporter;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    public void selectAllExpiresUsers() {
        List<AuthRegisterUserInfo> authRegisterUserInfos = authRegisterUserDao.selectAllExpiresUsers(0);
        System.out.println(authRegisterUserInfos);
    }

    @Test
    public void selectOneByUsername1() {
        AuthRegisterUserInfo authRegisterUserInfo1 = new AuthRegisterUserInfo();
        authRegisterUserInfo1.setUsername("SUPERFUN");
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsername(authRegisterUserInfo1);
        System.out.println(authRegisterUserInfo);
    }

    @Test
    public void selectExportUsers() {
        String [] macs = {"5C:41:E7:00:8E:36", "5C:41:E7:00:8C:DB", "5C:41:E7:00:8B:69", "5C:41:E7:00:81:7A"};
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectExportUsers(macs);
        List<String> list = new ArrayList<>();
        try {
            Class clasz = Class.forName("com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo");
            Field[] fields = clasz.getDeclaredFields();
            for(Field field: fields){
                String fieldName = field.getName();
                list.add(fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = ExcelExporter.export(authRegisterUserInfoList, list, "register user information");
        System.out.println(path);
    }
}