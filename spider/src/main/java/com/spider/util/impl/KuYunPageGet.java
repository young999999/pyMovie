package com.spider.util.impl;

import com.spider.entity.Page;
import com.spider.util.PageGetUtil;
import com.spider.util.download.KuYunPageDownUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author young
 * @create 2020-01-18 18:18
 * <p>
 * 由于酷云资源的网页是GB2312编码，所以需要将酷云网页的编码转换为GBK编码
 */
@Service
public class KuYunPageGet implements PageGetUtil {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public Page download(String url) {

        Page page = new Page();
        try {
            TimeUnit.SECONDS.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while ("".equals(page.getContent()) || 200 != page.getStatusCode()) {
            page = KuYunPageDownUtil.getPageContent(url);
            if ("".equals(page.getContent()) || 200 != page.getStatusCode()) {
//                System.err.println(sdf.format(System.currentTimeMillis())+" 下载出错，重新下载");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        String content = page.getContent();


        try {
            /*编码由GB2312转换为GBK*/
//            if(!"".equals(content))
            content = new String(content.getBytes("gb2312"), "gbk");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        page.setContent(content);

        return page;
    }
}
