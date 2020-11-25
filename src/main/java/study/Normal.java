package study;

import lombok.Data;

import java.io.File;
import java.math.BigDecimal;

public class Normal {

    @Data
    static class N {
        private long start;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) throws Exception {
        BigDecimal zero = BigDecimal.ZERO;
        zero = zero.subtract(BigDecimal.ONE).setScale(2);
        System.out.println(zero);
        System.out.println(System.nanoTime());
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
