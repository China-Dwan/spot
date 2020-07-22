package study;

import java.time.LocalDateTime;

public class Normal {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toLocalDate() + " 至 " + now.toLocalTime());
    }
}
