package com.jeebud.module.upload.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.FileUtils;
import com.jeebud.common.util.StringUtils;
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
public class AliyunStorage implements Storage {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String domain;

    /**
     * 获取阿里云OSS客户端对象
     *
     * @return OSS
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public String upload(InputStream inputStream, long contentLength, String contentType, String fileName, boolean reName) {
        if (reName) {
            fileName = FileUtils.genDateFileName(fileName);
        }
        OSS ossClient = getOssClient();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            objectMetadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            ossClient.putObject(putObjectRequest);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JeebudException(ex.getMessage());
        } finally {
            ossClient.shutdown();
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
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
            return "https://" + bucketName + "." + endpoint + "/";
        } else {
            return domain + "/";
        }
    }

}
