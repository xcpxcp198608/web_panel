package com.wiatec.panel.service;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.xutils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public ResultInfo<AuthRentUserInfo> login(HttpServletRequest request, String clientKey, String mac){
        ResultInfo<AuthRentUserInfo> resultInfo = new ResultInfo<>();
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo(clientKey, mac);
        if(authRentUserDao.countOneByMac(authRentUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("this device have no register");
            return resultInfo;
        }
        if(authRentUserDao.countOneByKeyAndMac(authRentUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("key not match with this device");
            return resultInfo;
        }
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectByClientKey(clientKey);
        int expires = commissionCategoryDao.selectOne(authRentUserInfo1.getCategory()).getExpires();
        if(isOutExpires(authRentUserInfo1.getActivateTime(), expires)){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("out expires");
            return resultInfo;
        }
        HttpSession session = SessionListener.getSession(clientKey);
        if(session == null) {
            session = request.getSession();
        }
        session.setAttribute(SessionListener.KEY ,clientKey);
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("login success");
        resultInfo.setData(authRentUserInfo1);
        return resultInfo;
    }

    @Transactional
    public ResultInfo<AuthRentUserInfo> validate(HttpServletRequest request, String clientKey, String mac,
                                                 String country, String region, String city, String timeZone){
        ResultInfo<AuthRentUserInfo> resultInfo = new ResultInfo<>();
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo(clientKey, mac);
        if(authRentUserDao.countOneByKeyAndMac(authRentUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("key not match with device");
            return resultInfo;
        }
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectByClientKey(clientKey);
        int expires = commissionCategoryDao.selectOne(authRentUserInfo1.getCategory()).getExpires();
        if(isOutExpires(authRentUserInfo1.getActivateTime(), expires)){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("out expires ");
            return resultInfo;
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
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("validate success");
        resultInfo.setData(authRentUserInfo1);
        return resultInfo;
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
