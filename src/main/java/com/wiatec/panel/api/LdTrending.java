package com.wiatec.panel.api;


import com.wiatec.panel.common.Constant;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.service.LdTrendingService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/trending")
public class LdTrending {

    @Resource
    private LdTrendingService ldTrendingService;

    /**
     * get all friends trending info by id
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/{userId}/{start}")
    public ResultInfo getAllFriendTrending(@PathVariable int userId, @PathVariable int start,
                                           @RequestParam(required = false, defaultValue = "1") int pageNum,
                                           @RequestParam(required = false, defaultValue = "50") int pageSize,
                                           @RequestParam(required = false, defaultValue = "id") String order){
        return ldTrendingService.getFriendsTrending(userId, start, pageNum, pageSize, order);
    }


    /**
     * delete a trending info by user id and trending id
     * @param userId user id
     * @param trendingId trending ids
     * @return ResultInfo
     */
    @DeleteMapping("/{userId}/{trendingId}")
    public ResultInfo deleteTrending(@PathVariable int userId, @PathVariable int trendingId){
        return ldTrendingService.deleteTrending(userId, trendingId);
    }


    /**
     * get user owner's trending info
     * @param userId user id
     * @param start start
     * @return ResultInfo
     */
    @GetMapping("/personal/{userId}/{start}")
    public ResultInfo getTrendingByUserId(@PathVariable int userId, @PathVariable int start){
        return ldTrendingService.getUserTrending(userId, start);
    }


    /**
     * insert new trending
     * @param files image files
     * @param userId user id
     * @param content content
     * @return ResultInfo
     */
    @PostMapping("/{userId}")
    public ResultInfo upload(HttpSession session, @PathVariable int userId,
                             @RequestParam(required = false) MultipartFile[] files,
                             @RequestParam(required = false) String content,
                             @RequestParam(required = false) String link) throws IOException {
        int imgCount = 0;
        String imgUrl = "";
        StringBuilder stringBuilder = new StringBuilder();

//        String path = session.getServletContext().getRealPath("/Resource/trending/user_" + userId);
        String path = "/home/static/panel/image/trending/user_" + userId;
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String [] ms = file.getOriginalFilename().split("\\.");
                String fileName = userId + "_" + System.currentTimeMillis() + "." + ms[ms.length - 1];

                File file1 = new File(path);
                if(!file1.exists()){
                    file1.mkdir();
                }
                FileUtils.writeByteArrayToFile(new File(file1, fileName), file.getBytes());
                stringBuilder.append(Constant.TRENDING_IMG_URL);
                stringBuilder.append(userId);
                stringBuilder.append("/");
                stringBuilder.append(fileName);
                stringBuilder.append("#");
            }
            Runtime.getRuntime().exec("chmod -R 777 " + path);
            imgCount = files.length;
            imgUrl = stringBuilder.toString();
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
        }
        return ldTrendingService.publishTrending(userId, content, imgCount, imgUrl, link);
    }

}
