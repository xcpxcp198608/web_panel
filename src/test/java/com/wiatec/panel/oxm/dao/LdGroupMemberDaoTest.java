package com.wiatec.panel.oxm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class LdGroupMemberDaoTest {

    @Resource
    private LdGroupMemberDao ldGroupMemberDao;

    @Test
    public void insertOne() {
    }

    @Test
    public void deleteAllByGroupId() {
    }

    @Test
    public void selectAllMembersById() {
    }

    @Test
    public void selectGroupIdByMemberId() {
        List<Integer> integers = ldGroupMemberDao.selectGroupIdByMemberId(41);
        System.out.println(integers);
    }

    @Test
    public void countByGroupIdAndMemberId() {
    }

    @Test
    public void deleteOne() {
    }
}