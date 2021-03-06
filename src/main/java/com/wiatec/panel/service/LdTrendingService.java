package com.wiatec.panel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiatec.panel.apns.APNsMaster;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.dto.LdTrendingVoteCountInfo;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdFriendDao;
import com.wiatec.panel.oxm.dao.LdTrendingDao;
import com.wiatec.panel.oxm.dao.LdTrendingVoteDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdTrendingInfo;
import com.wiatec.panel.oxm.pojo.LdTrendingVoteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author patrick
 */
@Service
public class LdTrendingService {

    private final Logger logger = LoggerFactory.getLogger(LdTrendingService.class);

    @Resource
    private LdFriendDao ldFriendDao;
    @Resource
    private LdTrendingDao ldTrendingDao;
    @Resource
    private LdTrendingVoteDao ldTrendingVoteDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    public ResultInfo getFriendsTrending(int userId, int pageNum, int pageSize){

        List<Integer> friendsIds = ldFriendDao.selectAllFriendsId(userId);
        friendsIds.add(0);
        friendsIds.add(userId);

        //start page
        PageHelper.startPage(pageNum, pageSize);
        List<LdTrendingInfo> ldTrendingInfoList = ldTrendingDao.selectByFriendIds(friendsIds);
        if(ldTrendingInfoList == null || ldTrendingInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        // query all trending votes
        List<LdTrendingVoteCountInfo> upVotes = ldTrendingVoteDao.countByTrendingIds(ldTrendingInfoList.stream()
                .map(LdTrendingInfo::getId)
                .collect(Collectors.toList()));

        // query does up vote trending
        List<LdTrendingVoteInfo> ldTrendingVoteInfoList = ldTrendingVoteDao.selectTrendingByUserId(userId);

        for(LdTrendingInfo ldTrendingInfo: ldTrendingInfoList){
            for(LdTrendingVoteCountInfo countInfo: upVotes){
                if(ldTrendingInfo.getId() == countInfo.getTrendingId()){
                    ldTrendingInfo.setVoteCount(countInfo.getCount());
                }
            }
            for(LdTrendingVoteInfo ldTrendingVoteInfo: ldTrendingVoteInfoList){
                if(ldTrendingVoteInfo.getTrendingId() == ldTrendingInfo.getId()){
                    ldTrendingInfo.setVoted(true);
                }
            }
        }
        //end page
        PageInfo pageInfo = new PageInfo(ldTrendingInfoList);
        return ResultMaster.success(pageInfo);
    }


    public ResultInfo<LdTrendingInfo> getUserTrending(int userId, int start){
        List<LdTrendingInfo> ldTrendingInfoList = ldTrendingDao.selectByUserId(userId, start);
        if(ldTrendingInfoList == null || ldTrendingInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        return ResultMaster.success(ldTrendingInfoList);
    }

    public ResultInfo deleteTrending(int userId, int trendingId){
        if(ldTrendingDao.deleteTrending(userId, trendingId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }


    public ResultInfo publishTrending(int userId, String content, int imgCount, String imgUrl, String link){
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo == null){
            throw new XException("user does not exists");
        }
        LdTrendingInfo ldTrendingInfo = new LdTrendingInfo();
        ldTrendingInfo.setUserId(userId);
        ldTrendingInfo.setUsername(userInfo.getUsername());
        ldTrendingInfo.setIcon(userInfo.getIcon());
        ldTrendingInfo.setContent(content);
        ldTrendingInfo.setImgCount(imgCount);
        ldTrendingInfo.setImgUrl(imgUrl);
        ldTrendingInfo.setLink(link);
        if(ldTrendingDao.insertOne(ldTrendingInfo) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        List<Integer> allFriendsId = ldFriendDao.selectAllFriendsId(userId);
        if(allFriendsId != null && allFriendsId.size() > 0) {
            APNsMaster.batchSend(userId, allFriendsId, APNsMaster.ACTION_TRENDING, "from " + userInfo.getUsername());
        }
        return ResultMaster.success();
    }



    public ResultInfo upVote(int userId, int trendingId){
        if(ldTrendingVoteDao.countOne(trendingId, userId) == 1){
            throw new XException("already upvote");
        }
        if(ldTrendingVoteDao.insertOne(trendingId, userId) != 1){
            throw new XException(EnumResult.ERROR_INTERNAL_SERVER_SQL);
        }
        return ResultMaster.success();
    }


    public ResultInfo checkNew(int userId, int lastId){
        List<Integer> friendsIds = ldFriendDao.selectAllFriendsId(userId);
        friendsIds.add(0);
        List<LdTrendingInfo> ldTrendingInfoList = ldTrendingDao.selectByFriendIds(friendsIds);
        if(ldTrendingInfoList == null || ldTrendingInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }
        if(ldTrendingInfoList.get(0).getId() <= lastId){
            throw new XException("no new trending");
        }
        return ResultMaster.success();
    }

}
