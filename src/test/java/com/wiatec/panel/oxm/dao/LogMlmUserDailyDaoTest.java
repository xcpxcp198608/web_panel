package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LogMlmUserDailyDaoTest {

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private LogMlmUserDailyDao logMlmUserDailyDao;

    @Test
    public void batchInsert() {
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectAll(5850);
        System.out.println(authRegisterUserInfoList);
        int i = logMlmUserDailyDao.batchInsert(authRegisterUserInfoList);
        System.out.println(i);
    }
}