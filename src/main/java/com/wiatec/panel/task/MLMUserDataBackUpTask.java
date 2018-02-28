package com.wiatec.panel.task;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.LogMlmUserDailyDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Component
public class MLMUserDataBackUpTask extends BackgroundTask {

    private final Logger logger = LoggerFactory.getLogger(MLMUserDataBackUpTask.class);
    private final String path = "/home/java_app/data_backup/panel/";

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private LogMlmUserDailyDao logMlmUserDailyDao;

    @Scheduled(cron = "0 10 1 1-31 1-12 1-7")
    public void executeBackUpTask() {
        logger.debug("MLM user data backup start ...");
        execute();
    }

    private void execute(){
        authRegisterUserDao = sqlSession.getMapper(AuthRegisterUserDao.class);
        logMlmUserDailyDao = sqlSession.getMapper(LogMlmUserDailyDao.class);
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        logMlmUserDailyDao.batchInsert(authRegisterUserInfoList);
    }

}
