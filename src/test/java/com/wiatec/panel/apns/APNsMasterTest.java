package com.wiatec.panel.apns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class APNsMasterTest {

    @Test
    public void send() {

    }

    @Test
    public void batchSend() {
        APNsMaster.batchSend(41, Arrays.asList(41), APNsMaster.ACTION_LIVE_START, "sf");
    }
}