package com.spider.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author young
 * @create 2020-01-27 11:18
 *
 * 有关ES数据库操作的类
 */

@RestController
@Api(tags = "ES查询公共类", description = "ES操作")
@CrossOrigin
public class MovieESController {
    @Autowired
    MovieESDao movieESDao;
    @Autowired
    MovieMapper movieMapper;



    /**
     *  此方法根据影视名称，模糊精确查询ES
     * @param name 影视名称
     * @return 影视：影视名字、演员、影视Id、资源
     */
    @GetMapping("movie/full/{name}")
    @ResponseBody
    public Movie getMovieByFullName(@PathVariable(value = "name") String name) {
        List<Movie> byMovieName = movieESDao.findByMovieName(name);
        for (Movie movie : byMovieName) {
            if (movie.getMovieName().equals(name)) return movie;
        }
        return null;
    }


    /**
     * 此方法查询并返回ES中有多少数据，及查询时间
     * @return
     */
    @GetMapping("totle")
    @ResponseBody
    public String getTotleMovie() {
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Page<Movie> page=new Page(1,1L);
        QueryWrapper queryWrapper=new QueryWrapper<>().orderByDesc("id");
        Page<Movie> selectPage = movieMapper.selectPage(page, queryWrapper);

        long end = System.currentTimeMillis();
//        System.err.println((end - start) / 1000.0);
        return sdf.format(System.currentTimeMillis())+"\t总数：" + selectPage.getRecords().get(0).getMovieId() + "\t用时："+((end - start) / 1000.0);

    }



    /**
     * 删除所有文档
     */
    @GetMapping("del")
    public void delAll() {

        System.err.println(movieESDao.findByIdGreaterThan(0).size());
        movieESDao.deleteAll();
    }





}
