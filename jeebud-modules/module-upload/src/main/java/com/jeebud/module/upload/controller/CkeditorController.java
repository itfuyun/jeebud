package com.jeebud.module.upload.controller;

import com.jeebud.module.upload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author itfuyun(itfuyun @ gmail.com)
 */
@RestController
@RequestMapping("${jeebud.sys.serverCtx}/ckeditor")
public class CkeditorController {
    @Autowired
    StorageService storageService;

    /**
     * 文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Object ckeditorUpload(@RequestParam("upload") MultipartFile file) {
        try {
            String url = storageService.upload(file);
            return success(file.getOriginalFilename(), url);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 成功
     *
     * @param fileName
     * @param url
     * @return
     */
    private Map<String, Object> success(String fileName, String url) {
        Map<String, Object> rst = new HashMap<>(4);
        rst.put("uploaded", 1);
        rst.put("fileName", fileName);
        rst.put("url", url);
        return rst;
    }

    /**
     * 失败
     *
     * @param msg
     * @return
     */
    private Map<String, Object> error(String msg) {
        Map<String, Object> rst = new HashMap<>(4);
        Map<String, Object> message = new HashMap<>(4);
        message.put("message", msg);
        rst.put("uploaded", 0);
        rst.put("error", message);
        return rst;
    }
}
