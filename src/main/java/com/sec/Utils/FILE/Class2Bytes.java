package com.sec.Utils.FILE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: Utils
 * @description:
 * @author: 0range
 * @create: 2021-03-05 16:30
 **/


public class Class2Bytes {
    //.class to byte[]
    public static byte[] class2byte(String filePath) {
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while((n = fis.read(b))!= -1){
                bos.write(b,0,n);
            }
            fis.close();
            byte[] classbytes = bos.toByteArray();
            bos.close();

            return classbytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
