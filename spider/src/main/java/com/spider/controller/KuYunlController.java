package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.util.PageGetUtil;
import com.spider.service.ProcessService;
import com.spider.util.impl.CommonPageGet;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import lombok.Data;
import org.apache.lucene.queryparser.classic.QueryParser;
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
public class KuYunlController {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieESDao movieESDao;

    private PageGetUtil downLoadService;
    private ProcessService processService;
    private ProcessService judgmentMovieListPageService;
    private ProcessService processMovie;
    private String baseurl = "http://www.okzyw.com";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String nowTime = "";


    //从第一页到最后一页爬取
    @GetMapping("url/{pageIndex}")
    public void saveMovie(@PathVariable(value = "pageIndex") int pageIndex) {
        KuYunlController dsj = new KuYunlController();
        dsj.setDownLoadService(new CommonPageGet());

        dsj.setProcessService(new OkMovieListProcessServiceImpl());
        dsj.setJudgmentMovieListPageService(new OkMovieListProcessServiceImpl());
        dsj.setProcessMovie(new OkMovieListProcessServiceImpl());

        int number=0;
        while (true) {

            /*起始页数*/
            int index = pageIndex;

            /*当前ES中文档总数*/
//            int movieTotle = dsj.searchTotle(movieESDao);

            /*此处之后放进一个while（true）的循环内，将页数以步长1依次增加*/
            while (true) {

                String url = baseurl + "/?m=vod-index-pg-" + index + ".html";
                Page movieListPage = dsj.downloadMovieListPage(url);//得到某页电影集合页面

                /*增加一个方法判断body/div[5]/ul[2]/li/span[2]/a这个xpath路径(每页第一步电影名称)是否存在，存在继续解析，不存在则跳出循环*/
                if (index > dsj.judgmentMovieListPage(movieListPage)) {
                    System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：第" + index + "页没有有电影");

                    break;
                }
                List<String> movieList = dsj.processMovieList(movieListPage);


                for (String movieUrl : movieList) {
                    movieUrl = baseurl + movieUrl;

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
                    int i = 0;
                    while ("".equals(movie.getMovieName()) && timeDifference <= 120000) {
                        Page moviePage = null;

                        moviePage = dsj.downloadMovieListPage(movieUrl);

                        /*爬取到的电影*/
                        movie = dsj.processMovie(moviePage);

                        endTime = System.currentTimeMillis();
                        timeDifference = endTime - startTime;

                        if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 0) {
//                            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：未爬取到该电影信息：" + movieUrl);
                            movieCrawlingFlag = 1;
                        }
                        if ("".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
                            continue;
                        }
                        if (!"".equals(movie.getMovieName()) && movieCrawlingFlag == 1) {
//                            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：爬取(多次)到该电影信息：" + movie);
                        }

                    }
                    if (timeDifference > 120000) {
//                        System.err.println("更新：爬取该电影信息超时" + movieUrl);
                        continue;
                    }


                    /*根据爬取到的电影名字查询ES，名字去除 / 空格 ~ ！? [ * ^特殊字符*/
                    String name = QueryParser.escape(movie.getMovieName());
                    String[] names = name.split(" ");
                    if (names.length > 1) {
                        name = names[0] + names[1];
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
                    number++;
                    /*ES中存在该电影，并且海报为空，直接向MySql和Elasticsearch中该电影的海报*/
                    if (!"".equals(byMovieNameLike.getMovieName()) && "".equals(byMovieNameLike.getPoster())) {

                        byMovieNameLike.setId( byMovieNameLike.getMovieId());
                        byMovieNameLike.setPoster(movie.getPoster());

                        movieESDao.save(byMovieNameLike);
                        System.err.println("影视海报抓取"+number+"："+byMovieNameLike.getMovieName()+" 添加成功 "+byMovieNameLike.getPoster());
//                        System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

                    }else if (!"".equals(byMovieNameLike.getMovieName()) && !"".equals(byMovieNameLike.getPoster())) {
                        System.err.println("影视海报抓取"+number+"："+byMovieNameLike.getMovieName()+" 海报已有 "+byMovieNameLike.getPoster());
                    }
                    /*ES中不存在该电影*/
                    else  if ("".equals(byMovieNameLike.getMovieName())){
                        System.err.println("影视海报抓取"+number+"："+movie.getMovieName()+" 不存在");
                    }

                }
                System.out.println(sdf.format(System.currentTimeMillis()) + "影视海报抓取：第" + index + "页完成");
                index++;
            }
            index=pageIndex;
//            System.err.println(sdf.format(System.currentTimeMillis()) + ":爬取：退出爬取");
        }
        //return "抓取结束";

    }

    /*返回文档总数*/
    public int searchTotle(MovieESDao movieESDao) {//因为本方法没有@GetMapping注解，而this.movieESDao是通过自动注入的，
        // 直接通过this.movieESDao调用findByIdGreaterThan方法会报空指针异常
        int size = movieESDao.findByIdGreaterThan(0).size();
        return size;
    }



    //判断某一页的是否存在电影集合
    private Integer judgmentMovieListPage(Page page) {
        return this.judgmentMovieListPageService.judgmentMovieListPage(page);
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
        CommonPageGet commonPageGet = new CommonPageGet();

        /*酷云资源测试*/
        String url = "http://www.kuyunzy1.com/list/?0-785.html";
        KuYunMovieListProcessServiceImpl movieListProcessService = new KuYunMovieListProcessServiceImpl();

        Page page = commonPageGet.download(url);
        List list = movieListProcessService.processMovieList(page);
        for (Object o : list) {
            System.err.println(o);
        }
    }
}
