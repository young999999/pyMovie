package com.spider.controller;

import com.spider.entity.Page;
import com.spider.service.ProcessService;
import com.spider.service.impl.Two09MovieListProcessServiceImpl;
import com.spider.service.impl.ZdMovieListProcessServiceImpl;
import com.spider.util.PageGetUtil;
import com.spider.util.impl.CommonPageGet;

import java.util.List;

/**
 * @author young
 * @create 2020-02-06 21:42
 */
public class Two09Controller {
    public static void main(String[] args) {
        {



            PageGetUtil commonPageGet = new CommonPageGet();

            /*酷云资源测试*/
//        String url = "http://www.zuidazy2.com/?m=vod-detail-id-78664.html";
//        String url = "http://www.zuidazy3.com/?m=vod-index-pg-888.html";
//            String url = "http://www.1156zy.net/?m=vod-index-pg-708.html";
        String url = "http://www.1156zy.net/?m=vod-detail-id-38117.html";

            ProcessService movieListProcessService = new Two09MovieListProcessServiceImpl();

            Page page = commonPageGet.download(url);
            System.err.println(movieListProcessService.judgmentPageDownSuccess(page));
//        System.err.println(movieListProcessService.processMovie(page));
//
//            System.err.println(movieListProcessService.processTotlePage(page,0));
//
////        System.err.println(page.getContent());
////
//            List list = movieListProcessService.processMovieList(page);
//            for (Object o : list) {
//                System.err.println(o);
//            }


        }
    }
}
