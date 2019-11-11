package com.jeebud.common.constant;

import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Getter
public enum  StorageEnum {
    /**
     * 阿里云存储
     */
    ALIYUN("aliyun"),
    /**
     * 腾讯云存储
     */
    TENCENT("tencent"),
    /**
     * 本地存储
     */
    LOCAL("local"),
    /**
     * 七牛存储
     */
    QINIU("qiniu");

    StorageEnum(String value){
        this.value = value;
    }
    private String value;
}
