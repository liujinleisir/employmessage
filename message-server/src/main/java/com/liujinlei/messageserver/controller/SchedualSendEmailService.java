package com.liujinlei.messageserver.controller;

import com.liujinlei.messageserver.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


/**
 * Title:
 * Date: 2019/2/25
 *
 * @author liujinlei
 * @version 1.0
 */
@FeignClient(value = "sendemail-server", configuration = MultipartSupportConfig.class)
public interface SchedualSendEmailService {
    @PostMapping(value = "/sendEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String sendEmail(@RequestPart(value = "file") MultipartFile file);



}
