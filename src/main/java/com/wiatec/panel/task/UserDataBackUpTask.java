package com.wiatec.panel.task;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
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
public class UserDataBackUpTask {

    private final Logger logger = LoggerFactory.getLogger(UserDataBackUpTask.class);
    private final String path = "/home/java_app/data_backup/panel/";

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private AuthRentUserDao authRentUserDao;

    private static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    public void executeBackUpTask() {
        logger.debug("user data backup start ...");
        execute();
    }

    private void execute(){
        authRegisterUserDao = sqlSession.getMapper(AuthRegisterUserDao.class);
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
    }

}
