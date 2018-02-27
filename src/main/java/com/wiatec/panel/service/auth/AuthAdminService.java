package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.*;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.PathUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.SalesMemberInvoiceUtil;
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

import javax.annotation.Resource;
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

    @Resource
    private AuthAdminDao authAdminDao;
    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;
    @Resource
    private AuthorizeTransactionSalesMemberDao authorizeTransactionSalesMemberDao;
    @Resource
    private AuthorizeTransactionSalesDepositDao authorizeTransactionSalesDepositDao;
    @Resource
    private DevicePCPDao devicePCPDao;
    @Resource
    private SalesActivateCategoryDao salesActivateCategoryDao;
    @Resource
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

    public String sales(HttpServletRequest request, Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        List<AuthDealerInfo> authDealerInfoList = authDealerDao.selectAll();
        model.addAttribute("authDealerInfoList", authDealerInfoList);
        List<SalesActivateCategoryInfo> salesActivateCategoryInfoList;
        if(AuthAdminInfo.LEVEL_HIGH == getAdminInfo(request).getPermission()){
            salesActivateCategoryInfoList = salesActivateCategoryDao.selectAllWithLimit(0);
        }else{
            salesActivateCategoryInfoList = salesActivateCategoryDao.selectAllWithLimit(1);
        }
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

        SalesActivateCategoryInfo salesActivateCategoryInfo = salesActivateCategoryDao
                .selectOneByCategory(authSalesInfo.getActivateCategory());
        authSalesInfo.setExpiresTime(TimeUtil.getExpiresDate(salesActivateCategoryInfo.getMonth()));
        authSalesDao.insertOne(authSalesInfo);

        if(salesActivateCategoryInfo.getPrice() > 0) {
            AuthSalesInfo authSalesInfo1 = authSalesDao.selectOneByUsername(authSalesInfo);
            //1. create transaction info
            float amount = salesActivateCategoryInfo.getPrice() *
                    (1 + AuthorizeTransactionInfo.TAX);
            AuthorizeTransactionInfo authorizeTransactionInfo = AuthorizeTransactionInfo
                    .createFromAuthSales(authSalesInfo1, amount);

            //2. process transaction and save transaction info
            AuthorizeTransactionInfo charge = new AuthorizeTransaction()
                    .charge(authorizeTransactionInfo);
            if (charge == null) {
                throw new XException(EnumResult.ERROR_TRANSACTION_FAILURE);
            }
            AuthorizeTransactionSalesMemberInfo authorizeTransactionSalesMemberInfo = AuthorizeTransactionSalesMemberInfo
                    .create(authSalesInfo1, salesActivateCategoryInfo, charge);

            authorizeTransactionSalesMemberDao.insertOne(authorizeTransactionSalesMemberInfo);
            //3. send invoice
            try {
                List<InvoiceInfo> invoiceInfoList = InvoiceInfoMaker.salesActivateNormal(salesActivateCategoryInfo);
                SalesMemberInvoiceUtil.setPath(PathUtil.getRealPath(request) + "invoice/");
                String invoice = SalesMemberInvoiceUtil.createInvoice(authSalesInfo1.getEmail(),
                        charge.getTransactionId(), invoiceInfoList);
                SalesMemberInvoiceUtil.copyInvoice(invoice);
                EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LDE);
                emailMaster.setInvoiceContent(authSalesInfo1.getUsername());
                emailMaster.addAttachment(invoice);
                emailMaster.sendMessage(authSalesInfo1.getEmail());
            } catch (Exception e) {
                logger.error("invoice send error", e);
            }
        }
        return ResultMaster.success(authSalesDao.selectOneByUsername(authSalesInfo));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateSalesPassword(AuthSalesInfo authSalesInfo){
        authSalesDao.updatePassword(authSalesInfo);
        return ResultMaster.success();
    }

    public String showSalesDetail(int salesId, Model model){
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(salesId);
        model.addAttribute("authSalesInfo", authSalesInfo);
        List<DevicePCPInfo> rentedDevicePCPInfoList = devicePCPDao.selectRentedBySalesId(salesId);
        model.addAttribute("rentedDeviceRentInfoList", rentedDevicePCPInfoList);
        return "admin/sales_detail";
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
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao
                .selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDE);
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "admin/customers";
    }

    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateUserStatus(String status, String key){
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setStatus(status);
        authRentUserInfo.setClientKey(key);
        authRentUserDao.updateUserStatus(authRentUserInfo);
//        if(AuthRentUserInfo.STATUS_ACTIVATE.equals(status)){
//            AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectOneByClientKey(key);
//            devicePCPDao.updateDeviceToRented(authRentUserInfo1.getMac());
//            int sdcnCount = devicePCPDao.countSDCNBySalesId(authRentUserInfo1.getSalesId());
//            if(sdcnCount >= AuthSalesInfo.SDCN_NOTICE_COUNT){
//                authSalesDao.updateSDCNById(authRentUserInfo1.getSalesId());
//            }
//        }
        return ResultMaster.success();
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateUserActivateWithCash(HttpServletRequest request,
                                                 String key, String password){
        AuthAdminInfo authAdminInfo = getAdminInfo(request);
        if(!password.equals(authAdminInfo.getPassword())){
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_ACTIVATE);
        authRentUserInfo.setClientKey(key);
        authRentUserDao.updateUserStatus(authRentUserInfo);
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectOneByClientKey(key);
        devicePCPDao.updateDeviceToRented(authRentUserInfo1.getMac());
        int sdcnCount = devicePCPDao.countSDCNBySalesId(authRentUserInfo1.getSalesId());
        if(sdcnCount >= AuthSalesInfo.SDCN_NOTICE_COUNT){
            authSalesDao.updateSDCNById(authRentUserInfo1.getSalesId());
        }
        return ResultMaster.success();
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
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao
                .selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDE);
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "admin/commission";
    }

    public String transactions(Model model){
        List<AuthorizeTransactionRentalInfo> authorizeTransactionRentalInfoList =
                authorizeTransactionRentalDao.selectAllLDE();
        model.addAttribute("authorizeTransactionRentalInfoList", authorizeTransactionRentalInfoList);
        return "admin/transactions";
    }

    public String devices(Model model){
        List<DevicePCPInfo> devicePCPInfoList = devicePCPDao.selectAll();
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("devicePCPInfoList", devicePCPInfoList);
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "admin/devices";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo saveDevice(HttpServletRequest request, DevicePCPInfo devicePCPInfo){
        if(devicePCPInfo.getMac().length() != 17){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(devicePCPDao.countOne(devicePCPInfo) == 1){
            throw new XException(1100, "this mac address already check in");
        }
        devicePCPInfo.setMac(devicePCPInfo.getMac().toUpperCase());
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(devicePCPInfo.getSalesId());
        if(authSalesInfo == null){
            throw new XException(1100, "sales not exists");
        }
        devicePCPInfo.setDealerId(authSalesInfo.getDealerId());
        devicePCPInfo.setAdminId(getAdminInfo(request).getId());
        devicePCPDao.insertOne(devicePCPInfo.getMac(), DeviceAllInfo.DISTRIBUTOR_LDE);
        int salesStoreCount = devicePCPDao.countNoRentedBySalesId(authSalesInfo.getId());
        if(salesStoreCount >= AuthSalesInfo.GOLD_COUNT){
            authSalesDao.updateGoldById(authSalesInfo.getId());
        }
        return ResultMaster.success(devicePCPDao.selectOneByMac(devicePCPInfo.getMac()));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo bathUpdateDeviceToSpecialSales(HttpServletRequest request, String [] macs,
                                                     int salesId, String password, boolean free){
        AuthAdminInfo authAdminInfo = getAdminInfo(request);
        if(!authAdminInfo.getPassword().equals(password)){
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        if(macs.length <= 0 ){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(salesId);
        if(authSalesInfo == null){
            throw new XException(1100, "rep not exists");
        }
        if(authSalesInfo.getCardNumber() == null || authSalesInfo.getCardNumber().length() < 16){
            throw new XException(1001, "rep credit card information error");
        }
        devicePCPDao.bathUpdateDeviceToSpecialSales(macs, salesId, authSalesInfo.getDealerId(),
                authAdminInfo.getId());
        int salesStoreCount = devicePCPDao.countNoRentedBySalesId(salesId);
        if(salesStoreCount >= AuthSalesInfo.GOLD_COUNT){
            authSalesDao.updateGoldById(salesId);
        }
        if(!free) {
            AuthorizeTransactionInfo authorizeTransactionInfo = AuthorizeTransactionInfo
                    .createFromAuthSales(authSalesInfo, CommissionCategoryInfo.DEPOSIT * macs.length);
            AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo, request);
            if (charge == null) {
                throw new XException(EnumResult.ERROR_TRANSACTION_FAILURE);
            }
            authorizeTransactionSalesDepositDao.insertOne(AuthorizeTransactionSalesDepositInfo
                    .createDepositForRepStore(authSalesInfo, charge));
        }
        return ResultMaster.success(authSalesInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo checkReturned(HttpServletRequest request, String[] macs, int salesId, String checkNumber){
        if(macs.length <= 0 ){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(TextUtil.isEmpty(checkNumber)){
            throw new XException(1001, "check number error");
        }
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(salesId);
        if(authSalesInfo == null){
            throw new XException(1100, "rep not exists");
        }
        if(authSalesInfo.isGold() && !authSalesInfo.isSdcn()){
            throw new XException(1100, "sales volume less than 5");
        }
        for(String mac: macs){
            DevicePCPInfo devicePCPInfo = devicePCPDao.selectOneByMac(mac);
            if(devicePCPInfo.isChecked()){
                throw new XException(1100, mac + " already checked");
            }
            if(!devicePCPInfo.isRented() || !devicePCPInfo.isSdcn()){
                throw new XException(1100, mac + " can not check");
            }
        }
        devicePCPDao.bathUpdateDeviceToChecked(macs, salesId, checkNumber);
        int sdcnCount = devicePCPDao.countSDCNBySalesId(salesId);
        if(sdcnCount <= 0){
            authSalesDao.updateNoSDCNById(salesId);
        }
        return ResultMaster.success();
    }

    /////////////////////////////////////////////////// chart //////////////////////////////////////////////////////////

    public List<VolumeDistributionInfo> getDistributionData(){
        return authRentUserDao.getDistributionData();
    }


    public List<SalesVolumeInDayOfMonthInfo> countSaleVolumeEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authRentUserDao.countAllSalesVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(int top){
        return authRentUserDao.selectTopVolume(top);
    }

    public List<TopAmountInfo> getTopAmount(int top){
        return authorizeTransactionRentalDao.selectTopAmount(top);
    }

    public List<AllDealerMonthCommissionInfo> getAllDealerCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllDealersCommissionByMonth(yearOrMonthInfo);
    }

    public List<AllDealerMonthCommissionInfo> getAllDealerTotalCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllDealersTotalCommissionByMonth(yearOrMonthInfo);
    }

    public List<AllDealerMonthCommissionInfo> getAllDealerActivationCommByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllDealersActivationCommByMonth(yearOrMonthInfo);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllSalesCommissionByMonth(yearOrMonthInfo);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesTotalCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllSalesTotalCommissionByMonth(yearOrMonthInfo);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesActivationCommByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectAllSalesActivationCommByMonth(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectSaleAmountEveryMonthInYear(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        return authorizeTransactionRentalDao.selectSaleAmountEveryDayInMonth(yearOrMonthInfo);
    }

    private AuthAdminInfo getAdminInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authAdminDao.selectOneByUsername(new AuthAdminInfo(username));
    }

}
