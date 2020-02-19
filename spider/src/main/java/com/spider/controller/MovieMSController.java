package com.spider.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spider.entity.Movie;
import com.spider.entity.SwiperPoster;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.mapper.SwiperPosterMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author young
 * @create 2020-02-16 23:53
 * <p>
 * 有关MySql数据库操作的类
 */
@RestController
@Api(tags = "MySql查询公共类", description = "MySql操作")
@CrossOrigin
public class MovieMSController {

    @Autowired
    MovieESDao movieESDao;
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    SwiperPosterMapper swiperPosterMapper;

    /**
     *  此方法根据影视名称，模糊查询mysql
     * @param name 影视名称
     * @return 影视：影视名字、演员、影视Id、资源
     */
    @GetMapping("movie/{name}")
    @ResponseBody
    public List getMovie(@PathVariable(value = "name") String name) {
        long start = System.currentTimeMillis();
        QueryWrapper<Movie> wrapper=new QueryWrapper<>();
        wrapper.like("movieName",name);
        List<Object> byMovieName = Collections.singletonList(movieMapper.selectList(wrapper));
        long end = System.currentTimeMillis();
        System.err.println((end-start)/1000.0);
        return byMovieName;
    }

    /**
     *  此方法根据影视名称，模糊精确查询msyql
     * @param name 影视名称
     * @return 影视：影视名字、演员、影视Id、资源
     */
    @GetMapping("movie/full/{name}")
    @ResponseBody
    public Movie getMovieByFullName(@PathVariable(value = "name") String name) {

        return null;
    }


    @GetMapping("poster")
    @ResponseBody
    public List getSwiperUrl() {



        SwiperPoster swiperPoster = new SwiperPoster();
        for (Long i = 1L; i < 10; i++) {

            Optional<Movie> byId = movieESDao.findById(i);

            Movie movie = byId.get();
            System.err.println(movie.getMovieName());

            swiperPoster.setMovieName(movie.getMovieName());
            swiperPoster.setMoviePoster(movie.getPoster());
            swiperPosterMapper.insert(swiperPoster);
        }

        return null;
    }
}
