package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransactionSales;
import com.wiatec.panel.authorize.AuthorizeTransactionSalesInfo;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.PathUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.InvoiceUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.AllSalesMonthCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfMonthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 */
@Service
public class AuthDealerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;
    @Autowired
    private AuthorizeTransactionSalesDao authorizeTransactionSalesDao;
    @Autowired
    private SalesActivateCategoryDao salesActivateCategoryDao;
    @Autowired
    private SalesGoldCategoryDao salesGoldCategoryDao;

    public String home(HttpServletRequest request, Model model){
        int dealerId = getDealerInfo(request).getId();
        int totalVolume = authRentUserDao.countTotalVolumeByDealerId(dealerId);
        float totalCommission = authorizeTransactionRentalDao.countTotalCommissionByDealerId(dealerId);
        model.addAttribute("totalVolume", totalVolume);
        model.addAttribute("totalCommission", totalCommission);
        return "dealer/home";
    }

    public String sales(HttpServletRequest request, Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectSales(getDealerInfo(request).getId());
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        List<SalesActivateCategoryInfo> salesActivateCategoryInfoList = salesActivateCategoryDao.selectAll();
        model.addAttribute("salesActivateCategoryInfoList", salesActivateCategoryInfoList);
        List<SalesGoldCategoryInfo> salesGoldCategoryInfoList = salesGoldCategoryDao.selectAll();
        model.addAttribute("salesGoldCategoryInfoList", salesGoldCategoryInfoList);
        return "dealer/sales";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createSales(HttpServletRequest request, AuthSalesInfo authSalesInfo) {
        if(authSalesDao.countUsername(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authSalesDao.countSSN(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_SSN_EXISTS);
        }
        if(authSalesDao.countEmail(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        //1. create transaction info
        SalesActivateCategoryInfo salesActivateCategoryInfo = salesActivateCategoryDao
                .selectOneByCategory(authSalesInfo.getActivateCategory());
        SalesGoldCategoryInfo salesGoldCategoryInfo = salesGoldCategoryDao
                .selectOneByCategory(authSalesInfo.getGoldCategory());
        AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo;
        if(salesGoldCategoryInfo != null){
            authorizeTransactionSalesInfo = AuthorizeTransactionSalesInfo.createGold(authSalesInfo,
                    salesActivateCategoryInfo, salesGoldCategoryInfo);
        }else{
            authorizeTransactionSalesInfo = AuthorizeTransactionSalesInfo.createNormal(authSalesInfo,
                    salesActivateCategoryInfo);
        }
        //2. process transaction and save transaction info
        AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo1 = new AuthorizeTransactionSales()
                .charge(authorizeTransactionSalesInfo);
        if(authorizeTransactionSalesInfo1 == null){
            throw new XException(1001, "pay failure");
        }
        authorizeTransactionSalesDao.insertOne(authorizeTransactionSalesInfo1);
        //3. send invoice
        try {
            List<InvoiceInfo> invoiceInfoList = salesGoldCategoryInfo != null ?
                    InvoiceInfoMaker.salesActivateGold(salesActivateCategoryInfo, salesGoldCategoryInfo):
                    InvoiceInfoMaker.salesActivateNormal(salesActivateCategoryInfo);
            InvoiceUtil.setPath(PathUtil.getRealPath(request) + "invoice/");
            String invoice = InvoiceUtil.createInvoice(authSalesInfo.getEmail(), authorizeTransactionSalesInfo1
                    .getTransactionId(), invoiceInfoList);
            EmailMaster emailMaster = new EmailMaster();
            emailMaster.setInvoiceContent(authSalesInfo.getUsername());
            emailMaster.addAttachment(invoice);
            emailMaster.sendMessage(authSalesInfo.getEmail());
        } catch (Exception e) {
            logger.error("invoice error", e);
        }

        //4. store sales info
        authSalesInfo.setExpiresTime(TimeUtil.getExpiresDate(salesActivateCategoryInfo.getMonth()));
        authSalesInfo.setDealerId(getDealerInfo(request).getId());
        authSalesDao.insertOne(authSalesInfo);
        return ResultMaster.success(authSalesDao.selectOneByUsername(authSalesInfo));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateSalesPassword(AuthSalesInfo authSalesInfo){
        authSalesDao.updatePassword(authSalesInfo);
        return ResultMaster.success();
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
        return "dealer/customers";
    }

    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateUserStatus(String status, String key) {
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setStatus(status);
        authRentUserInfo.setClientKey(key);
        authRentUserDao.updateUserStatus(authRentUserInfo);
        return ResultMaster.success();
    }


    private AuthDealerInfo getDealerInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        return authDealerDao.selectOne(new AuthDealerInfo(username));
    }


    public List<SalesVolumeInDayOfMonthInfo> selectDealerVolumeEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authRentUserDao.countDealerVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<DealerCommissionOfDaysInfo> selectDealerCommissionEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authorizeTransactionRentalDao.getCommissionOfDayByDealer(yearOrMonthInfo);
    }

    public List<DealerCommissionOfMonthInfo> selectDealerCommissionEveryMonthInYear(HttpServletRequest request, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authorizeTransactionRentalDao.getCommissionOfMonthByDealer(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(HttpServletRequest request, int top){
        AuthDealerInfo authDealerInfo = getDealerInfo(request);
        //用leader id 代替top
        authDealerInfo.setLeaderId(top);
        return authRentUserDao.selectTopVolumeByDealer(authDealerInfo);
    }

    public List<TopAmountInfo> getTopAmount(HttpServletRequest request, int top){
        AuthDealerInfo authDealerInfo = getDealerInfo(request);
        //用leader id 代替top
        authDealerInfo.setLeaderId(top);
        return authorizeTransactionRentalDao.selectTopAmountByDealer(authDealerInfo);
    }

    public List<AllSalesMonthCommissionInfo> getSalesCommissionByMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setDealerId(getDealerInfo(request).getId()+"");
        return authorizeTransactionRentalDao.selectSalesCommissionByMonthAndDealer(yearOrMonthInfo);
    }

}
