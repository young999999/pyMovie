package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author young
 * @create 2020-01-27 11:18
 */

@RestController
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

    @GetMapping("movie1/{id}")
    @ResponseBody
    public Movie getMovie1(@PathVariable(value = "id") Long id) {

        Movie movie = movieMapper.selectById(id);
        System.err.println(movie);
        return movie;
    }


}
