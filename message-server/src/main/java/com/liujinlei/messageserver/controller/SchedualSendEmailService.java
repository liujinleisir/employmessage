package com.liujinlei.messageserver.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@FeignClient(value = "sendemail-server")
public interface SchedualSendEmailService {
    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestBody FileInputStream input);
}
