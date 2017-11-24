package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.TimerTask;

@Component
public class AutoPayTask extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(AutoPayTask.class);

    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    private SqlSession sqlSession;

    private void init(){
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    @Override
    public void run() {
        init();
        commissionCategoryDao = sqlSession.getMapper(CommissionCategoryDao.class);
        authRentUserDao = sqlSession.getMapper(AuthRentUserDao.class);
        CommissionCategoryInfo b1 = commissionCategoryDao.selectOne("B1");
        CommissionCategoryInfo p1 = commissionCategoryDao.selectOne("P1");
        CommissionCategoryInfo p2 = commissionCategoryDao.selectOne("P2");

        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectAll();
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if("canceled".equals(authRentUserInfo.getStatus()) || "limited".equals(authRentUserInfo.getStatus())){
                continue;
            }
            CommissionCategoryInfo commissionCategoryInfo;
            switch (authRentUserInfo.getCategory()){
                case "B1":
                    commissionCategoryInfo = b1;
                    break;
                case "P1":
                    commissionCategoryInfo = p1;
                    break;
                case "P2":
                    commissionCategoryInfo = p2;
                    break;
                default:
                    commissionCategoryInfo = b1;
                    break;
            }
            if(!TimeUtil.isOutExpires(authRentUserInfo.getExpiresTime())){
                logger.debug("====================================================================");
                logger.debug("= prepare execute= {}", authRentUserInfo.getClientKey());
                logger.debug("= prepare execute= {}", authRentUserInfo.getFirstName());
                checkOutByMonth(commissionCategoryInfo, authRentUserInfo);
            }
        }
    }

    public void checkOutByMonth(CommissionCategoryInfo commissionCategoryInfo, AuthRentUserInfo authRentUserInfo){
        String today = TimeUtil.getStrDate();
        logger.debug("= {}", today);
        for(int i = 1; i <= commissionCategoryInfo.getExpires(); i ++){
            String date = TimeUtil.getExpiresDate(authRentUserInfo.getActivateTime(), i);
            if(today.equals(date)){
                logger.debug("= execute check out month");
                AuthorizePayInfo authorizePayInfo = new AuthorizePayInfo();
                authorizePayInfo.setSalesId(authRentUserInfo.getSalesId());
                authorizePayInfo.setClientKey(authRentUserInfo.getClientKey());
                authorizePayInfo.setCategory(authRentUserInfo.getCategory());
                authorizePayInfo.setCardNumber(authRentUserInfo.getCardNumber());
                authorizePayInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
                authorizePayInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
                authorizePayInfo.setAmount(authRentUserInfo.getMonthPay());
                authorizePayInfo.setDeposit(authRentUserInfo.getDeposit());
                authorizePayInfo.setLdCommission(authRentUserInfo.getLdCommission());
                authorizePayInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
                authorizePayInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
                authorizePayInfo.setType(AuthorizePayInfo.TYPE_RENT);
                AuthorizePayInfo authorizePayInfo1 = CreditCardTransaction.pay(authorizePayInfo);
                if(authorizePayInfo1 != null && "approved".equals(authorizePayInfo1.getStatus())){
                    logger.debug("= {} check out month successfully", authRentUserInfo.getClientKey());
                }else{
                    logger.debug("= check out month failure, deactivate = {}", authRentUserInfo.getClientKey());
                    authRentUserDao.updateStatusToDeactivate(authRentUserInfo.getClientKey());
                }
                return;
            }
        }
        logger.debug("= {} no need check out", authRentUserInfo.getClientKey());
        logger.debug("====================================================================");
    }


}
