package test;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.SpiderApplication;
import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.service.ProcessService;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.util.PageGetUtil;
import com.spider.util.impl.KuYunPageGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;


/**
 * @author young
 * @create 2020-02-11 23:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
public class MybatisPlusTest {

    @Resource
    MovieMapper movieMapper;
    @Resource
    MovieESDao movieESDao;

    @Test
    public void test2() {

        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("movieName", "锦衣之下");
        Movie movie = movieMapper.selectOne(wrapper);
        System.err.println(movie);
//        List<List<Movie>> byMovieName = Collections.singletonList(movieMapper.selectList(wrapper));
//        List<Movie> movies = byMovieName.get(0);
//        System.err.println(movies);
    }


    @Test
    public void test1() {
        PageGetUtil commonPageGet = new KuYunPageGet();

        /*酷云资源测试*/

        String url = "http://www.kuyunzy1.com/detail/?41012.html";
//        String url = "http://www.kuyunzy1.com/list/?0-793.html";

        ProcessService movieListProcessService = new KuYunMovieListProcessServiceImpl();

        Page page = commonPageGet.download(url);
//        System.err.println(page.getContent());
//        System.err.println(movieListProcessService.judgmentPageDownSuccess(page));
//        System.err.println(movieListProcessService.processTotlePage(page));
        Movie movie = movieListProcessService.processMovie(page);
//        System.err.println(movie);
//        movieMapper.insert(movie);
//        System.err.println(movie);
    }
}
