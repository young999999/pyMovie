package com.spider.service.impl;

import com.spider.entity.Movie;
import com.spider.entity.MovieCollection;
import com.spider.entity.Page;
import com.spider.service.ProcessService;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young
 * @create 2020-01-18 18:42
 * 解析电影页面
 */
@Service
public class One58MovieListProcessServiceImpl implements ProcessService {

    @Override
    public List processMovieList(Page page) {

        List movieList = new ArrayList();//存放某页电影集合中每部电影的链接

        String content = page.getContent();//得到下载的页面

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            /*即/html/body/div[4]*/
            Object[] evaluateXPath = rootNoade.evaluateXPath("/body/div[4]");

            if (evaluateXPath.length > 0 && evaluateXPath != null) {
                TagNode node = (TagNode) evaluateXPath[0];

                /*node.getChildTagList() => ul的集合*/
                for (int i = 1; i < node.getChildTagList().size() - 2; i++) {

//                    System.out.println();

                    /*node.getChildTagList().get(i) => ul*/
                    TagNode tagNode = node.getChildTagList().get(i);
//                    System.err.println(tagNode.getName() + ": " + tagNode.getChildTagList().size());

                    /*tagNode.getChildTagList() => li的集合*/
                    for (TagNode tagNode1 : tagNode.getChildTagList()) {


//                        System.out.println(tagNode1.getName() + ": " + tagNode1.getChildTagList().size());

                        for (int j = 1; j < tagNode1.getChildTagList().size() - 1; j++) {

                            if (j == 1) {
//                                System.out.print("电影名称：");
//                                System.err.print(tagNode1.getChildTagList().get(j).getText()+" ");

                                TagNode tagNode2 = tagNode1.getChildTagList().get(j).getChildTagList().get(1);
//                                System.out.print("链接：");
                                String movieUrl = tagNode2.getAttributeByName("href");
//                                String movieUrl = "https://www.okzyw.com" + tagNode2.getAttributeByName("href");
                                movieList.add(movieUrl);
                            }
                            if (j == 2) {
//                                System.out.print("电影类别：");
//                                System.err.print(tagNode1.getChildTagList().get(j).getText()+" ");
                            }
                        }
                    }
                }
            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    @Override
    public Movie processMovie(Page page) {
        Movie movie = new Movie();
        List list = new ArrayList();//存放某电影集合

        String movieCollection = "";//存放某电影集合

        MovieCollection mc = new MovieCollection();

        String content = page.getContent();//得到下载的页面
//        System.err.println(content);

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            //电影每集的链接和集数(m3u8)//*[@id="play_1"]/ul
            Object[] evaluateXPathMovieCollection = rootNoade.evaluateXPath("//*[@id=\"play_1\"]/ul");
            if (evaluateXPathMovieCollection.length > 0 && evaluateXPathMovieCollection != null) {
                TagNode node = (TagNode) evaluateXPathMovieCollection[0];

                /*node.getChildTagList() => li*/
                for (int i = 0; i < node.getChildTagList().size(); i++) {
                    TagNode tagNode = node.getChildTagList().get(i);

                    /*tagNode.getChildTagList().get(0) => input*/
                    String value = tagNode.getChildTagList().get(0).getAttributeByName("value");
                    if (i == node.getChildTagList().size() - 1) {
                        movieCollection = movieCollection.concat("EP" + (i + 1) + "$" + value);
                    } else {
                        movieCollection = movieCollection.concat("EP" + (i + 1) + "$" + value + ",");
                    }
                }

                mc.setOne58collectionm3u8(movieCollection);
            }
//            movieCollection = "";
//            //电影每集的链接和集数(mp4)//*[@id="down_1"]/ul
//            Object[] evaluateXPathMovieCollectionMp4 = rootNoade.evaluateXPath("//*[@id=\"down_1\"]/ul");
//            if (evaluateXPathMovieCollectionMp4.length > 0 && evaluateXPathMovieCollectionMp4 != null) {
//                TagNode node = (TagNode) evaluateXPathMovieCollectionMp4[0];
//
//                /*node.getChildTagList() => li*/
//                for (int i = 0; i < node.getChildTagList().size(); i++) {
//                    TagNode tagNode = node.getChildTagList().get(i);
//
//                    /*tagNode.getChildTagList().get(0) => input*/
//                    String value = tagNode.getChildTagList().get(0).getAttributeByName("value");
//                    if (i == node.getChildTagList().size() - 1) {
//                        movieCollection = movieCollection.concat("EP" + (i + 1) + "$" + value);
//                    } else {
//                        movieCollection = movieCollection.concat("EP" + (i + 1) + "$" + value + ",");
//                    }
//                }
//
//                mc.setZdcollectionmp4(movieCollection);
//            }
            movie.setMc(mc);

            //电影名称/html/body/div[4]/div[1]/div/div/div[2]/div[1]/h2
            Object[] evaluateXPathMovieName = rootNoade.evaluateXPath("/body/div[4]/div[1]/div/div/div[2]/div[1]/h2");

            if (evaluateXPathMovieName.length > 0 && evaluateXPathMovieName != null) {
                TagNode node = (TagNode) evaluateXPathMovieName[0];
                movie.setMovieName(node.getText().toString().trim());
            }
            //电影海报/html/body/div[4]/div[1]/div/div/div[1]/img
            Object[] evaluateXPathPoster = rootNoade.evaluateXPath("/body/div[4]/div[1]/div/div/div[1]/img");

            if (evaluateXPathPoster.length > 0 && evaluateXPathPoster != null) {
                TagNode node = (TagNode) evaluateXPathPoster[0];
                movie.setPoster(node.getAttributeByName("src"));
            }


            //演员
            Object[] evaluateXPathActor = rootNoade.evaluateXPath("/body/div[4]/div[1]/div/div/div[2]/div[2]/ul/li[3]/span");
            if (evaluateXPathMovieName.length > 0 && evaluateXPathActor != null) {
                TagNode node = (TagNode) evaluateXPathActor[0];
                String actor = node.getText().toString();
                String[] split = actor.split("&nbsp");
                actor="";
                for (int i = 0; i < split.length; i++) {
                    actor += split[i];
                }
                movie.setActor(actor);
            }

            //电影类别/html/body/div[5]/div[1]/div/div/div[2]/div[2]/ul/li[4]/span
            Object[] evaluateXPathMovieCategory = rootNoade.evaluateXPath("/body/div[4]/div[1]/div/div/div[2]/div[2]/ul/li[4]/span");

            if (evaluateXPathMovieCategory.length > 0 && evaluateXPathMovieCategory != null) {
                TagNode node = (TagNode) evaluateXPathMovieCategory[0];
                movie.setMovieCategory(node.getText().toString().split(" ")[0]);
            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    public Boolean processTotlePage(Page page, int pageIndex) {


        String content = page.getContent();//得到下载的页面

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            Object[] evaluateXPath = rootNoade.evaluateXPath("/body/div[4]/ul[2]/li/span[2]/a");

            if (evaluateXPath.length > 0) {

                return true;
            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Boolean judgmentPageDownSuccess(Page page) {


        String content = page.getContent();//得到下载的页面

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {/*/html/body/div[1]/ul/li[1]/img*/
            Object[] evaluateXPath = rootNoade.evaluateXPath("/body/div[1]/ul/li[1]/img");

            if (evaluateXPath.length > 0) {

                return true;

            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
//        System.err.println("下载页面失败");
        return false;
    }

}
