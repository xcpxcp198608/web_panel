package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthRentUserService {

    @Resource
    private AuthRentUserDao authRentUserDao;

    @Transactional(readOnly = true)
    public void login(HttpServletRequest request, String clientKey, String mac){

    }

    @Transactional
    public void validate(HttpServletRequest request, String clientKey, String mac){

    }
}
