package baidubody;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/11/7 17:47
 */

import baidubody.utils.Base64Util;
import baidubody.utils.FileUtil;
import baidubody.utils.HttpUtil;

import java.net.URLEncoder;
import java.time.LocalDateTime;

/**
 * 人体关键点识别
 */
public class BodyAnalysis {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static final String filePath = "C:\\Users\\asus\\Desktop\\linux\\mine\\素材\\健身图片\\u=2974616009,1786963050&fm=26&gp=0.jpg";


    public static void main(String[] args) throws Exception {
        System.out.println("开始："+ LocalDateTime.now().getSecond());
        String accessToken = getAccessToken("client_credentials","G8WlReiK90dK9r2us6DM1mWV","7QHhZ5lKns9MxvUh4e30HbPZMBs3gRt4");
        System.out.println(accessToken);
        for (int i = 0; i < 1; i++) {
            String result = BodyAnalysis.body_analysis(filePath,accessToken);
            System.out.println(result);
        }
        System.out.println("结束："+ LocalDateTime.now().getSecond());
    }



    public static String body_analysis(String filePath,String accessToken) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_analysis";

        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String oauthUrl = "https://aip.baidubce.com/oauth/2.0/token";

    public static String getAccessToken(String grant_type,String client_id,String client_secret) throws Exception {

        String param ="grant_type="+grant_type+"&client_id="+client_id+"&client_secret="+client_secret;
        return HttpUtil.sendGet(oauthUrl,param).getString("access_token");
    }


}
