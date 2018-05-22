package com.wiatec.panel.api;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.service.LdFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@RestController
public class LdFollow {

    @Resource private LdFollowService ldFollowService;

    /**
     *  get 2 user follow status
     * @param userId user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @GetMapping("/follow/status/{userId}/{friendId}")
    @ResponseBody
    public ResultInfo follow(@PathVariable int userId, @PathVariable int friendId){
        return ldFollowService.followStatus(userId, friendId);
    }

    /**
     *  add or delete a follow relationship
     * @param action 0-> delete, 1-> add
     * @param userId user id
     * @param friendId target user id
     * @return ResultInfo
     */
    @PostMapping("/follow/{action}/{userId}/{friendId}")
    @ResponseBody
    public ResultInfo follow(@PathVariable int action, @PathVariable int userId, @PathVariable int friendId){
        return ldFollowService.follow(action, userId, friendId);
    }


    /**
     *  list of all follows by user id
     * @param userId use id
     * @return ResultInfo with list of userInfo
     */
    @GetMapping("/follows/{userId}")
    @ResponseBody
    public ResultInfo follows(@PathVariable int userId){
        return ldFollowService.follows(userId);
    }


    /**
     * list all of user's followers
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/followers/{userId}")
    @ResponseBody
    public ResultInfo getFollowers(@PathVariable int userId){
        return ldFollowService.getFollowers(userId);
    }


}
