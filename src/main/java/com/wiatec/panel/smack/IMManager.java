package com.wiatec.panel.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author patrick
 */
public class IMManager {

    private final Logger logger = LoggerFactory.getLogger(IMManager.class);
    private AbstractXMPPConnection connection;


    private IMManager(){
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setCompressionEnabled(false)
                    .setConnectTimeout(3000)
                    .setUsernameAndPassword("admin", "wil123456")
                    .setXmppDomain("ldlegacy.com")
                    .setHost("www.ldlegacy.com")
                    .setPort(5222)
                    .build();
            connection = new XMPPTCPConnection(config);
        } catch (XmppStringprepException e) {
            logger.error("IMManager init error", e);
        }
    }

    private static IMManager instance;

    public static IMManager getInstance(){
        if(instance == null){
            synchronized (IMManager.class){
                if(instance == null){
                    instance = new IMManager();
                }
            }
        }
        return instance;
    }

    public void createAccount(String username){
        if(!connection.isConnected()){
            try {
                connection.connect();
                connection.login();
            } catch (SmackException | IOException | XMPPException | InterruptedException e) {
                logger.error("IMManager connect server error", e);
            }
            return;
        }
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.sensitiveOperationOverInsecureConnection(true);
        try {
            boolean tag = accountManager.supportsAccountCreation();
            if(tag){
                accountManager.createAccount(Localpart.from(username), "wil123456");
            }
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException |
                SmackException.NotConnectedException | InterruptedException | XmppStringprepException e) {
            logger.error("IMManager create account error", e);
        }
    }



}
