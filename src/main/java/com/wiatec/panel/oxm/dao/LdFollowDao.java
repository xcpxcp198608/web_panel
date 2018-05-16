package com.wiatec.panel.oxm.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdFollowDao {

    int selectOne(@Param("followerId") int followerId, @Param("followId") int followId);

    int insertOne(@Param("followerId") int followerId, @Param("followId") int followId);
    int deleteOne(@Param("followerId") int followerId, @Param("followId") int followId);

    List<Integer> selectFollowIdsByFollowerId(int followerId);
    List<Integer> selectFollowerIdsByFollowId(int followId);

}
