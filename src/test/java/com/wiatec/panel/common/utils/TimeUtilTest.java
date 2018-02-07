package com.wiatec.panel.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilTest {
    @Test
    public void getExpiresTimeByDay() throws Exception {
        String day = TimeUtil.getExpiresTimeByDay("2018-01-31 00:00:00", 7);
        System.out.println(day);
    }

    @Test
    public void isOutExpires() {
    }

    @Test
    public void isOutExpires1() {
        boolean day = TimeUtil.isOutExpires("2018-02-06 00:00:00");
        System.out.println(day);
    }
}