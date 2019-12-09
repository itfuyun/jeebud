package com.jeebud;

import static org.junit.Assert.assertTrue;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.jeebud.common.util.FileUtils;
import com.jeebud.common.util.ZipUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */


    /**
     * 压缩流
     * @param in
     * @param name
     * @return
     * @throws IOException
     */
    public static byte[] zip(List<InputStream> inList,String name) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        for(InputStream in: inList){
            zipOutputStream.putNextEntry(new ZipEntry(FileUtils.genDateFileName(name)));
            int temp;
            while ((temp= in.read())!=-1){
                zipOutputStream.write(temp);
            }
        }
        zipOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }




}
