package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransactionRental;
import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.authorize.AuthorizeTransactionSales;
import com.wiatec.panel.authorize.AuthorizeTransactionSalesInfo;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.PathUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.SalesInvoiceUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 */
@Service
public class AuthAdminService {

    private Logger logger = LoggerFactory.getLogger(AuthAdminService.class);

    @Autowired
    private AuthAdminDao authAdminDao;
    @Autowired
    private AuthDealerDao authDealerDao;
    @Autowired
    private AuthSalesDao authSalesDao;
    @Autowired
    private AuthRentUserDao authRentUserDao;
    @Autowired
    private CommissionCategoryDao commissionCategoryDao;
    @Autowired
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;
    @Autowired
    private AuthorizeTransactionSalesDao authorizeTransactionSalesDao;
    @Autowired
    private DeviceRentDao deviceRentDao;
    @Autowired
    private SalesActivateCategoryDao salesActivateCategoryDao;
    @Autowired
    private SalesGoldCategoryDao salesGoldCategoryDao;

    public String home(){
        return "admin/home";
    }

    public String dealer(Model model){
        List<AuthDealerInfo> authDealerInfoList = authDealerDao.selectAll();
        model.addAttribute("authDealerInfoList", authDealerInfoList);
        return "admin/dealer";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createDealer(HttpServletRequest request, AuthDealerInfo authDealerInfo) {
        if(authDealerDao.countUsername(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authDealerDao.countSSN(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_SSN_EXISTS);
        }
        if(authDealerDao.countEmail(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        try {
            authDealerInfo.setLeaderId(getAdminInfo(request).getId());
            authDealerDao.insertOne(authDealerInfo);
            return ResultMaster.success(authDealerDao.selectOne(authDealerInfo));
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateDealerPassword(AuthDealerInfo authDealerInfo){
        try {
            authDealerDao.updatePassword(authDealerInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String sales(Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        List<AuthDealerInfo> authDealerInfoList = authDealerDao.selectAll();
        model.addAttribute("authDealerInfoList", authDealerInfoList);
        List<SalesActivateCategoryInfo> salesActivateCategoryInfoList = salesActivateCategoryDao.selectAll();
        model.addAttribute("salesActivateCategoryInfoList", salesActivateCategoryInfoList);
        List<SalesGoldCategoryInfo> salesGoldCategoryInfoList = salesGoldCategoryDao.selectAll();
        model.addAttribute("salesGoldCategoryInfoList", salesGoldCategoryInfoList);
        return "admin/sales";
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
            List<InvoiceInfo> invoiceInfoList;
            if(salesGoldCategoryInfo != null) {
                invoiceInfoList = InvoiceInfoMaker.salesActivateGold(salesActivateCategoryInfo, salesGoldCategoryInfo);
            }else{
                invoiceInfoList = InvoiceInfoMaker.salesActivateNormal(salesActivateCategoryInfo);
            }
            SalesInvoiceUtil.setPath(PathUtil.getRealPath(request) + "invoice/");
            String invoice = SalesInvoiceUtil.createInvoice(authSalesInfo.getEmail(), authorizeTransactionSalesInfo1
                    .getTransactionId(), invoiceInfoList);
            EmailMaster emailMaster = new EmailMaster();
            emailMaster.setInvoiceContent(authSalesInfo.getUsername());
            emailMaster.addAttachment(invoice);
            emailMaster.sendMessage(authSalesInfo.getEmail());
        } catch (Exception e) {
            logger.error("invoice send error", e);
        }
        //4. store sales info
        authSalesInfo.setExpiresTime(TimeUtil.getExpiresDate(salesActivateCategoryInfo.getMonth()));
        authSalesDao.insertOne(authSalesInfo);
        return ResultMaster.success(authSalesDao.selectOneByUsername(authSalesInfo));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateSalesPassword(AuthSalesInfo authSalesInfo){
        try {
            authSalesDao.updatePassword(authSalesInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error("Exception: ", e);
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String users(Model model, int key, int value){
        List<AuthRentUserInfo> authRentUserInfoList;
        switch (key){
            case 0:
                authRentUserInfoList = authRentUserDao.selectAll();
                break;
            case 1:
                authRentUserInfoList = authRentUserDao.selectByDealerId(value);
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
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "admin/customers";
    }

    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateUserStatus(String status, String key){
        try {
            AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
            authRentUserInfo.setStatus(status);
            authRentUserInfo.setClientKey(key);
            authRentUserDao.updateUserStatus(authRentUserInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error("Exception:", e);
            return ResultMaster.error(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateUserCategory(HttpServletRequest request, String key, String category){
        try {
            AuthRentUserInfo authRentUserInfo = authRentUserDao.selectByClientKey(key);
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(category);
            commissionCategoryInfo.setFirstPay();
            authRentUserInfo.setCategory(category);
            authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
            authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
            authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
            authorizeTransactionRentalInfo.setSalesId(authRentUserInfo.getSalesId());
            authorizeTransactionRentalInfo.setDealerId(authRentUserInfo.getDealerId());
            authorizeTransactionRentalInfo.setSalesName(authRentUserInfo.getSalesName());
            authorizeTransactionRentalInfo.setCategory(authRentUserInfo.getCategory());
            authorizeTransactionRentalInfo.setClientKey(authRentUserInfo.getClientKey());
            authorizeTransactionRentalInfo.setCardNumber(authRentUserInfo.getCardNumber());
            authorizeTransactionRentalInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
            authorizeTransactionRentalInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
            authorizeTransactionRentalInfo.setAmount(authRentUserInfo.getFirstPay());
            authorizeTransactionRentalInfo.setDeposit(authRentUserInfo.getDeposit());
            authorizeTransactionRentalInfo.setLdCommission(authRentUserInfo.getLdCommission());
            authorizeTransactionRentalInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
            authorizeTransactionRentalInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
            authorizeTransactionRentalInfo.setType(AuthorizeTransactionRentalInfo.TYPE_CONTRACTED);
            AuthorizeTransactionRentalInfo charge = new AuthorizeTransactionRental().charge(authorizeTransactionRentalInfo, request);
            if(charge == null){
                throw new XException(EnumResult.ERROR_AUTHORIZE);
            }
            authorizeTransactionRentalDao.insertOne(charge);
            authRentUserDao.updateUserCategory(authRentUserInfo);
            return ResultMaster.success(authRentUserInfo);
        }catch (Exception e){
            logger.error("Exception:", e);
            return ResultMaster.error(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String distribution(Model model){
        return "admin/distribution";
    }


    public String commission(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "admin/commission";
    }

    public String transactions(Model model){
        List<AuthorizeTransactionRentalInfo> authorizeTransactionRentalInfoList =
                authorizeTransactionRentalDao.selectAll();
        model.addAttribute("authorizeTransactionRentalInfoList", authorizeTransactionRentalInfoList);
        return "admin/transactions";
    }

    public String devices(Model model){
        List<DeviceRentInfo> deviceRentInfoList = deviceRentDao.selectAll();
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("deviceRentInfoList", deviceRentInfoList);
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "admin/devices";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo saveDevice(HttpServletRequest request, DeviceRentInfo deviceRentInfo){
        if(deviceRentInfo.getMac().length() != 17){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(deviceRentDao.countOne(deviceRentInfo) == 1){
            throw new XException(1100, "this mac address already check in");
        }
        deviceRentInfo.setMac(deviceRentInfo.getMac().toUpperCase());
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(deviceRentInfo.getSalesId());
        if(authSalesInfo == null){
            throw new XException(1100, "sales not exists");
        }
        deviceRentInfo.setDealerId(authSalesInfo.getDealerId());
        deviceRentInfo.setAdminId(getAdminInfo(request).getId());
        deviceRentDao.insertOne(deviceRentInfo);
        int salesStoreCount = deviceRentDao.countNoRentedBySalesId(authSalesInfo.getId());
        if(salesStoreCount >= 10){
            authSalesDao.updateGoldById(authSalesInfo.getId());
        }
        return ResultMaster.success(deviceRentDao.selectOneByMac(deviceRentInfo));
    }

    /////////////////////////////////////////////////// chart //////////////////////////////////////////////////////////

    public List<VolumeDistributionInfo> getDistributionData(){
        return authRentUserDao.getDistributionData();
    }


    public List<SalesVolumeInDayOfMonthInfo> countSaleVolumeEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authRentUserDao.countAllSalesVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(int top){
        return authRentUserDao.selectTopVolume(top);
    }

    public List<TopAmountInfo> getTopAmount(int top){
        return authorizeTransactionRentalDao.selectTopAmount(top);
    }

    public List<AllDealerMonthCommissionInfo> getAllDealerCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authorizeTransactionRentalDao.selectAllDealersCommissionByMonth(yearOrMonthInfo);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authorizeTransactionRentalDao.selectAllSalesCommissionByMonth(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        return authorizeTransactionRentalDao.selectSaleAmountEveryMonthInYear(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authorizeTransactionRentalDao.selectSaleAmountEveryDayInMonth(yearOrMonthInfo);
    }

    private AuthAdminInfo getAdminInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authAdminDao.selectOne(new AuthAdminInfo(username));
    }

}
