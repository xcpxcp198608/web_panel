package com.wiatec.panel;

import com.wiatec.panel.common.utils.TimeUtil;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class CalendarUserExpires {

    @Test
    public void getDateAfterYear(){
        long acTime = TimeUtil.getUnixFromStr("2017-11-01 12:00:00");
        Date date = new Date(acTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 12);
        date = calendar.getTime();
        Logger.getLogger("").debug(date);
    }
}
