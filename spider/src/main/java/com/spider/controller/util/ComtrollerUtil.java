package com.spider.controller.util;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.service.ProcessService;
import com.spider.util.impl.CommonPageGet;
import com.spider.util.log.LogUtil;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author young
 * @create 2020-02-06 16:11
 */
public class ComtrollerUtil {
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    static
    MovieESDao movieESDao;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = "";

    public static void update(CommonPageGet commonPageGet, File file, ProcessService zd,String baseurl,int startIndex){
         {
             int movieTotle = searchTotle(movieESDao);
            String url = baseurl + "/?m=vod-index-pg-" + startIndex + ".html"; //zd
//            String url = baseurl + "/?m=vod-index-pg-" + startIndex + ".html"; //ok
//            String url = baseurl + "/list/?0-" + startIndex + ".html";  //kuyun

            /*循环下载页面的次数*/
            int downPageNum = 0;
            Page movieListPage;
            /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
            while (true) {
                downPageNum++;
                movieListPage = commonPageGet.download(url);

                if (zd.judgmentPageDownSuccess(movieListPage)) {
                    if (downPageNum > 1) {
//                        System.out.println("更新：下载电影集合页面成功 " + downPageNum + "--------------------------------");
                        LogUtil.fileWriter(file, "更新：下载电影集合页面成功 " + downPageNum + "--------------------------------");
                    }
                    downPageNum = 0;
                    break;
                }
                if (downPageNum == 1) {
//                    System.err.println("更新：下载电影集合页面失败 " + downPageNum + " " + url);
                    LogUtil.fileWriter(file, "更新：下载电影集合页面失败 " + downPageNum + " " + url);

                }
            }

            /*增加一个方法判断body/div[5]/ul[2]/li/span[2]/a这个xpath路径(每页第一步电影名称)是否存在，存在继续解析，不存在则跳出循环*/
            if (zd.processTotlePage(movieListPage,startIndex)) {
                System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：第" + startIndex + "页没有有电影");

                break;
            }
            List<String> movieList = zd.processMovieList(movieListPage);

            for (String movieUrl : movieList) {
                movieUrl = baseurl + movieUrl;
                Page moviePage;
                /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
                while (true) {
                    downPageNum++;
                    moviePage = commonPageGet.download(movieUrl);
                    if (zd.judgmentPageDownSuccess(moviePage)) {
                        if (downPageNum > 1) {
//                            System.out.println("更新：下载电影信息页面成功 " + downPageNum + "-----------------------------------------");
                            LogUtil.fileWriter(file, "更新：下载电影信息页面成功 " + downPageNum + "-----------------------------------------");

                        }
                        downPageNum = 0;
                        break;
                    }
                    if (downPageNum == 1) {
//                        System.err.println("更新：下载电影信息页面失败 " + downPageNum + " " + movieUrl);
                        LogUtil.fileWriter(file, "更新：下载电影信息页面失败 " + downPageNum + " " + movieUrl);

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
                    movie = zd.processMovie(moviePage);

                    endTime = System.currentTimeMillis();
                    timeDifference = endTime - startTime;

                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 0) {
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：未爬取到该电影信息：" + movieUrl);
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：未爬取到该电影信息：" + movieUrl);

                        movieCrawlingFlag = 1;
                    }
                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                        continue;
                    }
                    if (!"".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：爬取(多次)到该电影信息：" + movie);
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：爬取(多次)到该电影信息：" + movie);

                    }

                }
                if (timeDifference > 20000) {
//                    System.err.println("更新：爬取该电影信息超时" + movieUrl);
                    LogUtil.fileWriter(file, "更新：爬取该电影信息超时" + movieUrl);


                    continue;
                }

                int length1 = 0;
                if (movie.getMc().getZdcollectionm3u8() != null) {
                    length1 = movie.getMc().getZdcollectionm3u8().split(",").length;
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

                /*ES中不存在该电影，直接向MySql和Elasticsearch中添加该电影*/
                if ("".equals(byMovieNameLike.getMovieName()) &&
                        "".equals(byMovieNameLike.getMc().getZdcollectionm3u8())) {

                    movieTotle++;
                    movie.setId(movieTotle);
                    movie.setMovieId(movieTotle);


                    movieESDao.save(movie);

//                    System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());
                    LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

                }
                /*ES中存在该电影*/
                else {

                    length = byMovieNameLike.getMc().getZdcollectionm3u8().split(",").length;
//                    length = byMovieNameLike.getMovieCollection().split(",").length;

                    /*该电影是否与ES中存在的该电影剧集长度不相同*/
                    if (length1 != length) {

                        movie.setId(byMovieNameLike.getMovieId());
                        movie.setMovieId(byMovieNameLike.getMovieId());

                        movieESDao.save(movie);


//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：更新电影（剧集改变）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：更新电影（剧集改变）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());

                    } else {
//                        System.out.println(sdf.format(System.currentTimeMillis()) + ":更新：该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                        LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());

                    }
                }

            }
//            System.out.println(sdf.format(System.currentTimeMillis()) + ":更新：第" + startIndex + "页完成");
            LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新：第" + startIndex + "页完成");

            startIndex++;
        }
    }


    public static int searchTotle(MovieESDao movieESDao) {//因为本方法没有@GetMapping注解，而this.movieESDao是通过自动注入的，
        // 直接通过this.movieESDao调用findByIdGreaterThan方法会报空指针异常
        int size = movieESDao.findByIdGreaterThan(0).size();
        return size;
    }
}
