package gaode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.LinkedList;
import java.util.List;


/**
 * 公共路线接口方法，计算实际距离。
 */
public class MapUtils {

    private static final String HOSTADDR = "http://restapi.amap.com/v3/geocode/regeo";
    private static final String KEY = "196d2741e3b0430dabd6980637a96d81";// 这个是什么


    public static String getLocationAddr(String location){
        List<NameValuePair> list = new LinkedList<>();
        list.add(new BasicNameValuePair("key", KEY));
        list.add(new BasicNameValuePair("output", "JSON"));
        list.add(new BasicNameValuePair("location", location));
        String responseEntity = null;
        String addr="";
        try {
            HttpGet httpGet = new HttpGet(new URIBuilder(HOSTADDR).setParameters(list).build());
            httpGet.setConfig(RequestConfig.custom().setConnectTimeout(2000).build());
            HttpResponse response = HttpClients.createDefault().execute(httpGet);
            responseEntity = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject jsonObject=JSON.parseObject(responseEntity);
            addr=jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent")
                    .getJSONObject("building").getString("name");
            String country = jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent")
                    .getString("country");
            addr=jsonObject.getJSONObject("regeocode").getString("formatted_address");
            if (!country.equals("中国")){//外国需要加上
                addr=country+addr;
            }
        } catch (Exception e) {
            if (e instanceof ConnectTimeoutException) {
                System.out.println("-------请求超时-------");
            } else {
                e.printStackTrace();
            }
        }
        return addr;
    }



    public static void main(String[] args) {
        String addr = getLocationAddr("116.4127850533,39.9014114686");

        System.out.println(addr);
    }
}