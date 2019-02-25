package com.liujinlei.sendemailserver.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
public class SendEmail {
    //用于发送邮件
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/sendEmail")
    public String sendEmail(@RequestParam("file") String file) {
        try {
            System.out.println(file);
            System.out.println("测试成功");

            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            File f = new File(file);
            System.out.println(f.getName());
            FileSystemResource file1 = new FileSystemResource(f);
            message.addAttachment(f.getName(), file1);
            message.setFrom("1228493283@qq.com");
            message.setTo("liujinlei_19921214@163.com");
            message.setSubject("测试邮件主题");
            message.setText("测试邮件内容");


            this.mailSender.send(mimeMessage);


            return "sucesss";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}