package test;


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
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


/**
 * @author young
 * @create 2020-02-11 23:11
 */
@RunWith(JUnit4.class)
@SpringBootTest(classes = MybatisPlusTest.class)
public class MybatisPlusTest {

    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieESDao movieESDao;

    @Test
    public void test2() {
        long start = System.currentTimeMillis();


        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Optional<Movie> byId = movieESDao.findById("1");
            Movie movie = byId.get();
            if (movie==null) break;

        }
        long end = System.currentTimeMillis();
        System.err.println((end - start) / 1000.0);

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
        System.err.println(movie);
        movieMapper.insert(movie);
        System.err.println(movie);
    }
}