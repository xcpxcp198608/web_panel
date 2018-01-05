package com.wiatec.panel.common.utils;

import java.math.BigDecimal;

public class UnitUtil {

    public static void main (String [] args){
        float f = round(1.5992F);
        System.out.println(f);
    }


    public static float round(float f){
        return new BigDecimal(f).setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
