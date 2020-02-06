package com.spider.util.download;

import com.spider.entity.Movie;
import com.spider.entity.Page;
import com.spider.service.impl.KuYunMovieListProcessServiceImpl;
import com.spider.service.impl.OkMovieListProcessServiceImpl;
import com.spider.service.impl.ZdMovieListProcessServiceImpl;
import com.spider.util.impl.CommonPageGet;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author young
 * @create 2020-01-18 16:09
 */

public class PageDownUtil {
    public static String getPageContent(String url) {
        HttpClientBuilder builder = HttpClients.custom();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

        CloseableHttpClient client = builder.build();


        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        request.addHeader("Accept-Encoding","gzip, deflate");
        request.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        request.addHeader("Cache-Control","max-age=0");
        request.addHeader("Connection","keep-alive");
        request.addHeader("Upgrade-Insecure-Requests","1");
//        request.addHeader("Cookie","ASPSESSIONIDQASSQADC=COJJCHGAFLDBAFODPBPFNKDJ; __51cke__=; ASPSESSIONIDSASRSADD=KHPJHCGALNNIOOOIAMDEIEFF; ASPSESSIONIDSATRSACC=JKFKMNFAMOMNAEENGNJKKPKI; ASPSESSIONIDSATQTBDD=FHMBNEGAPHODPNKIEKPLCJHM; ASPSESSIONIDSASTRADC=EJDCCAGANJIEMIBDIHGMPIGE; ASPSESSIONIDSCTSQADC=IJIDIJGAGPODDEKNJGGCKLHG; ASPSESSIONIDQCSSRBCC=LPJLNLGAKHKIDPHLEKGMFJCG; ASPSESSIONIDQASSRBDC=ELDKBEKAHINCPBODOIMCEJOE; ASPSESSIONIDSCQTQACD=JGFJHNKANOPGBPDIDHNABPDI; ASPSESSIONIDQCRRTBDD=HHOKMIKAJKNNAAEABELICPDL; ASPSESSIONIDQAQRSBCD=EPLDNPKAOBAAPLIMPEGGNONK; ASPSESSIONIDQCSTSDAD=BJHKDAMAAGFFJNOAIOOHFIMN; ASPSESSIONIDQATRSACC=KFAOILLALINMNNCJHIHFBONM; ASPSESSIONIDSARRTBCD=ODNBJCMANDHPPBHKKIPIFFMO; ASPSESSIONIDQARRTACC=NGMMJJMAKLDMINNEOIAJFMNK; ASPSESSIONIDQCSSQBCD=AKKLEJBBHEIHBAMHHFBGHHJH; __tins__19534235=%7B%22sid%22%3A%201580873323093%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201580875123093%7D; __51laig__=38");
//        request.addHeader("Host","wwww.kuyunzy1.com");
//        request.addHeader("Referer","http://www.kuyunzy1.com/");
        String content = null;
        try {
            CloseableHttpResponse response = client.execute(request);


            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
//            content = EntityUtils.toString(entity, "gb2312");//kuyun
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.releaseConnection();
        }

        return content;
    }


    //    @Autowired
//    static MovieMapper movieMapper;
    public static void main(String[] args) throws UnsupportedEncodingException {


        CommonPageGet commonPageGet = new CommonPageGet();

        /*酷云资源测试*/

        String url ="http://www.kuyunzy1.com/detail/?9904.html";
//        String url = "http://www.kuyunzy1.com/list/?0-2.html";
//        String url = "http://www.zuidazy2.com/?m=vod-index-pg-1.html";

        int j=0;
       while (true){
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
            //        /*OK资源测试*/
        String url = "http://www.okzyw.com/?m=vod-detail-id-47626.html";


            OkMovieListProcessServiceImpl movieListProcessService = new OkMovieListProcessServiceImpl();

            Page page = commonPageGet.download(url);
            System.err.println(page.getContent());
            Movie movie = movieListProcessService.processMovie(page);
            System.err.println(movie);
        }

    }


}
