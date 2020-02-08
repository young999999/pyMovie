package com.spider.controller;

import com.spider.controller.util.SaveControllerUtil;
import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author young
 * @create 2020-01-27 11:18
 */

@RestController
@Api(tags = "公共类", description = "资源爬取的公共类")
@CrossOrigin
public class MovieController {
    @Autowired
    MovieMapper movieMapper;
    @Autowired
    MovieESDao movieESDao;

    @GetMapping("movie/{name}")
    @ResponseBody
    public List<Movie> getMovie(@PathVariable(value = "name") String name){


//        Movie movie = movieMapper.findByMovieName(name);
        List<Movie> byMovieName = movieESDao.findByMovieName(name);
//        System.err.println(movie);
        return byMovieName;
    }


    /*删除所有文档*/
    @GetMapping("del")
    public void delAll() {

        System.err.println(movieESDao.findByIdGreaterThan(0).size());
        movieESDao.deleteAll();
    }

    @GetMapping("movie1/{id}")
    @ResponseBody
    public Movie getMovie1(@PathVariable(value = "id") Long id) {

        Movie movie = movieMapper.selectById(id);
        System.err.println(movie);
        return movie;
    }


}
