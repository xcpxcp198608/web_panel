package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.dto.LdTrendingVoteCountInfo;
import com.wiatec.panel.oxm.pojo.LdTrendingVoteInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author patrick
 */
@Repository
public interface LdTrendingVoteDao {

    int insertOne(@Param("trendingId") int trendingId, @Param("userId") int userId);

    int deleteOne(@Param("trendingId") int trendingId, @Param("userId") int userId);

    int countOne(@Param("trendingId") int trendingId, @Param("userId") int userId);

    int countByTrendingId(int trendingId);

    List<LdTrendingVoteCountInfo> countByTrendingIds(@Param("trendingIds")List<Integer> trendingIds);
    List<LdTrendingVoteInfo> selectTrendingByUserId(int userId);

    int deleteByTrendingId(int trendingId);


}
