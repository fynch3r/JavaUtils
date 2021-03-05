package com.sec.Utils.FILE;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @program: Utils
 * @description:
 * @author: 0range
 * @create: 2021-03-05 16:32
 **/


public class FIle2String {
    //File to String
    public static String file2String(String path) throws FileNotFoundException {
        //file->byte[]->String

        try{
            File file = new File(path);
            InputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);

            //设置缓冲区
            byte[] b = new byte[1024];
            int n = 0;

            while((n = fis.read(b))!= -1){
                bos.write(b,0,n);
            }

            byte[] bytecode = bos.toByteArray();

            return new String(bytecode, StandardCharsets.UTF_8);

        }catch(Exception e){
            e.printStackTrace();
        }

        return "ERROR!";

    }
}
