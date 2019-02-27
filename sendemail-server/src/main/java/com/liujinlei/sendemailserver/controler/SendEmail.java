package com.liujinlei.sendemailserver.controler;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SendEmail {
    //用于发送邮件
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/sendEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendEmail(@RequestPart(value = "file") MultipartFile file,@RequestParam(value = "email") String[] email) {
        try {
            System.out.println(email+"   成功!!!!!!!!!!!!!!!!!!");
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //File f = new File(file);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
//            System.out.println(f.getName());
//            FileSystemResource file1 = new FileSystemResource(f);
//            message.addAttachment(date+"Boss.xls", file1);
            message.addAttachment(date+"Boss.xls", new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));
            message.setFrom("1228493283@qq.com");
            String[] to = {"liujinlei_19921214@163.com","qi_s@qq.com"};
            message.setTo(email);
            message.setSubject(date+"Boss直聘java职位");
            message.setText("甭说废话了，看附件吧");


            this.mailSender.send(mimeMessage);


            return "sucesss";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}