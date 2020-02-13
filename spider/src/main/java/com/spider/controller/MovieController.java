package com.spider.controller;

import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public List getMovie(@PathVariable(value = "name") String name) {
        List<Movie> byMovieName = movieESDao.findByMovieName(name);
        for (Movie movie : byMovieName) {
            System.err.println(movie);
        }
        return byMovieName;
    }

    @GetMapping("movie")
    @ResponseBody
    public int getMovie1() {
        long start = System.currentTimeMillis();


        Iterable<Movie> all = movieESDao.findAll();
        int i=0;
        for (Movie movie : all) {
            i++;
        }
        long end = System.currentTimeMillis();
        System.err.println((end - start) / 1000.0);
        return i;

    }

    @GetMapping("movie/full/{name}")
    @ResponseBody
    public Movie getMovieByFullName(@PathVariable(value = "name") String name) {
        List<Movie> byMovieName = movieESDao.findByMovieName(name);
        for (Movie movie : byMovieName) {
            if (movie.getMovieName().equals(name)) return movie;
        }
//        System.err.println(movie);
        return null;
    }


    /*删除所有文档*/
    @GetMapping("del")
    public void delAll() {

        System.err.println(movieESDao.findByIdGreaterThan(0).size());
        movieESDao.deleteAll();
    }

    @GetMapping("movie1/{id}")
    @ResponseBody
    public Movie getMovie1(@PathVariable(value = "id") int id) {
        Movie movie = new Movie();
//        MovieCollection movieCollection=new MovieCollection("1","1","1","1","1","1","1","1");
//        movie.setMovieId(id);
//        movie.setMovieName("锦衣之下");
//        movie.setPoster("132");
//        movie.setActor("oy");
//        movie.setMovieCategory("dd");
//        movie.setMc(movieCollection);
//        movieMapper.insert(movie);
        movie = movieMapper.selectById(id);
        System.err.println(movie);
        return movie;
    }

    @GetMapping("rest")
    public Movie restTest() {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject("http://localhost:8088/movie/full/锦衣之下", Movie.class);
        System.err.println(movie);
        return movie;
    }

}
