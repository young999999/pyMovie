package com.spider.mapper.impl;

import com.alibaba.fastjson.JSON;
import com.spider.entity.Movie;
import com.spider.entity.MovieCollection;
import com.spider.mapper.MovieMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springfox.documentation.spring.web.json.Json;

/**
 * @author young
 * @create 2020-02-11 23:30
 */
@Repository
public class MovieMapperImpl {
    @Autowired
    MovieMapper movieMapper;
        public Movie selectById(int id){
            Movie movie = movieMapper.selectById(id);

            MovieCollection mc = movie.getMc();
            String s= JSON.toJSONString(mc);
            System.out.println(s);

            return movie;
        }
        public void insert(Movie movie){

        }
}
