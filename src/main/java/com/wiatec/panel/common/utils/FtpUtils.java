package com.wiatec.panel.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author patrick
 */
@Component
public class FtpUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    private static String ADDRESS = PropertiesUtils.get("ftp.address");
    private static String USER = PropertiesUtils.get("ftp.user");
    private static String PWD = PropertiesUtils.get("ftp.pwd");

    private String address;
    private int port;
    private String user;
    private String pwd;

    private FTPClient ftpClient;

    private FtpUtils(){

    }

    private FtpUtils(String address, int port, String user, String pwd) {
        this.address = address;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean upload(List<File> fileList, String remotePath){
        FtpUtils ftpUtils = new FtpUtils(ADDRESS, 21, USER, PWD);
        logger.info("=====>> ftp server start connecting: {}", ADDRESS);
        boolean result = ftpUtils.upload(remotePath, fileList);
        if(result) {
            logger.info("=====>> ftp file upload to {} successful", ADDRESS);
        }else{
            logger.error("=====>> ftp file upload to {} failure", ADDRESS);
        }
        return result;
    }

    private boolean upload(String remotePath, List<File> fileList){
        boolean uploaded = false;
        FileInputStream fileInputStream = null;
        if(!connectServer(this.address, this.port, this.user, this.pwd)){
            logger.error("=====>> ftp server connect error");
            return uploaded;
        }
        try {
            if(!TextUtil.isEmpty(remotePath)) {
                ftpClient.changeWorkingDirectory(remotePath);
            }
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("utf-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            for(File file: fileList){
                fileInputStream = new FileInputStream(file);
                ftpClient.storeFile(file.getName(), fileInputStream);
            }
            uploaded = true;
        } catch (IOException e) {
            logger.error("=====>> ftp file upload error", e);
        }finally {
            try {
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("=====>> ftp close error", e);
            }
        }

        return uploaded;
    }

    private boolean connectServer(String address, int port, String user, String pwd){
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(address.trim(), port);
            ftpClient.login(user.trim(), pwd.trim());
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            logger.error("=====>> ftp server connect error", e);
            return false;
        }
    }





    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
