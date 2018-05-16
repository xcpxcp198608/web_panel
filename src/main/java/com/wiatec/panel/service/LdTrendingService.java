package com.wiatec.panel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wiatec.panel.apns.APNsMaster;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.LdFriendDao;
import com.wiatec.panel.oxm.dao.LdTrendingDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.LdTrendingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private AuthRegisterUserDao authRegisterUserDao;

    public ResultInfo getFriendsTrending(int userId, int start, int pageNum, int pageSize, String order){
        //start page
//        PageHelper.startPage(pageNum, pageSize);


        List<Integer> friendsIds = ldFriendDao.selectAllFriendsId(userId);
        friendsIds.add(0);
        friendsIds.add(userId);

        List<LdTrendingInfo> ldTrendingInfoList = ldTrendingDao.selectByFriendIds(friendsIds, start);
        if(ldTrendingInfoList == null || ldTrendingInfoList.size() <= 0){
            throw new XException(EnumResult.ERROR_NO_FOUND);
        }


        //end page
//        PageInfo pageInfo = new PageInfo(ldTrendingInfoList);
        return ResultMaster.success(ldTrendingInfoList);
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
            APNsMaster.batchSend(userId, allFriendsId, APNsMaster.ACTION_TRENDING, "");
        }
        return ResultMaster.success();
    }

}
