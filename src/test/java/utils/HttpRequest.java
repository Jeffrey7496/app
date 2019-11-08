package utils;

import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/22 9:31
 */
public class HttpRequest {
    public static byte[] sendGetForByte(String url,String param) {
        try {
            HttpURLConnection connection = null;
            String smsUrl = url;
            if (!StringUtils.isEmpty(param)){
                smsUrl+="?"+param;
            }

            URL url1 = new URL(smsUrl);
            connection = (HttpURLConnection) url1.openConnection();
            connection.connect();
            // 图片处理
            return getImgBytes(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getImgBytes(InputStream inputStream) {
        try {
            byte[] bytes = new byte[1024];
            int len =0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(bytes))!=-1){
                baos.write(bytes,0,len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
