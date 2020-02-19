package com.spider.controller.spiderUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.util.log.LogUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
    private MovieMapper movieMapper;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SaveControllerUtil(MovieESDao movieESDao, MovieMapper movieMapper,Movie movie, File file) {
        this.movieESDao=movieESDao;
        this.movie=movie;
        this.file=file;
        this.movieMapper=movieMapper;
    }

    @Override
    public void run() {

        synchronized (SaveControllerUtil.class){
            Page<Movie> page=new Page(1,1L);
            QueryWrapper queryWrapper=new QueryWrapper<>().orderByDesc("id");
            Page<Movie> selectPage = movieMapper.selectPage(page, queryWrapper);
            int movieTotle = selectPage.getRecords().get(0).getMovieId();
            movieTotle++;
            movie.setId(movieTotle);
            movie.setMovieId(movieTotle);

            Movie save = movieESDao.save(movie);
            movieMapper.insert(movie);

            System.err.println(sdf.format(System.currentTimeMillis()) +" "+
                    Thread.currentThread().getName()+"：\t添加电影id："+movieTotle +" "+movie.getMovieName());

            LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":添加电影id=" + movie.getMovieId() + "：" + movie.getMovieName());


        }


    }


}
