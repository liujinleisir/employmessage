package com.liujinlei.sendemailserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SendemailServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendemailServerApplication.class, args);
    }

}
