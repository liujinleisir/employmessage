package com.liujinlei.messageserver.service.impl;

import com.liujinlei.messageserver.controller.SchedualSendEmailService;
import com.liujinlei.messageserver.moudle.EmployMessage;
import com.liujinlei.messageserver.service.EmployMainService;
import com.liujinlei.messageserver.utils.Links;
import com.liujinlei.messageserver.utils.RequestAndResponseTool;
import org.apache.poi.hssf.usermodel.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title:
 * Date: 2019/2/25
 *
 * @author liujinlei
 * @version 1.0
 */
@Service
public class EmployMainServiceImpl implements EmployMainService {
    @Resource
    private SchedualSendEmailService schedualSendEmailService;

    @Override
    public void dealWork() {
        List<EmployMessage> list = new ArrayList<>();
        initCrawlerWithSeeds(new String[]{"https://www.zhipin.com/c101010100-p100101/e_104/?period=1&page=1&ka=page-1"});
        //循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!Links.unVisitedUrlQueueIsEmpty() && Links.getVisitedUrlNum() <= 1000) {
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null) {
                continue;
            }
            Document document = RequestAndResponseTool.sendRequst(visitUrl);
            Elements elements = document.getElementsByClass("job-primary");
            System.out.println(elements);


            for (Element e :
                    elements) {
                EmployMessage employMessage = new EmployMessage();
                employMessage.setJobName(e.getElementsByClass("job-title").text());
                employMessage.setSalary(e.getElementsByClass("red").text());
                Elements e1 = e.getElementsByTag("p");
                for (int j = 0; j < e1.size(); j++) {
                    String aaa[] = e1.get(j).toString().split("<em class=\"vline\"></em>");
                    for (int i = 0; i < aaa.length; i++) {
                        if (aaa[i].startsWith("<p>")) {
                            aaa[i] = aaa[i].substring(aaa[i].indexOf("<p>") + 3, aaa[i].length());
                        }
                        if (aaa[i].endsWith("</p>")) {
                            aaa[i] = aaa[i].substring(0, aaa[i].indexOf("</p>"));

                        }
                        if (j == 0) {
                            switch (i) {
                                case 0:
                                    employMessage.setLocation(aaa[i]);
                                    break;
                                case 1:
                                    employMessage.setWorkRequire(aaa[i]);
                                    break;
                                case 2:
                                    employMessage.setEdu(aaa[i]);
                                    break;
                                default:
                            }
                        } else if (j == 1) {
                            switch (i) {
                                case 0:
                                    employMessage.setBusNature(aaa[i]);
                                    break;
                                case 1:
                                    employMessage.setIsShangShi(aaa[i]);
                                    break;
                                case 2:
                                    employMessage.setGuiMo(aaa[i]);
                                    break;
                                default:
                            }
                        } else {
                            employMessage.setFabuShijian(aaa[i]);
                        }
                    }
                }
                String e2Str = e.getElementsByClass("info-primary").get(0)
                        .getElementsByClass("name").get(0)
                        .getElementsByTag("a").attr("href");
                employMessage.setZhiweiXiangqing(e2Str);
                String e3Str = e.getElementsByClass("info-company").get(0)
                        .getElementsByClass("name").get(0)
                        .getElementsByTag("a").attr("href");
                employMessage.setGongsixiangqing(e3Str);

                String e4Str = e.getElementsByClass("info-company").get(0)
                        .getElementsByClass("name").get(0)
                        .getElementsByTag("a").text();
                employMessage.setComName(e4Str);
                list.add(employMessage);
            }

        }
        try {
            File file = createExcel(list);
            System.out.println(file.getAbsolutePath());
            FileInputStream fileInput = new FileInputStream(file);

            MultipartFile multi = new MockMultipartFile("file", fileInput);


            schedualSendEmailService.sendEmail(multi);
            int o = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功");
    }

    /**
     * 创建人： liujinlei
     * 创建时间：2019/2/25
     * 创建目的：【使用种子初始化 URL 队列】
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++) {
            Links.addUnvisitedUrlQueue(seeds[i]);
        }
    }

    /**
     * 创建人： liujinlei
     * 创建时间：2019/2/25
     * 创建目的：【】
     */
    private File createExcel(List<EmployMessage> list) throws Exception {
        File file = null;
        FileOutputStream fos = null;
        HSSFWorkbook wb = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            //声明一个工作薄：包括构建工作簿、表格、样式
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(date);
            sheet.setDefaultColumnWidth((short) 15);
            // 生成一个样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            sheet.setColumnWidth(0, 2600);
            sheet.setColumnWidth(1, 2600 * 2);
            sheet.setColumnWidth(2, 2600 * 2);
            sheet.setColumnWidth(3, 2600 * 2);
            sheet.setColumnWidth(4, 2600 * 2);
            sheet.setColumnWidth(5, 2600);
            sheet.setColumnWidth(6, 2600);
            sheet.setColumnWidth(7, 2600 * 2);
            sheet.setColumnWidth(8, 2600 * 2);
            sheet.setColumnWidth(9, 2600 * 2);
            sheet.setColumnWidth(10, 2600 * 2);
            sheet.setColumnWidth(11, 2600 * 6);
            sheet.setColumnWidth(12, 2600 * 6);
            HSSFCell cell = null;
            for (int i = 0; i <= list.size(); i++) {
                HSSFRow row = sheet.createRow(i);
                String no = null;
                String jobName;
                String salary;
                String location;
                String workRequire;
                String edu;
                String BusNature;
                String isShangShi;
                String guiMo;
                String fabuShijian;
                String zhiweiXiangqing;
                String gongsixiangqing;
                String ComName;
                if (i == 0) {
                    no = "序号";
                    ComName = "公司名称";
                    jobName = "职位名称";
                    salary = "薪资水平";
                    location = "地址";
                    workRequire = "工作经验要求";
                    edu = "学历要求";
                    BusNature = "公司性质";
                    isShangShi = "上市状态";
                    guiMo = "公司规模";
                    fabuShijian = "发布时间";
                    zhiweiXiangqing = "职位详情";
                    gongsixiangqing = "公司详情";
                } else {
                    EmployMessage entry = list.get(i - 1);
                    no = i + "";
                    ComName = entry.getComName();
                    jobName = entry.getJobName();
                    salary = entry.getSalary();
                    location = entry.getLocation();
                    workRequire = entry.getWorkRequire();
                    edu = entry.getEdu();
                    BusNature = entry.getBusNature();
                    isShangShi = entry.getIsShangShi();
                    guiMo = entry.getGuiMo();
                    fabuShijian = entry.getFabuShijian();
                    zhiweiXiangqing = entry.getZhiweiXiangqing();
                    gongsixiangqing = entry.getGongsixiangqing();
                }

                cell = row.createCell(0);
                cell.setCellValue(no);
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(ComName);
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(jobName);
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(salary);
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(location);
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(workRequire);
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(edu);
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(BusNature);
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(isShangShi);
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(guiMo);
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(fabuShijian);
                cell.setCellStyle(style);

                cell = row.createCell(11);
                cell.setCellValue(zhiweiXiangqing);
                cell.setCellStyle(style);

                cell = row.createCell(12);
                cell.setCellValue(gongsixiangqing);
                cell.setCellStyle(style);
            }
            file = File.createTempFile(date + "_Boss直聘java行业职位清单", ".xls");
            fos = new FileOutputStream(file);
            wb.write(fos);

        } catch (Exception e) {
            throw new Exception("文件生成异常", e);
        } finally {
            if (file != null) {
                file.deleteOnExit();
            }
            if (fos != null) {
                fos.close();
            }
            if (wb != null) {
                wb.close();
            }
        }
        return file;
    }
}
