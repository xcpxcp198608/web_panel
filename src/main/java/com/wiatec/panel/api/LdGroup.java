package com.wiatec.panel.api;

import com.wiatec.panel.common.Constant;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.service.LdGroupMemberService;
import com.wiatec.panel.service.LdGroupService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author patrick
 */
@Controller
@ResponseBody
@RequestMapping(value = "/groups")
public class LdGroup {

    @Resource
    private LdGroupService ldGroupService;

    /**
     * get groups by id
     * @param ownerId owner id
     * @return ResultInfo
     */
    @GetMapping("/{ownerId}")
    public ResultInfo getGroupsById(@PathVariable int ownerId){
        return ldGroupService.getGroupsById(ownerId);
    }

    /**
     * create group
     * @param ownerId owner id
     * @param name group name
     * @param description group description
     * @return ResultInfo
     */
    @PostMapping("/{ownerId}")
    public ResultInfo createGroup(@PathVariable int ownerId, String name, String description,
                                  @RequestParam(required = false) MultipartFile file,
                                  HttpSession session) throws IOException {
        String icon = "";
        if(file != null) {
            icon = uploadFile(ownerId, file, session);
        }
        return ldGroupService.createGroup(ownerId, name, description, icon);
    }

    /**
     * delete group
     * @return ResultInfo
     */
    @DeleteMapping("/{ownerId}/{groupId}")
    public ResultInfo deleteGroup(@PathVariable int ownerId, @PathVariable int groupId){
        return ldGroupService.deleteGroupById(ownerId, groupId);
    }


    /**
     * update group information
     * @param groupId  groupId
     * @param action 1-> update name, 2-> update description
     * @param name name
     * @param description description
     * @return ResultInfo
     */
    @PutMapping("/{groupId}/{action}")
    public ResultInfo updateGroupInfo(@PathVariable int groupId, @PathVariable int action,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String description){
        switch (action){
            case 1:
                return ldGroupService.updateNameByOwnerId(groupId, name);
            case 2:
                return ldGroupService.updateDescriptionByOwnerId(groupId, description);
            default:
                throw new XException(EnumResult.ERROR_NO_FOUND);
        }
    }


    /**
     * update group icon
     * @param groupId groupId
     * @param file file
     * @return ResultInfo
     */
    @PostMapping("/icon/{ownerId}/{groupId}")
    public ResultInfo updateIcon(@PathVariable int ownerId, @PathVariable int groupId,
                                      MultipartFile file, HttpSession session) throws IOException {
        String icon = uploadFile(ownerId, file, session);
        return ldGroupService.updateIconByOwnerId(groupId, icon);
    }


    /**
     * get all member user info in group by group id
     * @param groupId group id
     * @return ResultInfo
     */
    @GetMapping("/{groupId}/members")
    public ResultInfo getGroupMembers(@PathVariable int groupId){
        return ldGroupService.getGroupMembers(groupId);
    }



    private String uploadFile(int ownerId, MultipartFile file, HttpSession session) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(file != null){
            //String path = session.getServletContext().getRealPath("/Resource/groups/user_" + ownerId);
            String path = "/home/static/panel/image/groups/user_" + ownerId;
            File file1 = new File(path);
            if(!file1.exists()){
                file1.mkdir();
            }
            FileUtils.writeByteArrayToFile(new File(file1, file.getOriginalFilename()), file.getBytes());
            stringBuilder.append(Constant.GROUP_IMG_URL);
            stringBuilder.append(ownerId);
            stringBuilder.append("/");
            stringBuilder.append(file.getOriginalFilename());
        }
        return stringBuilder.toString();
    }

}
