package com.coderzoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @author coderZoe
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ServerMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerMonitorApplication.class, args);
    }
}
