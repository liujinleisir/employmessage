package com.liujinlei.sendemailserver.controler;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SendEmail {
    @Resource
    private JavaMailSender mailSender;

    @PostMapping(value = "/sendEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendEmail(@RequestPart(value = "file") MultipartFile file) {
        try {
            System.out.println(file);
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            message.addAttachment(date + "Boss.xls", new ByteArrayResource(IOUtils.toByteArray(file.getInputStream())));
            message.setFrom("1228493283@qq.com");
            String[] to = {"liujinlei_19921214@163.com"};
            message.setTo(to);
            message.setSubject(date + "Boss直聘java职位");
            message.setText("甭说废话了，看附件吧");


            this.mailSender.send(mimeMessage);

            return "sucesss";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}