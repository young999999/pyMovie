package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.service.IDownLoadService;
import com.spider.service.IProcessService;
import com.spider.service.impl.HttpClientDownloadService;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import com.spider.service.impl.zdMovieListProcessServiceImpl;
import lombok.Data;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author young
 * @create 2020-01-18 18:33
 * @blame Android Team
 */
@Data
@RestController
public class StartDSJcount {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieESDao movieESDao;

    private IDownLoadService downLoadService;
    private IProcessService processService;
    private zdMovieListProcessServiceImpl judgmentMovieListPageService =new zdMovieListProcessServiceImpl();
    private zdMovieListProcessServiceImpl judgmentPageDownSuccess = new zdMovieListProcessServiceImpl();
    private IProcessService processMovie;
    private String baseurl = "http://www.zuidazy5.com";
//    private String baseurl = "http://www.kuyunzy1.com";
    //    private String baseurl = "http://www.okzyw.com";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = "";

    //从第一页到pageIndex页更新
    @GetMapping("update/{startIndex}/{endIndex}")
    public String updateMovie(@PathVariable(value = "startIndex") int startIndex, @PathVariable(value = "endIndex") int endIndex) {
        StartDSJcount dsj = new StartDSJcount();

        dsj.setDownLoadService(new HttpClientDownloadService());
        dsj.setProcessService(new zdMovieListProcessServiceImpl());
        dsj.setProcessMovie(new zdMovieListProcessServiceImpl());


        /*当前ES中文档总数*/
        int movieTotle = dsj.searchTotle(movieESDao);

        /*此处之后放进一个while（true）的循环内，将页数以步长1依次增加*/
        while (startIndex < endIndex) {

            String url = baseurl + "/?m=vod-index-pg-" + startIndex + ".html"; //zd
//            String url = baseurl + "/?m=vod-index-pg-" + startIndex + ".html"; //ok
//            String url = baseurl + "/list/?0-" + startIndex + ".html";  //kuyun

//            Page movieListPage = dsj.downloadMovieListPage(url);//得到某页电影集合页面
            /*循环下载的次数*/
            int downPageNum = 0;
            Page movieListPage;
            /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
            while (true) {
                downPageNum++;
                movieListPage = dsj.downloadMovieListPage(url);

                if (dsj.judgmentPageDownSuccess(movieListPage)) {
                    if(downPageNum>1) {
                        System.out.println("更新：下载电影集合页面成功 " + downPageNum + "--------------------------------");
                    }
                    downPageNum = 0;
                    break;
                }
                if(downPageNum==1) {
                    System.err.println("更新：下载电影集合页面失败 " + downPageNum + " " + url);
                }
            }


            List<String> movieList = dsj.processMovieList(movieListPage);

            for (String movieUrl : movieList) {
                movieUrl = baseurl + movieUrl;
                Page moviePage;
                /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
                while (true) {
                    downPageNum++;
                    moviePage = dsj.downloadMovieListPage(movieUrl);
                    if (dsj.judgmentPageDownSuccess(moviePage)) {
                        if(downPageNum>1) {
                            System.out.println("更新：下载电影信息页面成功 " + downPageNum + "-----------------------------------------");
                        }
                        downPageNum = 0;
                        break;
                    }
                    if(downPageNum==1) {
                        System.err.println("更新：下载电影信息页面失败 " + downPageNum + " " + movieUrl);
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
                while ("".equals(movie.getMovieName()) && timeDifference <= 5000) {


                    /*爬取到的电影*/
                    movie = dsj.processMovie(moviePage);

                    endTime = System.currentTimeMillis();
                    timeDifference = endTime - startTime;

                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 0) {
                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：未爬取到该电影信息：" + movieUrl);
                        movieCrawlingFlag = 1;
                    }
                    if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                        continue;
                    }
                    if (!"".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：爬取(多次)到该电影信息：" + movie);
                    }

                }
                if (timeDifference > 5000) {
                    System.err.println("更新：爬取该电影信息超时" + movieUrl);

                    continue;
                }

                int length1 = 0;
                if (movie.getMovieCollection() != null) {
                    length1 = movie.getMovieCollection().split(",").length;
                }


                int length = 0;
                /*根据爬取到的电影名字查询ES，名字转义 /  ~ ！? [ * ^等特殊字符*/
                String name = QueryParser.escape(movie.getMovieName());

                String[] names = name.split(" ");
                name="";
                for (int i = 0; i < name.length(); i++) {
                    if(!"".equals(names[i])) {
                        name+=names[i];
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
                        "".equals(byMovieNameLike.getMovieCollection())) {

                    movieTotle++;
                    movie.setId(movieTotle);
                    movie.setMovieId(movieTotle);

                    movieESDao.save(movie);

                    System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

                }
                /*ES中存在该电影*/
                else {

                    length = byMovieNameLike.getMovieCollection().split(",").length;

                    /*该电影是否与ES中存在的该电影剧集长度不相同*/
                    if (length1 != length) {

                        movie.setId(byMovieNameLike.getMovieId());
                        movie.setMovieId(byMovieNameLike.getMovieId());
                        movieESDao.save(movie);


                        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：更新电影（剧集改变）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                    } else {
                        System.out.println(sdf.format(System.currentTimeMillis()) + ":更新：该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                    }
                }

            }
            System.out.println(sdf.format(System.currentTimeMillis()) + ":更新：第" + startIndex + "页完成");
            startIndex++;
        }
        System.err.println(sdf.format(System.currentTimeMillis()) + ":更新：退出更新");
        return sdf.format(System.currentTimeMillis()) + ":退出更新";
    }

    //从第一页到最后一页爬取
    @GetMapping("climb/{pageIndex}")
    public void saveMovie(@PathVariable(value = "pageIndex") int pageIndex) {
        StartDSJcount dsj = new StartDSJcount();

        dsj.setDownLoadService(new HttpClientDownloadService());
        dsj.setProcessService(new zdMovieListProcessServiceImpl());
        dsj.setProcessMovie(new zdMovieListProcessServiceImpl());


        /*起始页数*/
        int index = pageIndex;

        /*当前ES中文档总数*/
        int movieTotle = dsj.searchTotle(movieESDao);

        /*此处之后放进一个while（true）的循环内，将页数以步长1依次增加*/
        while (true) {
            String url = baseurl + "/?m=vod-index-pg-" + index + ".html"; //zd

                /*循环下载的次数*/
                int downPageNum = 0;
                Page movieListPage;
                /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
                while (true) {
                    downPageNum++;
                    movieListPage = dsj.downloadMovieListPage(url);

                    if (dsj.judgmentPageDownSuccess(movieListPage)) {
                        if(downPageNum>1) {
                            if(downPageNum>2) {
                                System.out.println("爬取：下载电影集合页面成功 " + downPageNum + "--------------------------------");
                            }
                        }
                        downPageNum = 0;
                        break;
                    }
                    if(downPageNum==1) {
                        System.err.println("爬取：下载电影集合页面失败 " + downPageNum + " " + url);
                    }
                }
                /*增加一个方法判断body/div[5]/ul[2]/li/span[2]/a这个xpath路径(每页第一步电影名称)是否存在，存在继续解析，不存在则跳出循环*/
                if (!dsj.judgmentMovieListPage(movieListPage)) {
                    System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：第" + index + "页没有有电影");

                    break;
                }
                List<String> movieList = dsj.processMovieList(movieListPage);


                for (String movieUrl : movieList) {
                    movieUrl = baseurl + movieUrl;
                    Page moviePage;
                    /*循环下载页面，通过页面特定元素判断下载成功，则退出循环*/
                    while (true) {
                        downPageNum++;
                        moviePage = dsj.downloadMovieListPage(movieUrl);
                        if (dsj.judgmentPageDownSuccess(moviePage)) {
                            if(downPageNum>2) {
                                System.out.println("爬取：下载电影信息页面成功 " + downPageNum + "-----------------------------------------");
                            }
                            downPageNum = 0;
                            break;
                        }
                        if(downPageNum==1) {
                            System.err.println("爬取：下载电影信息页面失败 " + downPageNum + " " + movieUrl);
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


                    while ("".equals(movie.getMovieName()) && timeDifference <= 10000) {


                        /*爬取到的电影*/
                        movie = dsj.processMovie(moviePage);

                        endTime = System.currentTimeMillis();
                        timeDifference = endTime - startTime;

                        if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 0) {
                            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：未爬取到该电影信息：" + movieUrl);
                            movieCrawlingFlag = 1;
                        }
                        if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                            continue;
                        }
                        if (!"".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：爬取(多次)到该电影信息：" + movie);
                        }

                    }
                    if (timeDifference > 10000) {
                        System.err.println("更新：爬取该电影信息超时" + movieUrl);
                        continue;
                    }


                    int length1 = 0;
                    if (movie.getMovieCollection() != null) {
                        length1 = movie.getMovieCollection().split(",").length;
                    }


                    int length = 0;
                    /*根据爬取到的电影名字查询ES，名字去除 / 空格 ~ ！? [ * ^特殊字符*/
                    String name = QueryParser.escape(movie.getMovieName());
                    String[] names = name.split(" ");
                    name="";
                    for (int i = 0; i < name.length(); i++) {
                        if(!"".equals(names[i])) {
                            name+=names[i];
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
                            "".equals(byMovieNameLike.getMovieCollection())) {

                        movieTotle++;
                        movie.setId(movieTotle);
                        movie.setMovieId(movieTotle);

                        movieESDao.save(movie);

                        System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

                    }
                    /*ES中存在该电影*/
                    else {

                        length = byMovieNameLike.getMovieCollection().split(",").length;

                        /*该电影是否与ES中存在的该电影剧集长度不相同*/
                        if (length1 != length) {

                            movie.setId(byMovieNameLike.getMovieId());
                            movie.setMovieId(byMovieNameLike.getMovieId());
                            movieESDao.save(movie);
                            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：更新电影（剧集改变）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());

                        } else {
                            System.out.println(sdf.format(System.currentTimeMillis()) + ":爬取：该电影存在id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                        }
                    }

                }
                System.out.println(sdf.format(System.currentTimeMillis()) + ":爬取：第" + index + "页完成");
                index++;
            }
        System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：退出爬取 "+"爬取到："+index);

    }
        //return "抓取结束";



    /*返回文档总数*/
    public int searchTotle(MovieESDao movieESDao) {//因为本方法没有@GetMapping注解，而this.movieESDao是通过自动注入的，
        // 直接通过this.movieESDao调用findByIdGreaterThan方法会报空指针异常
        int size = movieESDao.findByIdGreaterThan(0).size();
        return size;
    }

    /*删除所有文档*/
    @GetMapping("del")
    public void delAll() {
        StartDSJcount dsj = new StartDSJcount();
        System.err.println(dsj.searchTotle(movieESDao));
        movieESDao.deleteAll();
    }

    //判断某一页的是否存在电影集合
    private boolean judgmentMovieListPage(Page page) {
        return this.judgmentMovieListPageService.zdJudgmentMovieListPage(page);
    }


    //判断某一页的是否下载成功
    private boolean judgmentPageDownSuccess(Page page) {
        return this.judgmentPageDownSuccess.zdJudgmentPageDownSuccess(page);
    }

    //下载某一页电影集合的页面
    private Page downloadMovieListPage(String url) {
        return this.downLoadService.download(url);
    }

    //解析某一页电影集合的页面
    private List processMovieList(Page page) {
        return this.processService.processMovieList(page);
    }

    //解析某一部电影的页面
    private Movie processMovie(Page page) {
        return this.processMovie.processMovie(page);
    }




    public static void main(String[] args) {
        HttpClientDownloadService httpClientDownloadService = new HttpClientDownloadService();

        /*酷云资源测试*/
        String url = "http://www.kuyunzy1.com/list/?0-785.html";
        KuYunMovieListProcessServiceImpl movieListProcessService = new KuYunMovieListProcessServiceImpl();

        Page page = httpClientDownloadService.download(url);
        List list = movieListProcessService.processMovieList(page);
        for (Object o : list) {
            System.err.println(o);
        }
    }
}