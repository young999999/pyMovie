package com.spider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("movie")
@Document(indexName = "movie", type = "movie")
public class Movie {


    @TableField("id")
    @Id/*ES中文档id*/
    private int id;

    @TableField("movieId")
    @Field(type = FieldType.Text)
    private int movieId = 0;//电影ID，值等于id，因id不能被后台获取，增加此属性

    @TableField("movieName")
    @Field(index = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String movieName = "";//电影名

    @Field(type = FieldType.Text)
    private String actor = "";//演员


    @TableField("movieCategory")
    @Field(type = FieldType.Text)
    private String movieCategory = "";//电影类别

//    @Field(type = FieldType.Text)
//    private String movieState="";//更新状态

    @Field(type = FieldType.Text)
    private String poster;//更新状态

//    @TableField("movieCollection")
//    @Field(type = FieldType.Text)
//    private String movieCollection = "";//电影集（m3u8）
//
//
//    @TableField("movieCollectionMp4")
//    @Field(type = FieldType.Text)
//    private String movieCollectionMp4 = "";//电影集（mp4）


    @Field(type = FieldType.Object)
//    private Map<Object,Object> MovieCollectionMap=new HashMap<Object,Object>();
    private MovieCollection mc = new MovieCollection();

    public static void main(String[] args) throws UnsupportedEncodingException {


        String s="";
        System.err.println(s.split(",").length);

    }
}

