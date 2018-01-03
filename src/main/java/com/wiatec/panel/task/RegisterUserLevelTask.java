package com.wiatec.panel.task;

import com.wiatec.panel.authorize.MonthAuthorizeTask;
import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RegisterUserLevelTask {

    private static final Logger logger = LoggerFactory.getLogger(MonthAuthorizeTask.class);

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
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(TimeUtil.isOutExpires(authRegisterUserInfo.getExpiresTime())){
                authRegisterUserInfo.setLevel(1);
                authRegisterUserInfo.setExpiresTime("");
                authRegisterUserDao.updateLevel(authRegisterUserInfo);
            }
        }
    }
}
