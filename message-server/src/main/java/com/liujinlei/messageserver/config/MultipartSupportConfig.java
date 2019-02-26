package com.liujinlei.messageserver.config;

import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.codec.Encoder;


/**
 * @author QiShuo
 * @version 1.0
 * @create 2019/2/26 10:41 AM
 */
@Configuration
public class MultipartSupportConfig {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}
