package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdGroupMemberDao {

    int insertOne(@Param("groupId") int groupId, @Param("memberId") int memberId);

    int deleteAllByGroupId(int groupId);

    List<AuthRegisterUserInfo> selectAllMembersById(int groupId);

    List<Integer> selectGroupIdByMemberId(int memberId);

    int countByGroupIdAndMemberId(@Param("groupId") int groupId, @Param("memberId") int memberId);

    int deleteOne(@Param("groupId") int groupId, @Param("memberId") int memberId);
}
