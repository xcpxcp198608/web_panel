package com.wiatec.panel.service;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdGroupDao;
import com.wiatec.panel.oxm.dao.LdGroupMemberDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdGroupInfo;
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
public class LdGroupService {

    private final Logger logger = LoggerFactory.getLogger(LdGroupService.class);

    @Resource
    private LdGroupDao ldGroupDao;
    @Resource
    private LdGroupMemberDao ldGroupMemberDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    public ResultInfo<LdGroupInfo> getGroupsById(int ownerId, int type){
        List<LdGroupInfo> groupList = new ArrayList<>();
        if(type == 1) {
            groupList = ldGroupDao.getGroupsByOwnerId(ownerId);
        }else if(type == 0) {
            List<Integer> memberIds = ldGroupMemberDao.selectGroupIdByMemberId(ownerId);
            groupList = ldGroupDao.getGroupsByOwnerIds(memberIds);
        }
        if(groupList == null || groupList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(groupList);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createGroup(int ownerId, String name, String description, String icon){
        List<LdGroupInfo> groupList = ldGroupDao.getGroupsByOwnerId(ownerId);
        int i = 1;
        if(groupList != null){
            if (groupList.size() >= 5){
                throw new XException("you only can create 5 groups");
            }
            i = groupList.size() + 1;
        }
        int groupId = ownerId * 10 + i;
        LdGroupInfo ldGroupInfo = new LdGroupInfo();
        ldGroupInfo.setOwnerId(ownerId);
        ldGroupInfo.setGroupId(groupId);
        ldGroupInfo.setName(name);
        ldGroupInfo.setDescription(description);
        ldGroupInfo.setIcon(icon);
        if(ldGroupDao.insetOne(ldGroupInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(ldGroupMemberDao.insertOne(groupId, ownerId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

    public ResultInfo updateByGroupId(int groupId, String name, String description, String icon){
        if(ldGroupDao.updateByGroupId(groupId, name, description, icon) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo deleteGroupById(int ownerId, int groupId){
        if(ldGroupDao.deleteOne(ownerId, groupId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        if(ldGroupMemberDao.deleteAllByGroupId(groupId) <= 0){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }

    public ResultInfo getGroupMembers(int groupId){
        List<AuthRegisterUserInfo> userInfoList = ldGroupMemberDao.selectAllMembersById(groupId);
        if(userInfoList == null || userInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(userInfoList);
    }

}
