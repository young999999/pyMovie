package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.service.ProcessService;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import com.spider.util.PageGetUtil;
import com.spider.util.impl.CommonPageGet;
import com.spider.util.impl.KuYunPageGet;

import java.util.List;

/**
 * @author young
 * @create 2020-02-06 16:55
 */
public class OKController {

    public static void main(String[] args) {
        PageGetUtil commonPageGet = new CommonPageGet();



//        String url = "http://www.okzyw.com/?m=vod-detail-id-48426.html";
        String url = "http://www.okzyw.com/?m=vod-index-pg-938.html";

        ProcessService movieListProcessService = new OkMovieListProcessServiceImpl();

        Page page = commonPageGet.download(url);
        System.err.println(movieListProcessService.judgmentPageDownSuccess(page));
//        Boolean totlePage= movieListProcessService.processTotlePage(page,1);
//        System.err.println(totlePage);
//        System.err.println(movieListProcessService.processMovie(page));
//
////        System.err.println(page.getContent());
////
//        List list = movieListProcessService.processMovieList(page);
//        for (Object o : list) {
//            System.err.println(o);
//        }
    }
}
