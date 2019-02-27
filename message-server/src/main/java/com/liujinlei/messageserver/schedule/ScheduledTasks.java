package com.liujinlei.messageserver.schedule;

import com.liujinlei.messageserver.service.EmployMainService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title:
 * Date: 2019/2/27
 *
 * @author liujinlei
 * @version 1.0
 */
@Component
public class ScheduledTasks {
    @Resource
    private EmployMainService employMainService;
    @Scheduled(cron = "0 1 0 * * ? ")
    public void doTask() {
        try{
            employMainService.dealWork(new String[]{"liujinlei_19921214@163.com","446052889@qq.com"});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
