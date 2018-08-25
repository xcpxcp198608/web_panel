package com.wiatec.panel.api;


import com.wiatec.panel.common.Constant;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.utils.FtpUtils;
import com.wiatec.panel.ftp.FileInfo;
import com.wiatec.panel.ftp.FtpMaster;
import com.wiatec.panel.ftp.FtpService;
import com.wiatec.panel.service.LdTrendingService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick
 */
@RestController
@RequestMapping(value = "/trending")
public class LdTrending {

    @Resource
    private LdTrendingService ldTrendingService;
    @Resource private FtpMaster ftpMaster;

    /**
     * get all friends trending info by id
     * @param userId user id
     * @return ResultInfo
     */
    @GetMapping("/{userId}")
    public ResultInfo getAllFriendTrending(@PathVariable int userId,
                                           @RequestParam(required = false, defaultValue = "1") int pageNum,
                                           @RequestParam(required = false, defaultValue = "20") int pageSize){
        return ldTrendingService.getFriendsTrending(userId, pageNum, pageSize);
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
    public ResultInfo upload(HttpServletRequest request, @PathVariable int userId,
                             @RequestParam(required = false) MultipartFile[] files,
                             @RequestParam(required = false) String content,
                             @RequestParam(required = false) String link) throws IOException {
        int imgCount = 0;
        String imgUrl = "";
        StringBuilder stringBuilder = new StringBuilder();
        String path = request.getServletContext().getRealPath("/upload");
        List<File> fileList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i ++) {
                MultipartFile file = files[i];
                String [] ms = file.getOriginalFilename().split("\\.");
                String fileName = userId + "_" + System.currentTimeMillis() + "" + i + "." + ms[ms.length - 1];

                File file1 = new File(path);
                if(!file1.exists()){
                    file1.mkdir();
                }
                File localFile = new File(file1, fileName);
                FileUtils.writeByteArrayToFile(localFile, file.getBytes());
                fileList.add(localFile);
                stringBuilder.append(Constant.TRENDING_IMG_URL1);
                stringBuilder.append(fileName);
                stringBuilder.append("#");
            }
        }
        Runtime.getRuntime().exec("chmod -R 777 " + path);
        if(fileList.size() > 0) {
            ftpMaster.upload(fileList, "trending/");
        }
        imgCount = files.length;
        imgUrl = stringBuilder.toString();
        imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
        return ldTrendingService.publishTrending(userId, content, imgCount, imgUrl, link);
    }

    /**
     * user up vote one trending by id
     * @param userId user id
     * @param trendingId trending id
     */
    @PostMapping("/vote/{userId}/{trendingId}")
    public ResultInfo upVote(@PathVariable int userId, @PathVariable int trendingId){
        return ldTrendingService.upVote(userId, trendingId);
    }

    /**
     * 检查用户是否有新的未看的trending
     * @param userId user id
     * @param lastId 用户看过的最后一条trending的id
     * @return ResultInfos
     */
    @GetMapping("/check/{userId}/{lastId}")
    public ResultInfo checkNew(@PathVariable int userId, @PathVariable int lastId){
        return ldTrendingService.checkNew(userId, lastId);
    }

}
