package com.wiatec.panel.service.auth;

import com.wiatec.panel.oxm.dao.AuthAdminDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthSalesService {

    @Resource
    private AuthSalesDao authSalesDao;


    /////////////////////////////////////////////////// sales //////////////////////////////////////////////////////////
    @Transactional
    public String home(HttpServletRequest request, Model model){
        return "sales/home";
    }


    /////////////////////////////////////////////////// users //////////////////////////////////////////////////////////
}
