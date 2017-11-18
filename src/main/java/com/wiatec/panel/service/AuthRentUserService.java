package com.wiatec.panel.service;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthRentUserService {

    @Resource
    private AuthRentUserDao authRentUserDao;

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
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("login success");
        resultInfo.setData(authRentUserDao.selectByClientKey(clientKey));
        return resultInfo;
    }

    @Transactional
    public void validate(HttpServletRequest request, String clientKey, String mac){

    }
}
