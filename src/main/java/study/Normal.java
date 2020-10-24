package study;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Normal {

    public static void main(String[] args) {

        JSONArray array = new JSONArray();
        HashMap<String,Object> i = new HashMap<>();
        i.put("type", 1);
        i.put("url", "https://img.ciics.com/1600157523409.png");
        array.add(i);

        String s = "[{\"type\":1,\"url\":\"https://img.ciics.com/1600157523409.png\"}]";
        System.out.println(array.toString());
    }

    public static String getUUID() {
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
