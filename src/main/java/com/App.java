package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableAsync
@EnableCaching
public class App {

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(App.class,args);
    }
}
