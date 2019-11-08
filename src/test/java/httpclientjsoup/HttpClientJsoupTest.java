package httpclientjsoup;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.util.List;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/24 14:26
 */
public class HttpClientJsoupTest {
    @Test
    public void test() {
        //通过httpClient获取网页响应,将返回的响应解析为纯文本
        HttpGet httpGet = new HttpGet("https://www.jfdaily.com/news/detail?id=178134");
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build());
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;

        String responseStr = "";
        try {
            httpClient = HttpClientBuilder.create().build();
            HttpClientContext context = HttpClientContext.create();
            response = httpClient.execute(httpGet, context);
            int state = response.getStatusLine().getStatusCode();
            if (state != 200)
                responseStr = "";
            HttpEntity entity = response.getEntity();
            if (entity != null)
                responseStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpClient != null)
                    httpClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (responseStr == null)
            return;

        //将解析到的纯文本用Jsoup工具转换成Document文档并进行操作
        Document document = Jsoup.parse(responseStr);
        List<Element> elements = document.getElementsByAttributeValue("class", "phdnews_txt fr").first()
                .getElementsByAttributeValue("class", "phdnews_hdline");
        elements.forEach(element -> {
            for (Element e : element.getElementsByTag("a")) {
                System.out.println(e.attr("href"));
                System.out.println(e.text());
            }
        });
    }
}
