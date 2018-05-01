package com.wiatec.panel;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    }

}
