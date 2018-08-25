package com.wiatec.panel.apns;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.oxm.dao.DeviceTokenDao;
import com.wiatec.panel.oxm.pojo.DeviceTokenInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author patrick
 */
@Component
public class APNsMaster {

    private static final Logger logger = LoggerFactory.getLogger(APNsMaster.class);

    private static final String TOKEN_41 = "809267062d69e53db70ab23622317b959e97b29ee72d3b4fee8438e2360ea887";

    public static final String ACTION_LIVE_START = "ACTION_LIVE_START";
    public static final String ACTION_TRENDING = "ACTION_TRENDING";
    public static final String ACTION_NEW_FRIEND_REQUEST = "ACTION_NEW_FRIEND_REQUEST";

    private static final String PATH = "/Users/xuchengpeng/IdeaProjects/panel/src/main/resources/aps_development.com.legacy.bvision.p12";
//    private static final String PATH = "/home/java_app/panel/csr/aps_development.com.legacy.bvision.p12";
    private static ApnsService service;
    private static DeviceTokenDao deviceTokenDao;

    static {
        try {
            SqlSession sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
            deviceTokenDao = sqlSession.getMapper(DeviceTokenDao.class);
            service = APNS.newService()
                    .withCert(PATH, "123456")
                    .withSandboxDestination()
                    .build();
        } catch (Exception e) {
            logger.error("apnsClient create error", e);
        }
    }

    private static String getTitile(String action){
        String title = "";
        if(ACTION_LIVE_START.equals(action)){
            title = "Live start";
        }else if(ACTION_TRENDING.equals(action)){
            title = "New trending";
        }else if(ACTION_TRENDING.equals(action)){
            title = "New friend request";
        }
        return title;
    }

    public static void send(int playerId, int userId, String action, String content){
        String token = deviceTokenDao.selectOne(userId);
        if(TextUtil.isEmpty(token)){
            return;
        }
        String payload = APNS.newPayload()
                .badge(1)
                .sound("default")
                .alertAction(action)
                .alertTitle(getTitile(action))
                .alertBody(content)
                .actionKey(playerId+"")
                .customField("content-available", 1)
                .build();
        if(service != null) {
            service.push(token, payload);
        }
    }


    public static void batchSend(int playerId, List<Integer> userIds, String action, String content){
        List<DeviceTokenInfo> deviceTokenInfos = deviceTokenDao.batchSelect(userIds);
        if(deviceTokenInfos == null || deviceTokenInfos.size() <= 0){
            return;
        }
        for (DeviceTokenInfo deviceTokenInfo: deviceTokenInfos){
            if(TextUtil.isEmpty(deviceTokenInfo.getDeviceToken())){
                continue;
            }
            String payload = APNS.newPayload()
                    .badge(1)
                    .sound("default")
                    .alertAction(action)
                    .alertTitle(getTitile(action))
                    .alertBody(content)
                    .actionKey(playerId+"")
                    .customField("content-available", 1)
                    .build();
            if(service != null) {
                service.push(deviceTokenInfo.getDeviceToken(), payload);
            }
        }
    }



}
