package com.spider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spider.entity.SwiperPoster;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author young
 * @create 2020-02-17 0:19
 * @blame Android Team
 */

@Repository
public interface SwiperPosterMapper extends BaseMapper<SwiperPoster> {
    public List<SwiperPoster> findSwiperPosterAll();

}
