package com.wiatec.panel.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author patrick
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ADDRESS = PropertiesUtils.get("ftp.address");
    private static String USER = PropertiesUtils.get("ftp.user");
    private static String PWD = PropertiesUtils.get("ftp.pwd");

    private String address;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    private FTPUtil(String address, int port, String user, String pwd) {
        this.address = address;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean upload(List<File> fileList, String remotePath){
        FTPUtil ftpUtil = new FTPUtil(ADDRESS, 21, USER, PWD);
        logger.info("ftp server start connecting");
        boolean result = ftpUtil.upload(remotePath, fileList);
        logger.info("ftp file upload successfully");
        return result;
    }

    private boolean upload(String remotePath, List<File> fileList){
        boolean uploaded = true;
        FileInputStream fileInputStream = null;
        if(connectServer(this.address, this.port, this.user, this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("utf-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for(File file: fileList){
                    fileInputStream = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(), fileInputStream);
                }
            } catch (IOException e) {
                logger.error("ftp file upload error", e);
                uploaded = false;
            }finally {
                try {
                    if(fileInputStream != null) {
                        fileInputStream.close();
                    }
                    ftpClient.disconnect();
                } catch (IOException e) {
                    logger.error("ftp close error", e);
                }
            }

        }
        return uploaded;
    }

    private boolean connectServer(String address, int port, String user, String pwd){
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(address, port);
            return ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("ftp server connect error", e);
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
