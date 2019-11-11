package com.jeebud.module.upload.storage;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.FileUtils;
import com.jeebud.common.util.StringUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
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
public class TencentStorage implements Storage {

    private String secretId;
    private String secretKey;
    private String region;
    private String bucketName;
    private String domain;


    private COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        return new COSClient(cred, clientConfig);
    }

    @Override
    public String upload(InputStream inputStream, long contentLength, String contentType, String fileName, boolean reName) {
        if (reName) {
            fileName = FileUtils.genDateFileName(fileName);
        }
        COSClient cosClient = getCosClient();
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(contentLength);
            objectMetadata.setContentType(contentType);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            cosClient.putObject(putObjectRequest);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new JeebudException(ex.getMessage());
        } finally {
            cosClient.shutdown();
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
            return "https://" + bucketName + ".cos." + region + ".myqcloud.com/";
        } else {
            return domain + "/";
        }
    }
}
