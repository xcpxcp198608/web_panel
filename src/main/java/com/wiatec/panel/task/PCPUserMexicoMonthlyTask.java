package com.wiatec.panel.task;

import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.LogPcpUserMonthlyMexicoDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
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
public class PCPUserMexicoMonthlyTask extends BackgroundTask {

    private final Logger logger = LoggerFactory.getLogger(PCPUserMexicoMonthlyTask.class);

    @Resource
    private LogPcpUserMonthlyMexicoDao logPcpUserMonthlyMexicoDao;
    @Resource
    private AuthRentUserDao authRentUserDao;

    @Scheduled(cron = "0 0 1 1-31 1-12 1-7")
    public void createMonthlyRecord(){
        logger.debug("PCP mexico user monthly record start ...");
        logPcpUserMonthlyMexicoDao = sqlSession.getMapper(LogPcpUserMonthlyMexicoDao.class);
        authRentUserDao = sqlSession.getMapper(AuthRentUserDao.class);
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao
                .selectAllActiveByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDM);
        logPcpUserMonthlyMexicoDao.batchInsert(authRentUserInfoList);
    }
}
