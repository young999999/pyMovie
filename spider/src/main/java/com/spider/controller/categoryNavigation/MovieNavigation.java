package com.spider.controller.categoryNavigation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spider.entity.Movie;
import com.spider.mapper.MovieMapper;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author young
 * @create 2020-02-17 20:29
 */
@Data
@RestController
@Api(tags = "影视导航", description = "有关影视导航的接口")
@CrossOrigin
public class MovieNavigation {
    @Autowired
    MovieMapper movieMapper;

    @GetMapping("film/{category}/{pageIndex}")
//    public Movie  film(){
    public List<Movie> film(@PathVariable String category, @PathVariable Long pageIndex){
        Page<Movie> page=new Page(pageIndex,9L);
        QueryWrapper queryWrapper=new QueryWrapper<>().like("movieCategory",category).orderByDesc("id");
        Page<Movie> selectPage = movieMapper.selectPage(page, queryWrapper);
//        for (Movie record : selectPage.getRecords()) {
//            System.err.println(record.getMovieId()+"\t"+record.getMovieCategory()+"\t"+record.getMovieName());
//        }
        return selectPage.getRecords();
    }
}
