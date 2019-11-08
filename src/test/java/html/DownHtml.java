package html;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonUtil;
import utils.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/22 9:19
 */
public class DownHtml {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownHtml.class);

    public static void main(String[] args) throws IOException {
        String url = "https://mp.weixin.qq.com/s/EjRduQyEquGLQBn_c0G0dQ";
        DownHtml downHtml = new DownHtml();
        System.out.println(downHtml.dlImgInTurnUrlHtml(url));
    }

    private String dlImgInTurnUrlHtml(String originalUrl) throws IOException {
        if (originalUrl.contains("taikang.com")){
            return "";// ÄÚÍø²»ÓÃ´¦Àí
        }
        JSONObject dImgCount = new JSONObject();//ÐèÒªÏÂÔØµÄÍ¼Æ¬×ÜÊý
        dImgCount.put("count",0);
        List<String> imgTempList = new ArrayList<>();//ÏÂÔØ³É¹¦µÄÍ¼Æ¬Á´½Ó
        Document doc = Jsoup.connect(originalUrl).get();
        doc.select("img").stream().forEach(imgTag->{
            String imgUrlTemp = imgTag.attr("data-src");
            if(!org.springframework.util.StringUtils.isEmpty(imgUrlTemp)){
                LOGGER.info("=======data-src=========" + imgUrlTemp);
                String outerImgUrl = dowLoadImg(imgUrlTemp, imgTempList, dImgCount);
                if(!org.springframework.util.StringUtils.isEmpty(outerImgUrl)){
                    imgTag.attr("data-src", outerImgUrl);
                    imgTag.attr("src", outerImgUrl);
                }
            }
        });

        String docHtmlSrc = doc.html();
        String docHtmlTemp = docHtmlSrc + "";
        while(!org.springframework.util.StringUtils.isEmpty(docHtmlTemp) && docHtmlTemp.contains("url(")){
            docHtmlTemp = docHtmlTemp.substring(docHtmlTemp.indexOf("url(") + 4);
            String bgUrl = docHtmlTemp.substring(0,docHtmlTemp.indexOf(")"));
            if(!org.springframework.util.StringUtils.isEmpty(bgUrl)){
                LOGGER.info("=======style=========" + bgUrl);
                String outerImgUrl = dowLoadImg(bgUrl, imgTempList, dImgCount);
                LOGGER.info("========·ÃÎÊÁ´½Ó========" + outerImgUrl);
                if(!org.springframework.util.StringUtils.isEmpty(outerImgUrl)){
                    docHtmlSrc = docHtmlSrc.replace(bgUrl, "&quot;" + outerImgUrl + "&quot;");
                }
            }
            docHtmlTemp = docHtmlTemp.substring(docHtmlTemp.indexOf(")") + 1);
        }
        doc.html(docHtmlSrc);
        LOGGER.debug("=========ÏÂÔØ³É¹¦Í¼Æ¬==========" + imgTempList);
        LOGGER.debug("=========Í¼Æ¬×Ü¸öÊý============" + dImgCount.getIntValue("count"));
        if(imgTempList.size() == dImgCount.getIntValue("count")){//Í¼Æ¬È«²¿ÏÂÔØ³É¹¦
            // Ö±½ÓÉú³ÉHTML
            return generateHtmlFile(EmojiParser.removeAllEmojis(doc.html()));
        }else{
            for (String tempPath : imgTempList) {
                File tempFile = new File(tempPath);
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        return "";
    }
    private String saveImgPath = "D://home/nginx/share_files/isale_msg_img/";
    private String saveHtmlPath = "D://home/nginx/share_files/isale_msg_html/";
    private String outerVisitUrl = "";
    private String generateHtmlFile(String html) throws IOException {
        FileOutputStream fos = null;
        String dateStr = CommonUtil.getCurrentTimeByDayWithNoPattern();
        String uuidName = CommonUtil.getUUID();
        //  20190909/uuid.html
        StringBuilder savePath = new StringBuilder(saveHtmlPath).append(dateStr);
        File fileTemp = new File(savePath.toString());
        if (!fileTemp.exists() && !fileTemp.isDirectory()) {
            fileTemp.mkdirs();
        }

        fos = new FileOutputStream(savePath.append("/").append(uuidName).append(".html").toString());
        fos.write(html.getBytes());
        if (fos != null) {
            fos.close();
        }

        return  new StringBuilder(outerVisitUrl).append("/isale_msg_html/").append(dateStr)
                .append("/").append(uuidName).append(".html").toString();
    }

    /**
     * ´¦ÀíÔ­Í¼Æ¬Â·¾¶£¬·µ»Ø·ÃÎÊµÄurl
     * @param imgUrlTemp -- Ô­Í¼Æ¬µØÖ·
     * @param imgTempList --¼ÇÂ¼´¦Àí³É¹¦µÄÍ¼Æ¬µÄ¸öÊý
     * @param dImgCount -- ¼ÇÂ¼´¦ÀíµÄÍ¼Æ¬µÄ¸öÊý
     * @return Íâ½çÍ¼Æ¬·ÃÎÊµØÖ·
     */
    private String dowLoadImg(String imgUrlTemp, List<String> imgTempList, JSONObject dImgCount){
        if(imgUrlTemp != null && (imgUrlTemp.startsWith("http") || imgUrlTemp.startsWith("&quot;http"))){
            if(imgUrlTemp.contains("&quot;")){
                imgUrlTemp = imgUrlTemp.replaceAll("&quot;", "");
            }
            if (true){
                return imgUrlTemp;
            }
            // ´¦ÀíÍ¼Æ¬+1
            dImgCount.put("count",dImgCount.getIntValue("count") + 1);
            byte[] imgByte = HttpRequest.sendGetForByte(imgUrlTemp, null);
            if (imgByte != null) {
                String fileType = ".png";
                if(imgUrlTemp.indexOf("wx_fmt=") >0){
                    fileType = "." + imgUrlTemp.split("wx_fmt=")[1];
                    if(fileType.indexOf("?") > 0){
                        fileType = fileType.split("\\?")[0];
                    }
                } else {
                    String fileTypeTemp = imgUrlTemp.substring(imgUrlTemp.lastIndexOf("."), imgUrlTemp.length());
                    if(fileTypeTemp.length() < 8){
                        fileType = fileTypeTemp;
                    }
                }
                FileOutputStream fos = null;
                try {
                    String dateStr = CommonUtil.getCurrentTimeByDayWithNoPattern();
                    String uuidName = CommonUtil.getUUID();
                    StringBuilder savePath = new StringBuilder(saveImgPath).append(dateStr).append("/");
                    //¼ìÑéÎÄ¼þ¼ÐÊÇ·ñ´æÔÚ
                    File fileTemp = new File(savePath.toString());
                    if (!fileTemp.exists() && !fileTemp.isDirectory()) {
                        fileTemp.mkdirs();
                    }
                    //  /home/nginx/share_files/isale_msg_img/20190909/uuid.png
                    String tempPath = savePath.append(uuidName).append(fileType).toString();
                    fos = new FileOutputStream(new File(tempPath));
                    fos.write(imgByte);// ´æ´¢Í¼Æ¬
                    imgTempList.add(tempPath);
                    if (fos != null) {
                        fos.close();
                    }
                    /*if(imgByte.length > 1048576 && !"gif".equalsIgnoreCase(tempPath.substring(tempPath.lastIndexOf(".") + 1))){//´óÓÚ1M£¬½øÐÐÑ¹Ëõ
                        CompressPicUtil.compressPictureByQality(new File(tempPath), 0.5f);
                    }*/
                    StringBuilder visitUrl = new StringBuilder(outerVisitUrl).append("/isale_msg_img/").append(dateStr)
                            .append("/").append(uuidName).append(fileType);
                    return tempPath;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

}
