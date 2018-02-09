package com.wiatec.panel.task;

import com.wiatec.panel.authorize.RentalMonthAuthorizeTask;
import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Component
public class RegisterUserLevelTask {

    private static final Logger logger = LoggerFactory.getLogger(RentalMonthAuthorizeTask.class);

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    private static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    @Scheduled(cron="0 0 3 1-31 1-12 1-7")
    public void executeLevelTask() {
        logger.debug("Register user level check start ...");
        execute();
    }

    private void execute(){
        authRegisterUserDao = sqlSession.getMapper(AuthRegisterUserDao.class);
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectAllExpiresUsers(0);
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(authRegisterUserInfo.getLevel() > 1) {
                Date expiration = authRegisterUserInfo.getExpiration();
                if(expiration != null && expiration.before(new Date())) {
                    logger.error("id: " + authRegisterUserInfo.getId() +
                            " level: " + authRegisterUserInfo.getLevel() +
                            " expiration: " + expiration +
                            " expires time: " + authRegisterUserInfo.getExpiresTime());
                    authRegisterUserInfo.setLevel(1);
                    authRegisterUserInfo.setExpiresTime(new Date(TimeUtil.DEFAULT_TIME));
                    authRegisterUserDao.updateLevelById(authRegisterUserInfo);
                }
            }
        }
    }
}
