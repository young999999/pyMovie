package com.spider.controller.util;

import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.util.log.LogUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @author young
 * @create 2020-02-06 16:11
 */

@Data
@NoArgsConstructor

public class SaveControllerUtil extends Thread{

    private MovieESDao movieESDao;
    private Movie movie;
    private File file;


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SaveControllerUtil(MovieESDao movieESDao, Movie movie, File file) {
        this.movieESDao=movieESDao;
        this.movie=movie;
        this.file=file;
    }

    @Override
    public void run() {

        synchronized (SaveControllerUtil.class){
            int movieTotle = movieESDao.findByIdGreaterThan(0).size();
            movieTotle++;
            movie.setId(movieTotle);
            movie.setMovieId(movieTotle);
            movieESDao.save(movie);
            LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());

//            System.err.println(Thread.currentThread().getName()+"："+movieTotle+" 添加");
        }


    }


}
