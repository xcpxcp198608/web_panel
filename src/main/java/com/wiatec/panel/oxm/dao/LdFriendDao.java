package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdFriendInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdFriendDao {

    List<AuthRegisterUserInfo> selectAllFriendsByUserId(int userId);
    List<Integer> selectAllFriendsId(int userId);
    LdFriendInfo selectOne(@Param("userId") int userId, @Param("friendId") int friendId);
    int insertOne(@Param("userId") int userId, @Param("friendId") int friendId, @Param("approved") int approved);
    int updateApproved(@Param("userId") int userId, @Param("friendId") int friendId);
    int deleteOne(@Param("userId") int userId, @Param("friendId") int friendId);
}
