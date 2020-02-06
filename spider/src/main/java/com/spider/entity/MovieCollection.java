package com.spider.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author young
 * @create 2020-02-06 12:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//生成无参的构造方法。
public class MovieCollection {
    String kycollectionm3u8 = "";
    String kycollectionmp4 = "";
    String zdcollectionm3u8 = "";
    String zdcollectionmp4 = "";
    String okcollectionm3u8 = "";
    String okcollectionmp4 = "";
    String two09collectionm3u8 = "";
    String two09collectionmp4 = "";

}
