package com.jeebud.module.upload.storage;

import com.jeebud.common.exception.JeebudException;
import com.jeebud.common.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Slf4j
public class LocalStorage implements Storage {

    private String storagePath;
    private String domain;
    private Path rootLocation;

    @Override
    public String upload(InputStream inputStream, long contentLength, String contentType, String fileName, boolean reName) {
        if (reName) {
            fileName = FileUtils.genDateFileName(fileName);
        }
        try {
            Files.copy(inputStream, rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new JeebudException("文件上传失败");
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getBaseUrl() + fileName;
    }

    @Override
    public String upload(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new JeebudException("文件流获取失败");
        }
        return upload(inputStream, file.getSize(), file.getContentType(), file.getOriginalFilename(), true);
    }

    @Override
    public String getBaseUrl() {
        return domain + "/";
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        setRootLocation(Paths.get(storagePath));
        try {
            Files.createDirectories(getRootLocation());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(Path rootLocation) {
        this.rootLocation = rootLocation;
    }
}
