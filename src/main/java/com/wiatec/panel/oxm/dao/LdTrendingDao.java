package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.LdTrendingInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LdTrendingDao {

    List<LdTrendingInfo> selectByUserId(@Param("userId") int userId, @Param("start") int start);
    List<LdTrendingInfo> selectByFriendIds(@Param("userIds") List<Integer> userIds, @Param("start") int start);
    int deleteTrending(@Param("userId") int userId, @Param("trendingId") int trendingId);
    int insertOne(LdTrendingInfo ldTrendingInfo);
}
