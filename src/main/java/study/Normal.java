package study;

import org.springframework.beans.factory.BeanFactory;

import java.io.File;
import java.time.LocalDateTime;

public class Normal {

    public static void main(String[] args) {
        File file = new File("e:\\apache-maven-3.6.3-bin.zip");
        System.out.println(getDirSize(file));
    }

    public static int getDirSize(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            int size = 0;
            for (File f : children) {
                size += getDirSize(f);
            }
            return size;
        } else {//如果是文件则直接返回其大小,以“kb”为单位
            int size = (int) file.length() / 1024;
            return size;
        }
    }
}
