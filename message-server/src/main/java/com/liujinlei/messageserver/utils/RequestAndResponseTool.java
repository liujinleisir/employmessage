package com.liujinlei.messageserver.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class RequestAndResponseTool {

    public static Document sendRequst(String url) {
        Connection con  =  Jsoup.connect(url);
        Document d = null;
        try {
             d = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }
}
