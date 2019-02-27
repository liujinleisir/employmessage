package com.liujinlei.messageserver.controller;

import com.liujinlei.messageserver.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

/**
 * Title:
 * Date: 2019/2/25
 *
 * @author liujinlei
 * @version 1.0
 */
@FeignClient(value = "sendemail-server", configuration = MultipartSupportConfig.class)
public interface SchedualSendEmailService {
    @RequestMapping(value = "/sendEmail" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String sendEmail(@RequestPart(value = "file") MultipartFile file,@RequestParam(value = "email") String[] email);
}
