package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LogPcpUserMonthlyMexicoDaoTest {

    @Resource
    private LogPcpUserMonthlyMexicoDao logPcpUserMonthlyMexicoDao;
    @Resource
    private AuthRentUserDao authRentUserDao;

    @Test
    public void insertOne() {
        AuthRentUserInfo authRentUserInfo = authRentUserDao.selectOneByClientKey("b214159e7b9df805");
        int i = logPcpUserMonthlyMexicoDao.insertOne(authRentUserInfo);
        System.out.println(i);
    }

    @Test
    public void batchInsert(){
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectAll();
        int i = logPcpUserMonthlyMexicoDao.batchInsert(authRentUserInfoList);
        System.out.println(i);
    }

    @Test
    public void countOneByMac() {

    }

    @Test
    public void selectByMonth() {
        List<AuthRentUserInfo> authRentUserInfoList = logPcpUserMonthlyMexicoDao
                .selectByMonth(YearOrMonthInfo.createNextMonthly(2018, 2, 101));
        System.out.println(authRentUserInfoList);
    }
}