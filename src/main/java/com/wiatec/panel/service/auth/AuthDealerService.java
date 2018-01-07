package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthDealerDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionDao;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.AllSalesMonthCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopVolumeInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class AuthDealerService {

    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;

    public String home(){
        return "dealer/home";
    }

    public String sales(HttpServletRequest request, Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectSales(getDealerInfo(request).getId());
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "dealer/sales";
    }

    public String users(HttpServletRequest request, Model model, int key, int value){
        List<AuthRentUserInfo> authRentUserInfoList;
        switch (key){
            case 1:
                authRentUserInfoList = authRentUserDao.selectByDealerId(getDealerInfo(request).getId());
                break;
            case 2:
                authRentUserInfoList = authRentUserDao.selectBySalesId(value);
                break;
            default:
                throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if(sessionMap.containsKey(authRentUserInfo.getClientKey())){
                authRentUserInfo.setOnline(true);
            }
        }
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "dealer/users";
    }

    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    private AuthDealerInfo getDealerInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        return authDealerDao.selectOne(new AuthDealerInfo(username));
    }


    public List<SalesVolumeInDayOfMonthInfo> countSaleVolumeEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authRentUserDao.countSalesUnderDealerVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(HttpServletRequest request, int top){
        AuthDealerInfo authDealerInfo = getDealerInfo(request);
        authDealerInfo.setLeaderId(top); //用leader id 代替top
        return authRentUserDao.selectTopVolumeByDealer(authDealerInfo);
    }

    public List<TopAmountInfo> getTopAmount(HttpServletRequest request, int top){
        AuthDealerInfo authDealerInfo = getDealerInfo(request);
        authDealerInfo.setLeaderId(top); //用leader id 代替top
        return authorizeTransactionDao.selectTopAmountByDealer(authDealerInfo);
    }

    public List<AllSalesMonthCommissionInfo> getSalesCommissionByMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authorizeTransactionDao.selectSalesCommissionByMonthAndDealer(yearOrMonthInfo);
    }

}
