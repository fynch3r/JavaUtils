package com.sec.Utils.JAR;

import java.io.File;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @program: Utils
 * @description:
 * @author: 0range
 * @create: 2021-03-05 16:37
 **/

/**
 * 寻找Jar包中的指定类
 * java -jar SearchClassInJar.jar <ClassName> <JarDir>
 */

public class SearchClassInJar {
    private String className;
    private String jarDir;
    private Integer totalNum =  0;

    public SearchClassInJar(String className,String jarDir) {
        this.className = className;
        this.jarDir = jarDir;
    }

    //将jar中的类文件路径形式改为包路径形式
    protected String getClassName(ZipEntry entry) {
        return entry.getName().replace('/', '.');
    }

    // 从jar从搜索目标类
    public void searchClass(boolean recurse) {
        searchDir(this.jarDir, recurse);
        System.out.printf("[!] Find %s classes%n",this.totalNum);
    }

    //递归搜索目录和子目录下所有jar和zip文件
    protected void searchDir(String dir, boolean recurse) {
        try {
            File d = new File(dir);
            if (!d.isDirectory()) {
                return;
            }
            File[] files = d.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (recurse && files[i].isDirectory()) {
                    searchDir(files[i].getAbsolutePath(), true);
                } else {
                    String filename = files[i].getAbsolutePath();
                    if (filename.endsWith(".jar")||filename.endsWith(".zip")) {
                        ZipFile zip = new ZipFile(filename);
                        Enumeration entries = zip.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            String thisClassName = getClassName(entry);
                            if (wildcardEquals(this.className.toLowerCase(),thisClassName.toLowerCase()) || wildcardEquals(this.className.toLowerCase() + ".class",thisClassName.toLowerCase())) {
                                String res = String.format("[+] %s | %s",thisClassName,filename);
                                System.out.println(res);
                                totalNum++;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通配符匹配
    private boolean wildcardEquals(String wildcard, String str) {
        String regRule = WildcardToReg(wildcard);
        return Pattern.compile(regRule).matcher(str).matches();
    }

    //将通配符转换为正则表达式
    private static String WildcardToReg(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for(int i=0;i<len;i++){
            if (chars[i] == '*'){
                if (preX){
                    sb.append(".*");
                    preX = false;
                }else if(i+1 == len){
                    sb.append("[^/]*");
                }else{
                    preX = true;
                    continue;
                }
            }else{
                if (preX){
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?'){
                    sb.append('.');
                }else{
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }
}
