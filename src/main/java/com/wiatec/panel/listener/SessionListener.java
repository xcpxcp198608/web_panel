package com.wiatec.panel.listener;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

/**
 * monitor session
 */
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener {

    public static Map<String ,HttpSession> sessionMap = new HashMap<>();
    public static Map<String ,HttpSession> userSessionMap = new HashMap<>();
    public static Map<String, HttpSession> idSessionMap = new HashMap<>();
    public static final String KEY = "key";
    public static final String KEY_USER_NAME = "username";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" created");
        idSessionMap.put(httpSessionEvent.getSession().getId(), httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId()+" destroyed");
        idSessionMap.remove(httpSessionEvent.getSession().getId());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            String key = (String) httpSessionBindingEvent.getValue();
            HttpSession httpSession = sessionMap.remove(key);
            if(httpSession != null){
                httpSession.removeAttribute(KEY);
            }
            sessionMap.put(key, httpSessionBindingEvent.getSession());
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            String username = (String) httpSessionBindingEvent.getValue();
            HttpSession httpSession = userSessionMap.remove(username);
            if(httpSession != null){
                httpSession.removeAttribute(KEY_USER_NAME);
            }
            userSessionMap.put(username, httpSessionBindingEvent.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            try {
                String key = (String) httpSessionBindingEvent.getValue();
                sessionMap.remove(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            try {
                String username = (String) httpSessionBindingEvent.getValue();
                userSessionMap.remove(username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
        if(KEY.equals(httpSessionBindingEvent.getName())){
            String key = (String) httpSessionBindingEvent.getValue();
            sessionMap.remove(key);
            sessionMap.put(key, httpSessionBindingEvent.getSession());
        }
        if(KEY_USER_NAME.equals(httpSessionBindingEvent.getName())){
            String username = (String) httpSessionBindingEvent.getValue();
            userSessionMap.remove(username);
            userSessionMap.put(username, httpSessionBindingEvent.getSession());
        }
    }

    public static HttpSession getSession (String key){
        return sessionMap.get(key);
    }

    public static HttpSession getUserSession (String userName){
        return userSessionMap.get(userName);
    }


}
