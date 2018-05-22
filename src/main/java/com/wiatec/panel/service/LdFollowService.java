package com.wiatec.panel.service;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdFollowDao;
import com.wiatec.panel.oxm.dao.LogUserOperationDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.log.LogUserOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class LdFollowService {

    private final Logger logger = LoggerFactory.getLogger(LdFollowService.class);

    @Resource private LdFollowDao ldFollowDao;
    @Resource private AuthRegisterUserDao authRegisterUserDao;
    @Resource private LogUserOperationDao logUserOperationDao;

    /**
     * get all follow user info by user id
     * @param followerId follower user id
     */
    public ResultInfo follows(int followerId){
        List<Integer> friendIds = ldFollowDao.selectFollowIdsByFollowerId(followerId);
        if(friendIds == null || friendIds.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao
                .selectMultiByUserId(friendIds);
        if(authRegisterUserInfoList == null || authRegisterUserInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(authRegisterUserInfoList);
    }

    public ResultInfo followStatus(int followerId, int followId){
        String status = "false";
        if(ldFollowDao.selectOne(followerId, followId) >= 1){
            status = "true";
        }
        return ResultMaster.success(status);
    }


    /**
     * set user relation
     * @param action 0->release follow, 1->follow
     * @param userId  user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo follow(int action, int userId, int friendId){
        int i;
        if(userId == friendId){
            throw new XException("can not follow yourself");
        }
        if(action == 0){
            i = ldFollowDao.deleteOne(userId, friendId);
            logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_DELETE, "cancel follow " + friendId);

        }else if(action == 1){
            if(ldFollowDao.selectOne(userId, friendId) >= 1){
                throw new XException("relation already exists");
            }
            i = ldFollowDao.insertOne(userId, friendId);
            logUserOperationDao.insertOne(userId, LogUserOperationInfo.TYPE_INSERT, "create follow " + friendId);
        }else{
            throw new XException("action error");
        }
        if(i != 1) {
            throw new XException("operation error");
        }
        return ResultMaster.success();
    }


    public ResultInfo<AuthRegisterUserInfo> getFollowers(int followId){
        List<Integer> userIds = ldFollowDao.selectFollowerIdsByFollowId(followId);
        if(userIds == null || userIds.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        List<AuthRegisterUserInfo> authRegisterUserInfoList = authRegisterUserDao
                .selectMultiByUserId(userIds);
        System.out.println(authRegisterUserInfoList);
        if(authRegisterUserInfoList == null || authRegisterUserInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(authRegisterUserInfoList);
    }

}
