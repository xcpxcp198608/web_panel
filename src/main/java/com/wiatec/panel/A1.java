package com.wiatec.panel;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author patrick
 */
public class A1 {

    private final Logger logger = LoggerFactory.getLogger(A1.class);

    public static void main (String [] args){
        String uuid= UUID.randomUUID().toString();
        System.out.println(uuid);

        boolean s = StringUtils.equals(null, "s");
        System.out.println(s);

        Sets.newHashSet();

        Lists.newArrayList();

        List<String> list = Splitter.on(",").splitToList("sdfdsfsd,sdfdsf,sf");

        Double d1 = 0.67;
        Double d2 = 0.617;
        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        BigDecimal result = b1.add(b2);
        double value = result.doubleValue();
        System.out.println(value);
    }

}
