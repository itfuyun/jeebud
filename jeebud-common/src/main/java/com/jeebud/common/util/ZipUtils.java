package com.jeebud.common.util;

import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class ZipUtils {

    /**
     * 批量压缩流
     *
     * @param zipFiles
     * @return
     * @throws IOException
     */
    public static byte[] batchZip(List<ZipFile> zipFiles) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        for(ZipFile zipFile: zipFiles){
            InputStream in = zipFile.getInputStream();
            zipOutputStream.putNextEntry(new ZipEntry(zipFile.getName()));
            int temp;
            while ((temp= in.read())!=-1){
                zipOutputStream.write(temp);
            }
        }
        zipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

}
