package com.liujinlei.messageserver.controller;

import com.liujinlei.messageserver.service.EmployMainService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Title:
 * Date: 2019/2/25
 *
 * @author liujinlei
 * @version 1.0
 */
@RestController
public class EmployMainController {
    @Resource
    private EmployMainService service;
    @RequestMapping("/hi")
    public void dealWork(){
        service.dealWork();
    }
}
