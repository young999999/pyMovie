package com.spider.controller;

import com.spider.controller.util.SaveControllerUtil;
import com.spider.controller.util.UpdateControllerutil;
import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.service.ProcessService;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import com.spider.util.impl.CommonPageGet;
import com.spider.util.log.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author young
 * @create 2020-02-06 16:55
 */
@Data
@RestController
@Api(tags = "ok资源", description = "有关www.okzyw.com网站的爬取")
@CrossOrigin
public class OKController {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieESDao movieESDao;

    @Autowired
    CommonPageGet commonPageGet;

    @Autowired
    OkMovieListProcessServiceImpl zd;


    String baseurl = "http://www.okzyw.com";

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //从第一页到pageIndex页更新
    @ApiOperation(value = "Ok资源更新", notes = "Ok最大资源，startIndex起始页，endIndex终止页（不包括）")
    @GetMapping("ok/update/{startIndex}/{endIndex}")
    public String updateMovie(@PathVariable(value = "startIndex") int startIndex, @PathVariable(value = "endIndex") int endIndex) {
        File file = LogUtil.creatFile("./ok资源更新日志.txt");
        CommonPageGet commonPageGet = new CommonPageGet();
        while (startIndex < endIndex) {
            startIndex = update(commonPageGet, file, zd, baseurl, startIndex, movieESDao);
        }

        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：退出更新");
        return sdf.format(System.currentTimeMillis()) + ":退出更新";
    }


    //从第一页到最后一页爬取
    @GetMapping("ok/climb/{pageIndex}")
    public void climbMovie(@PathVariable(value = "pageIndex") int pageIndex) {
        File file = LogUtil.creatFile("./ok资源爬取日志.txt");
        /*起始页数*/
        int index = pageIndex;
        while (true) {
            index = update(commonPageGet, file, zd, baseurl, index, movieESDao);
            if (index == Integer.MAX_VALUE) {
                LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":爬取：退出爬取 ");
                break;
            }
        }
    }

    public static int update(CommonPageGet commonPageGet, File file, ProcessService processService, String baseurl, int startIndex, MovieESDao movieESDao) {
        {


            String url = baseurl + "/?m=vod-index-pg-" + startIndex + ".html"; //ok


            /*循环下载页面的次数*/
            int downPageNum = 0;
            Page movieListPage;
            /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
            while (true) {
                downPageNum++;
                movieListPage = commonPageGet.download(url);

                if (processService.judgmentPageDownSuccess(movieListPage)) {
                    if (downPageNum > 1) {
//                        System.out.println("下载电影集合页面成功 " + downPageNum + "--------------------------------");
                        LogUtil.fileWriter(file, "下载电影集合页面成功 " + downPageNum + "--------------------------------");
                    }
                    downPageNum = 0;
                    break;
                }
                if (downPageNum == 1) {
//                    System.err.println("下载电影集合页面失败 " + downPageNum + " " + url);
                    LogUtil.fileWriter(file, "下载电影集合页面失败 " + downPageNum + " " + url);

                }
            }

            /*增加一个方法判断body/div[5]/ul[2]/li/span[2]/a这个xpath路径(每页第一步电影名称)是否存在，存在继续解析，不存在则跳出循环*/
            if (!processService.processTotlePage(movieListPage, startIndex)) {
                System.err.println(sdf.format(System.currentTimeMillis()) + "第" + startIndex + "页没有有电影");

                return Integer.MAX_VALUE;
            }
            List<String> movieList = processService.processMovieList(movieListPage);

            for (String movieUrl : movieList) {
                movieUrl = baseurl + movieUrl;
                Page moviePage;
                /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
                while (true) {
                    downPageNum++;
                    moviePage = commonPageGet.download(movieUrl);
                    if (processService.judgmentPageDownSuccess(moviePage)) {
                        if (downPageNum > 1) {
//                            System.out.println("下载电影信息页面成功 " + downPageNum + "-----------------------------------------");
                            LogUtil.fileWriter(file, "下载电影信息页面成功 " + downPageNum + "-----------------------------------------");

                        }
                        downPageNum = 0;
                        break;
                    }
                    if (downPageNum == 1) {
//                        System.err.println("下载电影信息页面失败 " + downPageNum + " " + movieUrl);
                        LogUtil.fileWriter(file, "下载电影信息页面失败 " + downPageNum + " " + movieUrl);

                    }
                }
                Movie movie = new Movie();
                /*movieCrawlingFlag = 0：代表第一次抓取；movieCrawlingFlag = 1：代表第一次以上抓取*/

                int movieCrawlingFlag = 0;
                /*当电影名字为空时，循环继续爬取该电影信息，避免未爬取到该电影，循环时间不超过120秒*/
                /*循环开始时间*/
                long startTime = System.currentTimeMillis();
                /*循环结束时间*/
                long endTime = System.currentTimeMillis();
                /*时间差*/
                long timeDifference = endTime - startTime;
                while ("".equals(movie.getMovieName()) && timeDifference <= 20000) {


                    /*爬取到的电影*/
                    movie = processService.processMovie(moviePage);

                    endTime = System.currentTimeMillis();
                    timeDifference = endTime - startTime;

                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 0) {
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":未爬取到该电影信息：" + movieUrl);
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":未爬取到该电影信息：" + movieUrl);

                        movieCrawlingFlag = 1;
                    }
                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                        continue;
                    }
                    if (!"".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取(多次)到该电影信息：" + movie);
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":爬取(多次)到该电影信息：" + movie);

                    }

                }
                if (timeDifference > 20000) {
//                    System.err.println("爬取该电影信息超时" + movieUrl);
                    LogUtil.fileWriter(file, "爬取该电影信息超时" + movieUrl);


                    continue;
                }

                int length1 = 0;
                if (movie.getMc().getOkcollectionm3u8() != null) {
                    length1 = movie.getMc().getOkcollectionm3u8().split(",").length;
                }


                int length = 0;
                /*根据爬取到的电影名字查询ES，名字转义 /  ~ ！? [ * ^等特殊字符*/
                String name = QueryParser.escape(movie.getMovieName());

                String[] names = name.split(" ");
                name = "";
                for (int i = 0; i < name.length(); i++) {
                    if (!"".equals(names[i])) {
                        name += names[i];
                    }
                }


                List<Movie> byMovieNameLikeList = movieESDao.findByMovieNameLike(name);

                //保存按影视名从ES中查询的数据
                Movie byMovieNameLike = new Movie();
                for (Movie movie1 : byMovieNameLikeList) {
                    if (movie.getMovieName().equals(movie1.getMovieName())) {
                        byMovieNameLike = movie1;
                        break;
                    }
                }
                length = byMovieNameLike.getMc().getOkcollectionm3u8().split(",").length;
                /*ES中不存在该电影，直接向MySql和Elasticsearch中添加该电影*/
                if ("".equals(byMovieNameLike.getMovieName()) && "".equals(byMovieNameLike.getMc().getOkcollectionm3u8())) {
                    SaveControllerUtil saveControllerUtil =new SaveControllerUtil(movieESDao,movie,file);
                    saveControllerUtil.setName("209");
                    saveControllerUtil.start();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.err.println(sdf.format(System.currentTimeMillis()) + ":添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

                }
                /*ES中存在该电影*/
                else if (!"".equals(byMovieNameLike.getMovieName())){


                    /*该电影是否与ES中存在的该电影剧集长度不相同*/

                    if (length1 != length||"".equals(byMovieNameLike.getMc().getOkcollectionm3u8())) {

                        UpdateControllerutil controllerutil=new UpdateControllerutil(movieESDao,movie,file,name,"ok");
                        controllerutil.setName("ok");
                        controllerutil.start();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新电影（剧集改变）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());

                    } else {
//                        System.out.println(sdf.format(System.currentTimeMillis()) + ":该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());

                    }
                }

            }
//            System.out.println(sdf.format(System.currentTimeMillis()) + ":第" + startIndex + "页完成");
            LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":第" + startIndex + "页完成");

            startIndex++;
            return startIndex;
        }
    }


}
