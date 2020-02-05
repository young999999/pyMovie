package com.spider.util.impl;

import com.spider.entity.Page;
import com.spider.util.PageGetUtil;
import com.spider.util.download.KuYunPageDownUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author young
 * @create 2020-01-18 18:18
 *
 * 由于酷云资源的网页是GB2312编码，所以需要将酷云网页的编码转换为GBK编码
 */
public class KuYunPageGet implements PageGetUtil {
    @Override
    public Page download(String url) {
        Page page = new Page();
        String content= KuYunPageDownUtil.getPageContent(url);


        try {
            /*编码由GB2312转换为GBK*/
            content=new String(content.getBytes("gb2312"),"gbk");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        page.setContent(content);

        return page;
    }
}
