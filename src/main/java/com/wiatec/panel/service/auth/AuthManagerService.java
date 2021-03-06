package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.security.MD5Util;
import com.wiatec.panel.common.utils.*;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthManagerDao;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LogUserLevelDao;
import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.MonthVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.YearVolumeInfo;
import com.wiatec.panel.oxm.pojo.log.LogUserLevelInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class AuthManagerService {

    private final int LIMIT = 1000;

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private AuthManagerDao authManagerDao;
    @Resource
    private LogUserLevelDao logUserLevelDao;

    public String home(){
        return "manager/home";
    }

    public String users(HttpSession session, Model model, int page){
        if(page <= 0){
            page = 1;
        }
        String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(username == null){
            throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        AuthManagerInfo authManagerInfo = authManagerDao.selectOneByUsername(username);
        List<AuthRegisterUserInfo> authRegisterUserInfoList;
        int totalCount = 0;
        int totalPage = 1;
        if(authManagerInfo.getPermission() >= AuthManagerInfo.LEVEL_HIGHEST ) {
            totalCount = authRegisterUserDao.countAll(0);
            totalPage = totalCount / LIMIT;
            if(totalCount % LIMIT != 0){
                totalPage ++;
            }
            if(page > totalPage){
                page = totalPage;
            }
            authRegisterUserInfoList = authRegisterUserDao.selectByPage(0, (page -1) * LIMIT, LIMIT);
        }else{
            totalCount = authRegisterUserDao.countAll(100);
            totalPage = totalCount / LIMIT;
            if(totalCount % LIMIT != 0){
                totalPage ++;
            }
            if(page > totalPage){
                page = totalPage;
            }
            authRegisterUserInfoList = authRegisterUserDao.selectByPage(100, (page -1) * LIMIT, LIMIT);
        }
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(SessionListener.userSessionMap.containsKey(authRegisterUserInfo.getUsername())){
                authRegisterUserInfo.setOnline(true);
            }
        }

        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("authRegisterUserInfoList", authRegisterUserInfoList);
        return "manager/customers";
    }

    public String users1(HttpSession session, Model model){
        String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(username == null){
            throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        AuthManagerInfo authManagerInfo = authManagerDao.selectOneByUsername(username);
        List<AuthRegisterUserInfo> authRegisterUserInfoList;
        if(authManagerInfo.getPermission() >= AuthManagerInfo.LEVEL_HIGHEST ) {
            authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        }else{
            authRegisterUserInfoList = authRegisterUserDao.selectAll(100);
        }
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(SessionListener.userSessionMap.containsKey(authRegisterUserInfo.getUsername())){
                authRegisterUserInfo.setOnline(true);
            }
        }
        model.addAttribute("authRegisterUserInfoList", authRegisterUserInfoList);
        return "manager/customers";
    }


    public ResultInfo listUsers(HttpSession session){
        String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(username == null){
            throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        AuthManagerInfo authManagerInfo = authManagerDao.selectOneByUsername(username);
        List<AuthRegisterUserInfo> authRegisterUserInfoList;
        if(authManagerInfo.getPermission() >= AuthManagerInfo.LEVEL_HIGHEST ) {
            authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        }else{
            authRegisterUserInfoList = authRegisterUserDao.selectAll(100);
        }
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(SessionListener.userSessionMap.containsKey(authRegisterUserInfo.getUsername())){
                authRegisterUserInfo.setOnline(true);
            }
        }
        return ResultMaster.success(authRegisterUserInfoList);
    }

    public AuthRegisterUserInfo userDetails(int id){
        return authRegisterUserDao.selectOneById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo activate(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setToken(MD5Util.create64(id+""));
        authRegisterUserDao.updateEmailStatusById(authRegisterUserInfo);
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo delete(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        int i = authRegisterUserDao.deleteOneById(authRegisterUserInfo);
        if(i != 1){
            throw new XException(1001, "delete failure");
        }
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateLevel(HttpServletRequest request, int id, int level, Date expiresTime){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setLevel(level);
        if(level <= 1){
            authRegisterUserInfo.setExpiresTime(new Date(TimeUtil.DEFAULT_TIME));
        }else {
            authRegisterUserInfo.setExpiresTime(expiresTime);
        }
        authRegisterUserDao.updateLevelById(authRegisterUserInfo);
        authRegisterUserInfo = authRegisterUserDao.selectOneById(id);
        LogUserLevelInfo logUserLevelInfo = LogUserLevelInfo.createFromRegisterUser(authRegisterUserInfo);
        logUserLevelInfo.setExecutorId(getManager(request).getId());
        logUserLevelDao.insertOne(logUserLevelInfo);
        return ResultMaster.success(authRegisterUserInfo);
    }

    public ResultInfo export(HttpServletRequest request, String[] macs){
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao.selectExportUsers(macs);
        List<String> list = new ArrayList<>();
        try {
            Class clasz = Class.forName("com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo");
            Field[] fields = clasz.getDeclaredFields();
            for(Field field: fields){
                String fieldName = field.getName();
                list.add(fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = ExcelExporter.export(request, authRegisterUserInfoList, list, "register user information");
        String fileName = ExcelExporter.copyFile(path);
        String url = "http://www.ldlegacy.com:8899/static/panel/export/" + fileName;
        return ResultMaster.success(url);
    }

    public String level(Model model){
        LevelDistributionInfo levelDistributionInfo = authRegisterUserDao.selectAllLevelDistribution();
        model.addAttribute("levelDistributionInfo", levelDistributionInfo);
        return "manager/level";
    }

    public String distribution(){
        return "manager/distribution";
    }


    public List<VolumeDistributionInfo> getDistributionData(){
        return authRegisterUserDao.getDistributionData();
    }

    public List<LogUserLevelInfo> selectUserLevelLogs(){
        return logUserLevelDao.selectAll();
    }

    public ResultInfo getMonthVolume(int year, int month){
        List<MonthVolumeInfo> monthVolumeInfoList = authRegisterUserDao
                .selectVolumeOfMonth(new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE));
        return ResultMaster.success(monthVolumeInfoList);
    }

    public ResultInfo getYearVolume(int year, int month){
        List<YearVolumeInfo> yearVolumeInfoList = authRegisterUserDao
                .selectVolumeOfYear(new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE, true));
        return ResultMaster.success(yearVolumeInfoList);
    }

    public ResultInfo<Integer> getLevelChart(int level, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, AuthRentUserInfo.DISTRIBUTOR_LDE);
        //sale id replace level
        yearOrMonthInfo.setSalesId(level + "");
        return ResultMaster.success(authRegisterUserDao.selectLevelOfYear(yearOrMonthInfo));
    }

    private AuthManagerInfo getManager(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authManagerDao.selectOneByUsername(username);
    }

}
