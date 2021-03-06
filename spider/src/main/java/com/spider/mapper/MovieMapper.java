package com.spider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spider.entity.Movie;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author young
 * @create 2020-01-27 9:49
 */
@Repository
public interface MovieMapper extends BaseMapper<Movie> {

//    @Select("select * from movie where movieName=#{name}")
    public Movie findByMovieName(String name);

    void updateByMovieId(int id);


}
