package com.wiatec.panel.oxm.dao;

import com.google.common.collect.Lists;
import com.wiatec.panel.oxm.pojo.LdGroupInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LdGroupDaoTest {

    @Resource
    private LdGroupDao ldGroupDao;

    @Test
    public void getGroupsByGroupIds() {
        List<Integer> groupIds = Lists.newArrayList();
        groupIds.add(411);
        groupIds.add(221081);
        List<LdGroupInfo> groups = ldGroupDao.getGroupsByGroupIds(groupIds);
        System.out.println(groups);
    }
}