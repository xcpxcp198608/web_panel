package com.wiatec.panel.smack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class IMManagerTest {

    @Test
    public void createAccount() {
        IMManager.getInstance().createAccount("sdfs");
    }
}