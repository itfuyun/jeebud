package com.jeebud.module.upload.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public interface Storage {
    /**
     * 上传
     *
     * @param inputStream 文件流
     * @param contentLength 文件大小
     * @param contentType 文件类型
     * @param fileName 文件名
     * @param reName 是否重命名
     * @return 文件地址
     */
    String upload(InputStream inputStream, long contentLength, String contentType, String fileName, boolean reName);

    /**
     * 上传
     *
     * @param file
     * @return 文件地址
     */
    String upload(MultipartFile file);

    /**
     * 获取基础路径
     *
     * @return
     */
    String getBaseUrl();
}
