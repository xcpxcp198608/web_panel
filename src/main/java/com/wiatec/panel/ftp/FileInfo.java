package com.wiatec.panel.ftp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author patrick
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInfo {

    private String name;
    private String path;
    private String dirPath;
    private String md5;
}
