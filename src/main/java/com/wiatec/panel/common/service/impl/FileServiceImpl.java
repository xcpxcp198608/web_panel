package com.wiatec.panel.common.service.impl;

import com.google.common.collect.Lists;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.service.IFileService;
import com.wiatec.panel.common.utils.FtpUtils;
import com.wiatec.panel.common.utils.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @author patrick
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String localPath, String ftpPath){
        if(file == null){
            throw new XException("no file");
        }
        if(TextUtil.isEmpty(localPath)){
            throw new XException("file path no supported");
        }
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString() + "." + extensionName;
        logger.info("==>start upload file: {} to {} , the new file name is {}", fileName, localPath, uploadFileName);
        File file1 = new File(localPath);
        boolean makeDirSuccess = false;
        if(!file1.exists()){
            file1.setWritable(true);
            makeDirSuccess = file1.mkdirs();
        }else{
            makeDirSuccess = true;
        }
        if(!makeDirSuccess){
            throw new XException("upload target file path create fail");
        }
        File uploadFile = new File(file1, uploadFileName);
        try {
            file.transferTo(uploadFile);
            if(!TextUtil.isEmpty(ftpPath)) {
                FtpUtils.upload(Lists.newArrayList(uploadFile), ftpPath);
                uploadFile.delete();
            }
        } catch (IOException e) {
            logger.error("==>file upload error", e);
            return null;
        }
        return uploadFile.getName();
    }

}
