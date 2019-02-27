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
    public String  dealWork(@RequestParam(value = "email") String email){
        try{
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            boolean isEmail =  email.matches(regex);
            if(isEmail){
                service.dealWork(new String[]{email});
            }else{
                return "失败 : 邮箱格式不正确";
            }
        }catch(Exception e){
            return "失败"+e;
        }
        return "成功";
    }
}
