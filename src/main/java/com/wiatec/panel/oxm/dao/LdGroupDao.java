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

    List<LdGroupInfo> getGroupsByOwnerId(int ownerId);
    List<LdGroupInfo> getGroupsByOwnerIds(@Param("ownerIds") List<Integer> ownerIds);
    List<LdGroupInfo> getGroupsByGroupIds(@Param("groupIds") List<Integer> groupIds);

    int countName(String name);
    int insetOne(LdGroupInfo ldGroupInfo);
    int deleteOne(@Param("ownerId") int ownerId, @Param("groupId") int groupId);

    int updateByGroupId(@Param("groupId")int groupId, @Param("name")String name,
                        @Param("description")String description, @Param("icon")String icon);

    int updateNumbersByGroupId(int groupId);

    LdGroupInfo selectOneByGroupId(int groupId);

}
