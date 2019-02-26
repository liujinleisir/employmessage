package com.liujinlei.messageserver.controller;

import com.liujinlei.messageserver.service.EmployMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title:
 * Date: 2019/2/25
 *
 * @author liujinlei
 * @version 1.0
 */
@RestController
public class EmployMainController {
    @Autowired
    private EmployMainService service;
    @RequestMapping("/sendEmail")
    public void dealWork(@RequestParam(value = "email") String email){
        service.dealWork(email);
    }
}
