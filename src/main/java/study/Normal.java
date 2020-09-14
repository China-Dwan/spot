package study;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.BeanFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Normal {

    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            buffer.append(i);
        }
        System.out.println(buffer.toString());
    }

    public static String getUUID(){
        return null;
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
