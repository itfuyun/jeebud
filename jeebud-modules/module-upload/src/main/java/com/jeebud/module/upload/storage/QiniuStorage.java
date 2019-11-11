package com.jeebud.module.upload.storage;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.FileUtils;
import com.jeebud.common.util.StringUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
@Data
public class QiniuStorage implements Storage {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String domain;

    private UploadManager getUploadManager() {
        return new UploadManager(new Configuration());
    }

    @Override
    public String upload(InputStream inputStream, long contentLength, String contentType, String fileName, boolean reName) {
        if (reName) {
            fileName = FileUtils.genDateFileName(fileName);
        }
        Auth auth = Auth.create(accessKey, secretKey);
        UploadManager uploadManager = getUploadManager();
        try {
            String upToken = auth.uploadToken(bucketName);
            uploadManager.put(inputStream, fileName, upToken, null, contentType);
        } catch (QiniuException ex) {
            log.error(ex.getMessage(), ex);
            throw new JeebudException(ex.getMessage());
        }
        return getBaseUrl() + fileName;
    }

    @Override
    public String upload(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new JeebudException("文件流获取失败");
        }
        return upload(inputStream, file.getSize(), file.getContentType(), file.getOriginalFilename(), true);
    }

    @Override
    public String getBaseUrl() {
        if (StringUtils.isEmpty(domain)) {
            return "https://" + endpoint + "/";
        } else {
            return domain + "/";
        }
    }
}
