package com.wiatec.panel.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author patrick
 */
@Slf4j
@Component
public class FtpMaster {

    private static final String ADDRESS = "resource.ldlegacy.com";
    private static final int PORT = 21;
    private static final String USER = "wiatec";
    private static final String PWD = "wil12345678";
    private FTPClient ftpClient;

    public boolean upload(List<File> fileList, String remotePath){
        log.info("==>> ftp server start connecting: {}", ADDRESS);
        boolean result = upload(remotePath, fileList);
        if(result) {
            log.info("==>> ftp file upload to {} successful", ADDRESS);
        }else{
            log.error("==>> ftp file upload to {} failure", ADDRESS);
        }
        return result;
    }



    private boolean connectServer(){
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ADDRESS, PORT);
            ftpClient.login(USER.trim(), PWD.trim());
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            log.error("==>> ftp server connect error", e);
            return false;
        }
    }

    private boolean upload(String remotePath, List<File> fileList){
        boolean uploaded = false;
        FileInputStream fileInputStream = null;
        if(!connectServer()){
            log.error("==>> ftp server connect error");
            return uploaded;
        }
        try {
            if(!StringUtils.isEmpty(remotePath)) {
                String[] paths = remotePath.split("/");
                for(int i = 0; i < paths.length; i ++ ) {
                    ftpClient.makeDirectory(paths[i]);
                    ftpClient.changeWorkingDirectory(paths[i]);
                }
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
            log.error("==>> ftp file upload error", e);
        }finally {
            try {
                if(fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("==>> ftp close error", e);
            }
        }

        return uploaded;
    }

}
