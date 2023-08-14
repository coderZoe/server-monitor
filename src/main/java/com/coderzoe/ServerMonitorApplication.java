package com.coderzoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author coderZoe
 * @date 2023/8/13 19:20
 */
@SpringBootApplication
@EnableScheduling
public class ServerMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerMonitorApplication.class, args);
    }
}
