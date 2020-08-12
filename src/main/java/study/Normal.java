package study;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.BeanFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Normal {

    public static void main(String[] args) {
        JSONObject map = new JSONObject();
        map.put("s1", "s1");
        List<Integer> list = Arrays.asList(1, 2, 3);
        map.put("s2", list);

        JSONArray jsonArray = map.getJSONArray("s2");
        List<Integer> s2 = jsonArray.toJavaList(Integer.class);
        System.out.println(s2);
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
