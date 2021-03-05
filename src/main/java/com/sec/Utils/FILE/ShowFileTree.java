package com.sec.Utils.FILE;

import java.io.File;

/**
 * @program: Utils
 * @description:
 * @author: 0range
 * @create: 2021-03-05 16:33
 **/


public class ShowFileTree {
    //show file tree
    public static void showFileTree(String path,int depth){
        File fileDir = new File(path);

        if(depth == 1){
            System.out.println("- " + fileDir.getName());
        }

        if (fileDir.exists()) {
            File[] listFiles = fileDir.listFiles();
            if(listFiles!= null) {
                for (File file : listFiles) {
                    if (file != null) {
                        if (file.isDirectory()) {
                            System.out.println(new String(new char[depth - 1]).replace('\0', ' ') + " - " + file.getName());
                            depth++;
                            showFileTree(file.getAbsolutePath(), depth);
                            depth--;
                        } else {
                            System.out.println(new String(new char[depth - 1]).replace('\0', ' ') + " - " + file.getName());
                        }
                    }
                }
            }
        }
    }
}
