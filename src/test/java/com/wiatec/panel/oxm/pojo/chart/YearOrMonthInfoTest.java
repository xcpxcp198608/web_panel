package com.wiatec.panel.oxm.pojo.chart;

import org.junit.Test;

import static org.junit.Assert.*;

public class YearOrMonthInfoTest {

    @Test
    public void test(){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(2017, 12, true);
        System.out.println(yearOrMonthInfo.getStart());
        System.out.println(yearOrMonthInfo.getEnd());
    }

}