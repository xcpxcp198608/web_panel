package com.wiatec.panel.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilTest {
    @Test
    public void getExpiresTimeByDay() throws Exception {
        String day = TimeUtil.getExpiresTimeByDay("2017-12-06 17:30:30", 7);
        System.out.println(day);
    }

    @Test
    public void isOutExpires() {
    }

    @Test
    public void isOutExpires1() {
        boolean day = TimeUtil.isOutExpires("2019-01-02 00:00:00");
        System.out.println(day);
    }
}