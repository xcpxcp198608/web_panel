package com.wiatec.panel.service;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdGroupDao;
import com.wiatec.panel.oxm.dao.LdGroupMemberDao;
import com.wiatec.panel.oxm.dao.LdIllegalWordDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdGroupInfo;
import com.wiatec.panel.rongc.RCManager;
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
    private LdIllegalWordDao ldIllegalWordDao;
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
            List<Integer> groupIds = ldGroupMemberDao.selectGroupIdByMemberId(ownerId);
            List<LdGroupInfo> groups = ldGroupDao.getGroupsByGroupIds(groupIds);
            for(LdGroupInfo ldGroupInfo: groups){
                if(ldGroupInfo.getOwnerId() != ownerId){
                    groupList.add(ldGroupInfo);
                }
            }
        }
        if(groupList == null || groupList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(groupList);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createGroup(int ownerId, String name, String description, String icon){
        List<String> strings = ldIllegalWordDao.selectAll();
        for(String key: strings){
            if(name.contains(key) || description.contains(key)){
                throw new XException("group name or description contains illegal word");
            }
        }
        if(ldGroupDao.countName(name) == 1){
            throw new XException("group name already exists");
        }
        List<LdGroupInfo> groupList = ldGroupDao.getGroupsByOwnerId(ownerId);
        int i = 1;
        if(groupList != null){
            if (groupList.size() >= 8){
                throw new XException("you only can create 8 groups");
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
        boolean rcGroup = RCManager.createGroup(ownerId, groupId, name);
        if(!rcGroup){
            throw new XException("group create fail(rc)");
        }
        return ResultMaster.success();
    }

    public ResultInfo updateByGroupId(int groupId, String name, String description, String icon){
        if(ldGroupDao.updateByGroupId(groupId, name, description, icon) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        boolean rcGroup = RCManager.updateGroupName(groupId, name);
        if(!rcGroup){
            throw new XException("group info update fail(rc)");
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

        boolean rcGroup = RCManager.dismissGroup(ownerId, groupId);
        if(!rcGroup){
            throw new XException("group dismiss fail(rc)");
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


    public ResultInfo addMember(int groupId, int userId, String username){
        LdGroupInfo ldGroupInfo = ldGroupDao.selectOneByGroupId(groupId);
        if(ldGroupInfo == null){
            throw new XException("group does not exists");
        }
        if(TextUtil.isEmpty(username)){
            if(userId <= 0){
                throw new XException("user does not exists");
            }
            if(ldGroupMemberDao.countByGroupIdAndMemberId(groupId, userId) == 1){
                throw new XException("this user already in group");
            }
            if(ldGroupMemberDao.insertOne(groupId, userId) !=1){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }else{
            AuthRegisterUserInfo userInfo = new AuthRegisterUserInfo();
            userInfo.setUsername(username.trim());
            AuthRegisterUserInfo userInfo1 = authRegisterUserDao.selectOneByUsername(userInfo);
            if(userInfo1 == null){
                throw new XException("user does not exists");
            }
            if(ldGroupMemberDao.countByGroupIdAndMemberId(groupId, userId) == 1){
                throw new XException("this user already in group");
            }
            if(ldGroupMemberDao.insertOne(groupId, userInfo1.getId()) != 1){
                throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
            }
        }
        boolean rcGroup = RCManager.joinGroup(userId, groupId, ldGroupInfo.getName());
        if(!rcGroup){
            throw new XException("group join fail(rc)");
        }
        return ResultMaster.success();
    }


    public ResultInfo deleteMember(int groupId, int memberId){
        if(ldGroupMemberDao.deleteOne(groupId, memberId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        boolean rcGroup = RCManager.quitGroup(memberId, groupId);
        if(!rcGroup){
            throw new XException("group quit fail(rc)");
        }
        return ResultMaster.success();
    }

}
