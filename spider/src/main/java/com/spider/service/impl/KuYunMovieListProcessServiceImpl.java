package com.spider.service.impl;

import com.spider.entity.Movie;
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
public class KuYunMovieListProcessServiceImpl implements ProcessService {

    @Override
    public List processMovieList(Page page) {

        List movieList = new ArrayList();//存放某页电影集合中每部电影的链接

        String content = page.getContent();//得到下载的页面


        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            /*即/html/body/table[2]/tbody*/
            Object[] evaluateXPath = rootNoade.evaluateXPath("body/table[2]/tbody");

            if (evaluateXPath.length > 0 && evaluateXPath != null) {
                /*node => tbody*/
                TagNode node = (TagNode) evaluateXPath[0];

                /*node.getChildTagList() => tr的集合*/
                for (int i = 3; i < node.getChildTagList().size(); i++) {
                    if (i % 3 == 0) {
                        /*tagNode => tr*/
                        TagNode tagNode = node.getChildTagList().get(i);
                        /*tagNode1 => td*/
                        TagNode tagNode1 = tagNode.getChildTagList().get(0);
                        /*tagNode1 => a*/
                        TagNode tagNode2 = tagNode1.getChildTagList().get(0);

//                        System.out.print("电影名称：");
//                        System.err.print(tagNode2.getText() + " ");
//
//                        System.out.print("链接：");
                        String movieUrl = tagNode2.getAttributeByName("href");
                        movieList.add(movieUrl);
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
        movie.setPoster("");
        List list = new ArrayList();//存放某电影集合
        String movieCollection = new String();//存放某电影集合

        String content = page.getContent();//得到下载的页面
//        System.err.println(content);

        HtmlCleaner htmlCleaner = new HtmlCleaner();

        TagNode rootNoade = htmlCleaner.clean(content);


        try {

            //电影名称
            Object[] evaluateXPathMovieName = rootNoade.evaluateXPath("/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[1]/td/font");
            if (evaluateXPathMovieName.length > 0 && evaluateXPathMovieName != null) {
                TagNode node = (TagNode) evaluateXPathMovieName[0];

                String name="";
                name=node.getText().toString();

                movie.setMovieName(name);
            }

            //演员
            Object[] evaluateXPathActor = rootNoade.evaluateXPath("/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[3]/td/font");
            if (evaluateXPathMovieName.length > 0 && evaluateXPathActor != null) {
                TagNode node = (TagNode) evaluateXPathActor[0];
                movie.setActor(node.getText().toString());
            }



            //电影类别
            Object[] evaluateXPathMovieCategory = rootNoade.evaluateXPath("/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[5]/td/font");
            if (evaluateXPathMovieCategory.length > 0 && evaluateXPathMovieCategory != null) {
                TagNode node = (TagNode) evaluateXPathMovieCategory[0];
                movie.setMovieCategory(node.getText().toString());
            }



            //电影更新状态/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[8]/td/font
//            Object[] evaluateXPathMovieState = rootNoade.evaluateXPath("/body/table[2]/tbody/tr[1]/td[2]/table/tbody/tr[8]/td/font");
//            if (evaluateXPathMovieState.length > 0 && evaluateXPathMovieState != null) {
//                TagNode node = (TagNode) evaluateXPathMovieState[0];
//                movie.setMovieState(node.getText().toString());
//            }


            //电影每集的链接和集数
            Object[] evaluateXPathMovieCollection = rootNoade.evaluateXPath("/body/table[2]/tbody/tr[4]/td/table/tbody");
            if (evaluateXPathMovieCollection.length > 0 && evaluateXPathMovieCollection != null) {
                /*node => tbody*/
                TagNode node = (TagNode) evaluateXPathMovieCollection[0];

                /*node.getChildTagList().get(i) => tr*/
                for (int i = 0; i < node.getChildTagList().size()-1; i++) {

                    /*tagNode => td*/
                    TagNode tagNode = node.getChildTagList().get(i);

                    /*tagNode.getChildTagList().get(0) => input*/
                    TagNode tagNode1 = tagNode.getChildTagList().get(0);
                    String value = tagNode1.getChildTagList().get(0).getAttributeByName("value");
                    if (i == node.getChildTagList().size() - 2) {
                        movieCollection = movieCollection.concat("EP" + (i + 1) + "：" + "$" + value);
                    } else {
                        movieCollection = movieCollection.concat("EP" + (i + 1) + "：" + "$" + value + ",");
                    }
                }
                movie.setMovieCollectionMp4(movieCollection);
            }
        } catch (XPatherException  e) {
            e.printStackTrace();
        }
        return movie;
    }


    @Override
    public Integer judgmentMovieListPage(Page page) {


        String content = page.getContent();//得到下载的页面

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            Object[] evaluateXPath = rootNoade.evaluateXPath("/body/table[3]/tbody/tr/td/span");

            if (evaluateXPath.length > 0) {
                TagNode node = (TagNode) evaluateXPath[0];
                String text = node.getText()+"";
                String[] s = text.split(" ")[0].split("/");


                return Integer.parseInt(s[1]);

            }

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean judgmentPageDownSuccess(Page page) {


        String content = page.getContent();//得到下载的页面

        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNoade = htmlCleaner.clean(content);
        try {
            Object[] evaluateXPath = rootNoade.evaluateXPath("//*[@id=\"logoSea\"]/div[1]/a/img");

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
