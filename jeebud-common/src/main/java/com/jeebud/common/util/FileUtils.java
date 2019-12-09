package com.jeebud.common.util;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * 生成带时间的文件路径
     *
     * @param originalFilename
     * @return
     */
    public static String genDateFileName(String originalFilename) {
        String suffix = getFileSuffix(originalFilename);
        String key = CharUtils.getRandomString(20) + "." + suffix;
        return DateUtils.formatDate(new Date(), "yyyyMMdd") + "/" + key;
    }

    /**
     * 获取文件后缀
     *
     * @param originalFilename
     * @return
     */
    public static String getFileSuffix(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index + 1);
        return suffix;
    }

}
