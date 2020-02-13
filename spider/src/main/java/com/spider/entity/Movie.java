package com.spider.entity;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.UnsupportedEncodingException;

/**
 * @author young
 * @create 2020-01-25 20:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor //生成无参的构造方法。
@TableName(value = "movie",autoResultMap = true)

@Document(indexName = "movie", type = "movie")
public class Movie {


    @TableField("id")
    @Id/*ES中文档id*/
    private int id;

    @TableField("movieId")
    @Field(type = FieldType.Integer)
    private int movieId = 0;//电影ID，值等于id，因id不能被后台获取，增加此属性

    @TableField("movieName")
    @Field(index = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String movieName = "";//电影名

    @TableField("actor")
    @Field(type = FieldType.Text)
    private String actor = "";//演员


    @TableField("movieCategory")
    @Field(type = FieldType.Text)
    private String movieCategory = "";//电影类别

//    @Field(type = FieldType.Text)
//    private String movieState="";//更新状态

    @TableField("poster")
    @Field(type = FieldType.Text)
    private String poster;//更新状态


    @TableField(typeHandler = JacksonTypeHandler.class)
//    @TableField(typeHandler = FastjsonTypeHandler.class)
    @Field(type = FieldType.Object)
    private MovieCollection mc=new MovieCollection();





    public static void main(String[] args) throws UnsupportedEncodingException {


        String s="";
        System.err.println(s.split(",").length);

    }
}

