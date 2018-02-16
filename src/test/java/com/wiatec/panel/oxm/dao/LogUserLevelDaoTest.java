package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.log.LogUserLevelInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LogUserLevelDaoTest {

    @Autowired
    private LogUserLevelDao logUserLevelDao;

    @Test
    public void selectAll() {
        List<LogUserLevelInfo> logUserLevelInfos = logUserLevelDao.selectAll();
        System.out.println(logUserLevelInfos);
    }

    @Test
    public void insertOne() {
    }
}