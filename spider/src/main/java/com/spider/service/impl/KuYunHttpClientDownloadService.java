package com.spider.service.impl;

import com.spider.entity.Page;
import com.spider.service.IDownLoadService;
import com.spider.util.KuYunPageDownUtil;
import com.spider.util.PageDownUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author young
 * @create 2020-01-18 18:18
 */
public class KuYunHttpClientDownloadService implements IDownLoadService {
    @Override
    public Page download(String url) {
        Page page = new Page();
        String content= KuYunPageDownUtil.getPageContent(url);
        try {//kuyun
            content=new String(content.getBytes("gb2312"),"gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        page.setContent(content);

        return page;
    }
}
