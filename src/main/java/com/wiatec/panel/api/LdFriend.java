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
