package com.wiatec.panel.common.utils;

import com.wiatec.panel.authorize.MonthAuthorizeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * @author patrick
 */
public class ApplicationContextAfterLoading implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ApplicationContextAfterLoading.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            logger.debug("application loading completed");

//            MonthAuthorizeTask monthAuthorizeTask = new MonthAuthorizeTask();
//            monthAuthorizeTask.executeUploadBackTask();
        }
    }
}
