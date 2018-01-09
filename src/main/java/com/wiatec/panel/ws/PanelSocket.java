package com.wiatec.panel.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author patrick
 */
@ServerEndpoint("/socket/{mac}")
public class PanelSocket {

    private static final Logger logger = LoggerFactory.getLogger(PanelSocket.class);
    private static final String MSG_TYPE_FULL = "0";
    private static final String MSG_TYPE_SIGNAL = "1";
    private static final String MSG_EVENT_LIMITED_REGISTER = "e1";
    private static final String MSG_EVENT_LIMITED_RENT = "e2";

    private static Map<String, PanelSocket> clientMap = new ConcurrentHashMap<>();
    private Session session;
    private String mac;

    @OnOpen
    public void onOpen(Session session, @PathParam("mac")String mac) {
        this.session = session;
        this.mac = mac;
        clientMap.put(mac, this);
        logger.debug("ws -> new client " + mac + " connect, current client is: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        String[] msg = message.split("/");
        String type = msg[0];
        String event = msg[1];
        String mac = msg[2];
        logger.debug("ws -> type: {}", type);
        logger.debug("ws -> event: {}", event);
        logger.debug("ws -> target: {}", mac);
        if(MSG_TYPE_FULL.equals(type)){
            fullNotice(event);
        }else if(MSG_TYPE_SIGNAL.equals(type)){
            switch (event){
                case MSG_EVENT_LIMITED_REGISTER:
                    limitAuthRegister(mac);
                    break;
                case MSG_EVENT_LIMITED_RENT:
                    limitAuthRent(mac);
                    break;
                default:
                    break;
            }
            signalNotice(mac, event);
        }

    }

    private void fullNotice(String mac) {
        for(Map.Entry<String, PanelSocket> entry: clientMap.entrySet()){
            try {
                entry.getValue().sendMessage(mac);
            } catch (Exception e) {
                logger.error("ws -> Exception", e);
                continue;
            }
        }
    }

    private void limitAuthRegister(String mac) {
        //database operation
    }

    private void limitAuthRent(String mac){
        //database operation
    }

    private void signalNotice(String mac, String event){
        try{
            if(clientMap.containsKey(mac)){
                clientMap.get(mac).sendMessage(event);
            }
        } catch (Exception e) {
            logger.error("ws -> Exception", e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        clientMap.remove(mac);
        logger.debug("ws -> one client disconnect, current client is: {}", getOnlineCount());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        clientMap.remove(mac);
        logger.error("ws -> Exception: ", t);
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return clientMap.size();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
