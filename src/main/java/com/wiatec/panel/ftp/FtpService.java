package com.wiatec.panel.ftp;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick
 */
@Slf4j
@Service
public class FtpService {

    @Resource private FtpMaster ftpMaster;

    public FileInfo uploadFile(HttpServletRequest request, MultipartFile file, String remotePath) {
        try {
            FileInfo fileInfo = new FileInfo();
            if (file == null) {
                throw new XException(EnumResult.ERROR_BAD_REQUEST);
            }
            String path = request.getServletContext().getRealPath("/upload");
            String[] ms = file.getOriginalFilename().split("\\.");
            String fileName = System.currentTimeMillis() + "." + ms[ms.length - 1];
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdir();
            }
            File localFile = new File(file1, fileName);
            FileUtils.writeByteArrayToFile(localFile, file.getBytes());
            Runtime.getRuntime().exec("chmod -R 777 " + path);
            List<File> files = new ArrayList<>();
            files.add(localFile);
            boolean upload = ftpMaster.upload(files, remotePath);
            if (!upload) {
                throw new XException("upload failure");
            }
            fileInfo.setName(fileName);
            fileInfo.setPath(path + fileName);
            fileInfo.setDirPath(path);
            return fileInfo;
        }catch (Exception e){
            log.error("ftp upload failure", e);
            throw new XException("upload failure #");
        }
    }
}
