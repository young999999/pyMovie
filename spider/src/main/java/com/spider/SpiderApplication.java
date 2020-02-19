package com.spider;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author young
 * @create 2020-01-27 9:40
 */
@SpringBootApplication
@MapperScan("com.spider.mapper")

public class SpiderApplication   {
    public static void main(String[] args) {
        SpringApplication.run(SpiderApplication.class, args);
    }


}
