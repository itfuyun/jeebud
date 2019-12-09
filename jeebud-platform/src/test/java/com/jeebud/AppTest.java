package com.jeebud;

import static org.junit.Assert.assertTrue;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.junit.Test;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test() throws IOException {
        OSSObject ossObject = getOssObject("myeg8nfvaj4md6i0zlwk.png");
        InputStream in = ossObject.getObjectContent();
        BufferedInputStream bis = new BufferedInputStream(in);
        FileOutputStream fos = new FileOutputStream(new File("D://myeg8nfvaj4md6i0zlwk.png"));
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        byte[] b = new byte[1024];
        int temp;
        while ((temp = bis.read(b))!=-1){
            System.out.println(temp);
            bos.write(b,0,temp);
        }
//        while ((temp = in.read())!=-1){
//            fos.write(temp);
//        }
        bos.close();
    }

    public OSSObject getOssObject(String key) throws IOException {
        OSSObject o = new OSSObject();
        OSS ossClient = getOssClient();
        OSSObject ossObject = ossClient.getObject("jeebud-lite",key);
        o = ossObject;
        ossObject.close();
        return o;
    }

    public OSS getOssClient(){
        return new OSSClientBuilder().build("oss-cn-shenzhen.aliyuncs.com", "LTAIeqRWXuyfMI2h", "H0RB7iLIYYecj9fqtuobn5rmde0J1Q");
    }
}
