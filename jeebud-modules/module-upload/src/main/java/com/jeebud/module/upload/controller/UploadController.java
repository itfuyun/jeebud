package com.jeebud.module.upload.controller;

import com.jeebud.core.web.RestEntity;
import com.jeebud.module.upload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@RestController
@RequestMapping("${jeebud.sys.serverCtx}")
public class UploadController {

    @Autowired
    StorageService storageService;

    /**
     * 统一上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public RestEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String url = storageService.upload(file);
        return RestEntity.ok().data(url);
    }


}
