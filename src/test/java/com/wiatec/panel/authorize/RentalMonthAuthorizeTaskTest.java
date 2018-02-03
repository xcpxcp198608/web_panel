package com.wiatec.panel.authorize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class RentalMonthAuthorizeTaskTest {

    @Test
    public void executeUploadBackTask() {
        RentalMonthAuthorizeTask rentalMonthAuthorizeTask = new RentalMonthAuthorizeTask();
        rentalMonthAuthorizeTask.executeUploadBackTask();
    }
}