package com.spider.controller.util;

import com.spider.mapper.MovieESDao;

/**
 * @author young
 * @create 2020-02-06 16:11
 */
public class ControllerUtil {

    
    public synchronized static int searchTotle(MovieESDao movieESDao) {//因为本方法没有@GetMapping注解，而this.movieESDao是通过自动注入的，
        // 直接通过this.movieESDao调用findByIdGreaterThan方法会报空指针异常
        int size = movieESDao.findByIdGreaterThan(0).size();
        return size;
    }
}
