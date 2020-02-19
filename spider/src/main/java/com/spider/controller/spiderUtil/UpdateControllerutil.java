package com.spider.controller.spiderUtil;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.spider.entity.Movie;
import com.spider.mapper.MovieESDao;
import com.spider.mapper.MovieMapper;
import com.spider.util.log.LogUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author young
 * @create 2020-02-08 20:40
 */

@Data
@NoArgsConstructor
public class UpdateControllerutil extends Thread {

    private MovieESDao movieESDao;
    private MovieMapper movieMapper;
    private Movie movie;
    private File file;
    private String movieName;
    private String flag;


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UpdateControllerutil(MovieESDao movieESDao, MovieMapper movieMapper, Movie movie, File file, String movieName, String flag) {
        this.movieESDao = movieESDao;
        this.movieMapper=movieMapper;
        this.movie = movie;
        this.file = file;
        this.movieName = movieName;
        this.flag = flag;
    }

    @Override
    public void run() {

        synchronized (SaveControllerUtil.class) {
            List<Movie> byMovieNameLikeList = movieESDao.findByMovieNameLike(movieName);

            //保存按影视名从ES中查询的数据
            Movie byMovieNameLike = new Movie();
            for (Movie movie1 : byMovieNameLikeList) {
                if (movie.getMovieName().equals(movie1.getMovieName())) {
                    byMovieNameLike = movie1;

                    byMovieNameLike.setId(byMovieNameLike.getMovieId());

                    if ("209".equals(flag)) {
                        byMovieNameLike.getMc().setTwo09collectionm3u8(movie.getMc().getTwo09collectionm3u8());
                        byMovieNameLike.getMc().setTwo09collectionmp4(movie.getMc().getTwo09collectionmp4());
                    }
                    if ("ky".equals(flag)) {
                        byMovieNameLike.getMc().setKycollectionm3u8(movie.getMc().getKycollectionm3u8());
                        byMovieNameLike.getMc().setKycollectionmp4(movie.getMc().getKycollectionmp4());
                    }
                    if ("158".equals(flag)) {
                        byMovieNameLike.getMc().setOne58collectionm3u8(movie.getMc().getOne58collectionm3u8());
                        byMovieNameLike.getMc().setOne58collectionmp4(movie.getMc().getOne58collectionmp4());
                    }
                    if ("123".equals(flag)) {
                        byMovieNameLike.getMc().setOne23collectionm3u8(movie.getMc().getOne23collectionm3u8());
                        byMovieNameLike.getMc().setOne23collectionmp4(movie.getMc().getOne23collectionmp4());
                    }
                    if ("zd".equals(flag)) {
                        byMovieNameLike.getMc().setZdcollectionm3u8(movie.getMc().getZdcollectionm3u8());
                        byMovieNameLike.getMc().setZdcollectionmp4(movie.getMc().getZdcollectionmp4());
                    }
                    if ("ok".equals(flag)) {
                        byMovieNameLike.getMc().setOkcollectionm3u8(movie.getMc().getOkcollectionm3u8());
                        byMovieNameLike.getMc().setOkcollectionmp4(movie.getMc().getOkcollectionmp4());
                    }

                    Movie save = movieESDao.save(byMovieNameLike);

                    movieMapper.update(byMovieNameLike,new UpdateWrapper<Movie>()
                            .eq("movieId",byMovieNameLike.getMovieId())
                    );

                    LogUtil.fileWriter(file, sdf.format(System.currentTimeMillis()) + ":更新电影（剧集改变或该资源新添加）id=" + byMovieNameLike.getMovieId() + "：" + movie.getMovieName());
                    System.err.println(sdf.format(System.currentTimeMillis()) +" "+
                            Thread.currentThread().getName()+"：\t更新电影id："+byMovieNameLike.getMovieId() +" "+save.getMovieName());

                    return;
                }
            }
            System.err.println(sdf.format(System.currentTimeMillis()) +" "+ Thread.currentThread().getName()+"：未查找到电影 "+movieName);
        }


    }


}
