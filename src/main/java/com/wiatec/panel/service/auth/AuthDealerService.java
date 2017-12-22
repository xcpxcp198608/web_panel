package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.oxm.dao.AuthDealerDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthDealerService {

    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;

    public String home(){
        return "dealer/home";
    }

    public String sales(HttpServletRequest request, Model model){
        int id = getDealerInfo(request).getId();
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectSales(id);
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "dealer/sales";
    }

    public String users(HttpServletRequest request, Model model){
        int id = getDealerInfo(request).getId();
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectByDealerId(id);
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "dealer/users";
    }


    private AuthDealerInfo getDealerInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        return authDealerDao.selectOne(new AuthDealerInfo(username));
    }
}
