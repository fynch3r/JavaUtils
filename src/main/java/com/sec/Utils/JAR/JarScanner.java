package com.sec.Utils.JAR;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @program: Utils
 * @description:
 * @author: 0range
 * @create: 2021-03-05 16:35
 **/


public class JarScanner {
    public static String jarScanner(File src , File desDir) throws IOException {
        JarFile jarFile = new JarFile(src);
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        if(!desDir.exists()) {
            desDir.mkdirs(); //建立用户指定存放的目录
        }
        byte[] bytes = new byte[1024];

        while(jarEntrys.hasMoreElements()){
            ZipEntry entryTemp = jarEntrys.nextElement();
            File desTemp = new File(desDir.getAbsoluteFile() + File.separator + entryTemp.getName());
            //jar条目是空目录
            if(entryTemp.isDirectory()){
                if(!desTemp.exists()){
                    desTemp.mkdirs();
                }

            }else{//jar条目是文件
                //因为manifest的Entry是"META-INF/MANIFEST.MF",写出会报"FileNotFoundException"
                File desTempParent = desTemp.getParentFile();
                BufferedInputStream in = new BufferedInputStream(jarFile.getInputStream(entryTemp));
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desTemp));

                int len = in.read(bytes, 0, bytes.length);
                while(len != -1){
                    out.write(bytes, 0, len);
                    len = in.read(bytes, 0, bytes.length);
                }
                in.close();
                out.flush();
                out.close();
            }
        }
        jarFile.close();
        return "done";
    }
}
