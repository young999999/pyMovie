package com.spider.util.impl;

import com.spider.entity.Page;
import com.spider.util.download.KuYunPageDownUtil;
import com.spider.util.download.PageDownUtil;
import com.spider.util.PageGetUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author young
 * @create 2020-01-18 18:18
 *
 * 本类适合网页是utf-8的页面；
 * 下载得到的页面是什么编码，调用本类得到页面就是编码
 */
@Service
public class CommonPageGet implements PageGetUtil {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public Page download(String url) {
        Page page=PageDownUtil.getPageContent(url);
        while ("".equals(page.getContent()) || 200 != page.getStatusCode()) {
            page = PageDownUtil.getPageContent(url);
            if ("".equals(page.getContent()) || 200 != page.getStatusCode()) {
                System.err.println(sdf.format(System.currentTimeMillis())+" 下载出错，重新下载");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return page;
    }
}
