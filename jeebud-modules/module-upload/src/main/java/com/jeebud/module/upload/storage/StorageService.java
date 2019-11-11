package com.jeebud.module.upload.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class StorageService {

    private Storage storage;

    public String upload(MultipartFile file) {
        return storage.upload(file);
    }


    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
