package com.spider.util.download;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.util.impl.KuYunPageGet;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author young
 * @create 2020-01-18 16:09
 */


public class KuYunPageDownUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Page getPageContent(String url) {
        Page page = new Page();
        String content = null;
        int statusCode = 0;


        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        CloseableHttpClient client = builder.build();

        RequestConfig requestConfig=RequestConfig.custom()
                .setConnectTimeout(10000)//创建连接最长时间
                .setConnectionRequestTimeout(500)//获取连接最长时间
                .setSocketTimeout(30000)//数据传输的最长时间
                .build();

        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);

        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        request.addHeader("Cache-Control", "max-age=0");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Upgrade-Insecure-Requests", "1");

//        request.addHeader("Cookie","PHPSESSID=qv76mb46cmj9q1gbdvvsr8kes7; UM_distinctid=1700ef2eb40b0-0a5b85da50849b-3a614f0b-100200-1700ef2eb41a5; CNZZDATA1261462053=244164477-1580794588-%7C1580794588; mac_history=%7Bvideo%3A%5B%7B%22name%22%3A%22%u574F%u7231%u60C5%22%2C%22link%22%3A%22/%3Fm%3Dvod-detail-id-73720.html%22%2C%22typename%22%3A%22%u97E9%u56FD%u5267%22%2C%22typelink%22%3A%22/%3Fm%3Dvod-type-id--pg-1.html%22%2C%22pic%22%3A%22upload/vod/2019-12-02/201912021575262163.jpg%22%7D%5D%7D");
//        request.addHeader("Host","www.zuidazy2.com");
//        request.addHeader("Referer","http://www.zuidazy2.com/");
        CloseableHttpResponse response = null;
        try {


            response = client.execute(request);
            statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
//            content = EntityUtils.toString(entity);
            content = EntityUtils.toString(entity, "gb2312");//kuyun

            page.setContent(content);
            page.setStatusCode(statusCode);

        } catch (HttpHostConnectException e) {
//            System.err.println(sdf.format(System.currentTimeMillis()) + " socket连接错误：" + e.getMessage());

        } catch (ClientProtocolException e) {
//            System.err.println(sdf.format(System.currentTimeMillis()) + " 客户端协议异常" + e.getMessage());

        } catch (IOException e) {
//            System.err.println(sdf.format(System.currentTimeMillis()) + " IO异常" + e.getMessage());
//            e.printStackTrace();

        } finally {
            request.releaseConnection();
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                System.err.println(sdf.format(System.currentTimeMillis()) + " response关闭异常：" + e.getMessage());
//                e.printStackTrace();
            } finally {
                return page;
            }
        }


    }


    //    @Autowired
//    static MovieMapper movieMapper;
    public static void main(String[] args) throws UnsupportedEncodingException {


        KuYunPageGet httpClientDownloadService = new KuYunPageGet();
        /*酷云资源测试*/

        String url = "http://www.kuyunzy1.com/detail/?40311.html";
//        String url = "http://www.kuyunzy1.com/list/?0-2.html";


        int j = 0;
        while (true) {
            KuYunMovieListProcessServiceImpl movieListProcessService = new KuYunMovieListProcessServiceImpl();

            Page page;
            int i = 0;
            while (true) {
                page = httpClientDownloadService.download(url);
                i++;
                if (movieListProcessService.judgmentPageDownSuccess(page)) {
                    System.err.println("成功" + i);
                    break;
                }
                System.err.println("失败" + i);
            }
            Movie movie = movieListProcessService.processMovie(page);
            System.err.println(j++);
            System.err.println(movie.getMovieName());
        }


    }

    @Test
    public void kyTest() {

        KuYunPageGet httpClientDownloadService = new KuYunPageGet();
        /*酷云资源测试*/

        String url = "http://www.kuyunzy1.com/detail/?40311.html";
//        String url = "http://47.98.53.232:8088/";
        httpClientDownloadService.download(url);

    }
}
