package com.spider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author young
 * @create 2020-02-17 0:15
 *
 * 首页轮播影视海报
 */
@Data
@AllArgsConstructor
@NoArgsConstructor //生成无参的构造方法。
@TableName(value = "movie_poster")
public class SwiperPoster {

    @Id
    private int id;
    @TableField("movieName")
    private String movieName="";
    @TableField("moviePoster")
    private String moviePoster="";
}
