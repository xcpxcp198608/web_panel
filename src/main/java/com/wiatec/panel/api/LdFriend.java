package com.wiatec.panel.api;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.service.LdFriendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@ResponseBody
@RequestMapping(value = "/friends")
public class LdFriend {

    @Resource
    private LdFriendService ldFriendService;

    /**
     * get user info of all friends by id
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/{userId}")
    public ResultInfo getFriendsById(@PathVariable int userId){
        return ldFriendService.getAllFriends(userId);
    }

    /**
     * get ask be friend request
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/ask/{userId}")
    public ResultInfo getAskFriends(@PathVariable int userId){
        return ldFriendService.getAskFriends(userId);
    }



    /**
     * search user by keyword in username
     * @param userId user id
     * @param keyword keyword
     * @return ResultInfo
     */
    @GetMapping("/{userId}/{keyword}")
    public ResultInfo searchFriend(@PathVariable int userId, @PathVariable String keyword){
        return ldFriendService.searchUsers(userId, keyword);
    }


    /**
     * check friend relation by id
     * @param userId user id
     * @param friendId friend id
     * @return ResultInfo
     */
    @GetMapping("/check/{userId}/{friendId}")
    public ResultInfo checkFriend(@PathVariable int userId, @PathVariable int friendId){
        return ldFriendService.checkFriend(userId, friendId);
    }


    /**
     * add or delete friend
     * @param action 0-> delete, 1-> add, 2-> agree be friend
     * @param userId usr id
     * @param friendId friend id
     * @return ResultInfo
     */
    @PostMapping("/{action}/{userId}/{friendId}")
    public ResultInfo addOrDeleteFriends(@PathVariable int action, @PathVariable int userId,
                                         @PathVariable int friendId){
        return ldFriendService.friendOperation(action, userId, friendId);
    }

}
