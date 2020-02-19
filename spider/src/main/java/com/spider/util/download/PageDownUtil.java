package com.spider.util.download;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.util.impl.CommonPageGet;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

/**
 * @author young
 * @create 2020-01-18 16:09
 */

public class PageDownUtil {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Page getPageContent(String url) {
        Page page = new Page();
        String content = null;
        int statusCode = 0;

        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

        CloseableHttpClient client = builder.build();


        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000)//创建连接最长时间
                .setConnectionRequestTimeout(5000)//获取连接最长时间
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
//        request.addHeader("Cookie","ASPSESSIONIDQASSQADC=COJJCHGAFLDBAFODPBPFNKDJ; __51cke__=; ASPSESSIONIDSASRSADD=KHPJHCGALNNIOOOIAMDEIEFF; ASPSESSIONIDSATRSACC=JKFKMNFAMOMNAEENGNJKKPKI; ASPSESSIONIDSATQTBDD=FHMBNEGAPHODPNKIEKPLCJHM; ASPSESSIONIDSASTRADC=EJDCCAGANJIEMIBDIHGMPIGE; ASPSESSIONIDSCTSQADC=IJIDIJGAGPODDEKNJGGCKLHG; ASPSESSIONIDQCSSRBCC=LPJLNLGAKHKIDPHLEKGMFJCG; ASPSESSIONIDQASSRBDC=ELDKBEKAHINCPBODOIMCEJOE; ASPSESSIONIDSCQTQACD=JGFJHNKANOPGBPDIDHNABPDI; ASPSESSIONIDQCRRTBDD=HHOKMIKAJKNNAAEABELICPDL; ASPSESSIONIDQAQRSBCD=EPLDNPKAOBAAPLIMPEGGNONK; ASPSESSIONIDQCSTSDAD=BJHKDAMAAGFFJNOAIOOHFIMN; ASPSESSIONIDQATRSACC=KFAOILLALINMNNCJHIHFBONM; ASPSESSIONIDSARRTBCD=ODNBJCMANDHPPBHKKIPIFFMO; ASPSESSIONIDQARRTACC=NGMMJJMAKLDMINNEOIAJFMNK; ASPSESSIONIDQCSSQBCD=AKKLEJBBHEIHBAMHHFBGHHJH; __tins__19534235=%7B%22sid%22%3A%201580873323093%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201580875123093%7D; __51laig__=38");
//        request.addHeader("Host","wwww.kuyunzy1.com");
//        request.addHeader("Referer","http://www.kuyunzy1.com/");
        CloseableHttpResponse response = null;
        try {


            response = client.execute(request);
            statusCode = response.getStatusLine().getStatusCode();


            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
//            content = EntityUtils.toString(entity, "gb2312");//kuyun

            page.setStatusCode(statusCode);
            page.setContent(content);
        } catch (HttpHostConnectException e) {
//                System.err.println(sdf.format(System.currentTimeMillis()) + " socket连接错误："+e.getMessage());
        } catch (ClientProtocolException e) {
//                System.err.println(sdf.format(System.currentTimeMillis()) + " 客户端协议异常"+e.getMessage());
        } catch (IOException e) {
//                System.err.println(sdf.format(System.currentTimeMillis()) + " IO异常"+e.getMessage());
        } finally {
            request.releaseConnection();
            try {
                assert response != null;
                response.close();
            } catch (IOException e) {
                System.err.println(sdf.format(System.currentTimeMillis()) + " response关闭异常：" + e.getMessage());
//                    e.printStackTrace();
            } finally {
                return page;
            }
        }


    }


    //    @Autowired
//    static MovieMapper movieMapper;
    public static void main(String[] args) throws UnsupportedEncodingException {


        CommonPageGet commonPageGet = new CommonPageGet();

        /*酷云资源测试*/

        String url = "http://www.kuyunzy1.com/detail/?9904.html";
//        String url = "http://www.kuyunzy1.com/list/?0-2.html";
//        String url = "http://www.zuidazy2.com/?m=vod-index-pg-1.html";

        int j = 0;
        while (true) {
            KuYunMovieListProcessServiceImpl movieListProcessService = new KuYunMovieListProcessServiceImpl();

            Page page;
            int i = 0;
            while (true) {
                i++;
                page = commonPageGet.download(url);
                if (movieListProcessService.judgmentPageDownSuccess(page)) {
                    System.err.println("成功" + i);
                    break;
                }

                System.err.println("失败" + i);


            }


            Movie movie = movieListProcessService.processMovie(page);
            System.err.println(j++);
            System.err.println(movie);
        }


    }

    @Test
    public void OkTest() {

//        while (true)
        {
            CommonPageGet commonPageGet = new CommonPageGet();
            /*酷云资源测试*/

//        String url = "http://www.kuyunzy1.com/detail/?40311.html";
            String url = "http://47.98.53.232:8088/";
            commonPageGet.download(url);
        }

    }


}
