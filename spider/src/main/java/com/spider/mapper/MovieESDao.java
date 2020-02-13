package com.spider.mapper;

import com.spider.entity.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young
 * @create 2020-01-28 18:20
 */
//@Repository
public interface MovieESDao extends ElasticsearchRepository<Movie, String> {
    public List<Movie> findByMovieNameLike(String name);
    public List<Movie> findByMovieName(String name);
    public List<Movie> findByIdGreaterThan(int id);

}
