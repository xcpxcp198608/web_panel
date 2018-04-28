package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.LdGroupInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdGroupDao {

    List<LdGroupInfo> getGroupsById(int ownerId);
    int insetOne(LdGroupInfo ldGroupInfo);
    int deleteOne(@Param("ownerId") int ownerId, @Param("groupId") int groupId);
    int updateNameByGroupId(@Param("groupId")int groupId, @Param("name")String name);
    int updateDescriptionByGroupId(@Param("groupId")int groupId, @Param("description")String description);
    int updateIconByGroupId(@Param("groupId")int groupId, @Param("icon")String icon);
    int updateNumbersByGroupId(@Param("groupId")int groupId);

}
