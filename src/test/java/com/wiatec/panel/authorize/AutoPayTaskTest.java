package com.wiatec.panel.authorize;

import org.junit.Test;

import static org.junit.Assert.*;


public class AutoPayTaskTest {


    @Test
    public void run() throws Exception {
        AutoPayTask autoPayTask = new AutoPayTask();
        autoPayTask.run();
    }

    @Test
    public void checkOutByMonth() throws Exception {
        AutoPayTask autoPayTask = new AutoPayTask();
    }

}