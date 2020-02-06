package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.service.impl.ZdMovieListProcessServiceImpl;
import com.spider.util.PageGetUtil;
import com.spider.service.ProcessService;
import com.spider.util.impl.CommonPageGet;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import com.spider.util.impl.KuYunPageGet;
import lombok.Data;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author young
 * @create 2020-01-18 18:33
 * @blame Android Team
 */
@Data
@RestController
public class KuYunController {

    public static void main(String[] args) {
        PageGetUtil commonPageGet = new KuYunPageGet();

        /*酷云资源测试*/

//        String url = "http://www.kuyunzy1.com/detail/?41012.html";
        String url = "http://www.kuyunzy1.com/list/?0-793.html";

        ProcessService movieListProcessService = new KuYunMovieListProcessServiceImpl();

        Page page = commonPageGet.download(url);
        System.err.println(movieListProcessService.judgmentPageDownSuccess(page));
////        System.err.println(movieListProcessService.processTotlePage(page));
//        Movie movie = movieListProcessService.processMovie(page);
//        System.err.println(movie);
//
//
//        List list = movieListProcessService.processMovieList(page);
//        for (Object o : list) {
//            System.err.println(o);
//        }
    }
}
