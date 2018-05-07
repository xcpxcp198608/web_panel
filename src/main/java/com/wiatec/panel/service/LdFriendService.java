package com.wiatec.panel.service;

import com.wiatec.panel.common.result.*;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdFriendDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdFriendInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class LdFriendService {

    private final Logger logger = LoggerFactory.getLogger(LdFriendService.class);

    @Resource
    private LdFriendDao ldFriendDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    public ResultInfo<AuthRegisterUserInfo> getAllFriends(int userId){
        List<AuthRegisterUserInfo> userInfoList = new ArrayList<>();
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(41);
        userInfo.setPassword("");
        userInfoList.add(userInfo);
        List<AuthRegisterUserInfo> userInfoList1 = ldFriendDao.selectAllFriendsByUserId(userId);
        if(userInfoList1 != null){
            userInfoList.addAll(userInfoList1);
        }
        return ResultMaster.success(userInfoList);
    }


    public ResultInfo<AuthRegisterUserInfo> getAskFriends(int userId){
        List<AuthRegisterUserInfo> userInfoList = ldFriendDao.selectAskFriendsByUserId(userId);
        if(userInfoList == null || userInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(userInfoList);
    }


    public ResultInfo<AuthRegisterUserInfo> searchUsers(int userId, String keyword){
        List<AuthRegisterUserInfo> userInfoList = authRegisterUserDao.selectByKeywordInUsername("%" + keyword + "%");
        if(userInfoList == null || userInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(userInfoList);
    }


    public ResultInfo checkFriend(int userId, int friendId){
        LdFriendInfo ldFriendInfo = ldFriendDao.selectOne(userId, friendId);
        if(ldFriendInfo == null){
            throw new XException(9001, "has no add yet");
        }
        if(!ldFriendInfo.isApproved()){
            throw new XException(9002, "friend has no approve your request");
        }
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo friendOperation(int action, int userId, int friendId){
        if (userId == friendId){
            throw new XException("this operation can not do in yourself");
        }
        LdFriendInfo ldFriendInfo = ldFriendDao.selectOne(userId, friendId);
        switch (action){
            case 0:
                if(ldFriendInfo == null || !ldFriendInfo.isApproved()){
                    throw new XException("your are not friends");
                }
                if(ldFriendDao.deleteOne(userId, friendId) != 1){
                    throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                }
                if(ldFriendDao.deleteOne(friendId, userId) != 1){
                    throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                }
                break;
            case 1:
                if(ldFriendInfo != null){
                    if(ldFriendInfo.isApproved()) {
                        throw new XException("your are already be friend");
                    }else{
                        throw new XException("your are already request with friend, waiting for approve");
                    }
                }
                if(ldFriendDao.insertOne(userId, friendId, 0) != 1){
                    throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                }
                break;
            case 2:
                ldFriendInfo = ldFriendDao.selectOne(friendId, userId);
                if(ldFriendInfo == null){
                    throw new XException("your are not friends");
                }
                if(ldFriendInfo.isApproved()){
                    throw new XException("your are already be friend");
                }
                if(ldFriendDao.updateApproved(friendId, userId) != 1){
                    throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                }
                LdFriendInfo ldFriendInfo1 = ldFriendDao.selectOne(userId, friendId);
                if(ldFriendInfo1 == null) {
                    if (ldFriendDao.insertOne(userId, friendId, 1) != 1) {
                        throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                    }
                }else{
                    if(ldFriendDao.updateApproved(userId, friendId) != 1){
                        throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
                    }
                }
                break;
            default:
                throw new XException(EnumResult.ERROR_BAD_REQUEST);
        }

        return ResultMaster.success();
    }
}
