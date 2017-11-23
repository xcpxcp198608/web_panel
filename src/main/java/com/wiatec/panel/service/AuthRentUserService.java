package com.wiatec.panel.service;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Service
public class AuthRentUserService {

    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    public ResultInfo login(HttpServletRequest request, String clientKey, String mac){
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo(clientKey, mac);
        if(authRentUserDao.countOneByMac(authRentUserInfo) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_REGISTER);
        }
        if(authRentUserDao.countOneByKeyAndMac(authRentUserInfo) != 1){
            throw new XException(EnumResult.ERROR_KEY_INCORRECT);
        }
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectByClientKey(clientKey);
        if(!"activate".equals(authRentUserInfo1.getStatus())){
            throw new XException(EnumResult.ERROR_KEY_DEACTIVATE);
        }
        int expires = commissionCategoryDao.selectOne(authRentUserInfo1.getCategory()).getExpires();
        if(isOutExpires(authRentUserInfo1.getActivateTime(), expires)){
            throw new XException(EnumResult.ERROR_OUT_EXPIRATION);
        }
        HttpSession session = SessionListener.getSession(clientKey);
        if(session == null) {
            session = request.getSession();
        }
        session.setAttribute(SessionListener.KEY ,clientKey);
        return ResultMaster.success(authRentUserInfo1);
    }

    public ResultInfo validate(HttpServletRequest request, String clientKey, String mac,
                               String country, String region, String city, String timeZone){
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo(clientKey, mac);
        if(authRentUserDao.countOneByKeyAndMac(authRentUserInfo) != 1){
            throw new XException(EnumResult.ERROR_KEY_INCORRECT);
        }
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectByClientKey(clientKey);
        if(!"activate".equals(authRentUserInfo1.getStatus())){
            throw new XException(EnumResult.ERROR_KEY_DEACTIVATE);
        }
        int expires = commissionCategoryDao.selectOne(authRentUserInfo1.getCategory()).getExpires();
        if(isOutExpires(authRentUserInfo1.getActivateTime(), expires)){
            throw new XException(EnumResult.ERROR_OUT_EXPIRATION);
        }
        authRentUserInfo1.setCountry(country);
        authRentUserInfo1.setRegion(region);
        authRentUserInfo1.setCity(city);
        authRentUserInfo1.setTimeZone(timeZone);
        authRentUserDao.updateLocation(authRentUserInfo1);
        HttpSession session = SessionListener.getSession(clientKey);
        if(session == null) {
            session = request.getSession();
            session.setAttribute(SessionListener.KEY ,clientKey);
        }
        return  ResultMaster.success(authRentUserInfo1);
    }

    private boolean isOutExpires(String activateTime, int expires){
        long acTime = TimeUtil.getUnixFromStr(activateTime);
        Date date = new Date(acTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, expires);
        date = calendar.getTime();
        return System.currentTimeMillis() > date.getTime();
    }
}
