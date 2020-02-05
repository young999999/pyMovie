package com.spider.util.impl;

import com.spider.entity.Page;
import com.spider.util.download.PageDownUtil;
import com.spider.util.PageGetUtil;

/**
 * @author young
 * @create 2020-01-18 18:18
 *
 * 本类适合网页是utf-8的页面；
 * 下载得到的页面是什么编码，调用本类得到页面就是编码
 */
public class CommonPageGet implements PageGetUtil {
    @Override
    public Page download(String url) {
        Page page = new Page();
        String content= PageDownUtil.getPageContent(url);

        page.setContent(content);

        return page;
    }
}
