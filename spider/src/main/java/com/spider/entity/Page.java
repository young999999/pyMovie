package com.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author young
 * @create 2020-01-18 18:03
 */
@Data//生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法
@AllArgsConstructor//生成包含类中所有字段的构造方法。
@NoArgsConstructor //生成无参的构造方法。
public class Page {
    //内容
    private String content="";
    private int statusCode=0;


}
