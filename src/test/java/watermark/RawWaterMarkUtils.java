package watermark;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import gaode.MapUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 5450fontSize9fontSize@qq.com
 * @date 2019/5/5 10:14
 */
public class RawWaterMarkUtils {

    static void toFile1(BufferedImage bufferedImage) throws IOException {

        OutputStream outImgStream = new FileOutputStream("C:\\Users\\asus\\Desktop\\生成的.jpg");
        ImageIO.write(bufferedImage, "jpg", outImgStream);
        if(outImgStream != null){
            outImgStream.flush();
            outImgStream.close();
        }
    }



    @Test
    public void testTTTTTTT() throws Exception {
        //原图片添加水印字节流
        BufferedImage bufferedImage1 = ImageIO.read(new File("C:\\Users\\asus\\Desktop\\111.jpg"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage1,"jpg",out);
        byte[] data1 = out.toByteArray();
        String text = "$$$$$time=2019-08-08 12:12:12&location=北京创新中心北京创新中心&coordinates="+"#####";
        byte[] data2 = text.getBytes();
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);



        // 带水印的字节流转图片
        File file = new File("C:\\Users\\asus\\Desktop\\临时.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data3,0,data3.length);
        fos.flush();
        fos.close();

        // 前端对方生成的带有所有信息的图片
        File file1 = new File("C:\\Users\\asus\\Desktop\\bdbce93e18f6488a.jpg");
        // 将带有水印信息的图片转成字节流
        byte[] buffer = null;
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        //bos.flush();
        fis.close();
        bos.close();
        buffer = bos.toByteArray();


        // 打水印方法
        byte[] waterMarkedData = drawWaterMark(buffer);



        RawWaterMarkUtils.toFile2(waterMarkedData);
    }

    private static void toFile2(byte[] bytes) throws IOException {
        System.out.println(bytes.length);
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\asus\\Desktop\\处理生成.jpg"));
        fos.write(bytes);
        fos.close();
    }


    /**
     * 处理水印
     * @param srcBytes
     * @return bytes 如果为空，则处理异常
     */
    public byte[] drawWaterMark(byte[] srcBytes){
        byte[] result;
        ByteArrayInputStream srcImgBais=null;// 原图片输入流
        FileInputStream watermarkImgFis=null;// 水印图片输入流
        ByteArrayOutputStream resultBaos=null;// 打水印后的图片输出流
        try {
            /*处理水印相关数据*/
            //判断图片长度是否满足要求
            if (srcBytes==null||srcBytes.length<10){//&&&&& #####
                return srcBytes;
            }
            //判断最后5个字节是否是#####
            if(!endWith(srcBytes,"#####")){
                return srcBytes;
            }
            // 截取字符串
            int index = lastIndexOf(srcBytes,"$$$$$");
            int appendLength = srcBytes.length-index-10;
            byte[] image = new byte[index];
            byte[] append = new byte[appendLength];
            // 提取字节
            System.arraycopy(srcBytes,0,image,0,index);
            System.arraycopy(srcBytes,index+5,append,0,appendLength);
            // 提取字符串
            String appendStr = new String(append);
            String[] messages = appendStr.split("&");
            //将数据放入map
            Map<String,String> map = new HashMap<>();
            for (String msg:
                    messages) {
                String[] splits = msg.split("=");
                if (splits.length==2){
                    map.put(splits[0],splits[1]);
                }else {
                    map.put(splits[0],"");
                }
            }
            String time = map.get("time")==null?"":map.get("time");

            String location = map.get("location")==null?"":map.get("location");

            if (StringUtils.isEmpty(location)){// 如果是空，则获取coordinates
                String coordinates = map.get("coordinates");
                if (!StringUtils.isEmpty(coordinates)){// 如果不是空，则通过坐标计算位置信息
                    // 获取坐标信息 经度x维度---将"x"替换成 ","
                    location=MapUtils.getLocationAddr(coordinates.replace("x",","));
                }
            }
            if (StringUtils.isEmpty(time)&&StringUtils.isEmpty(location)){
                // TODO 打log
                return null;
            }

            /*正式添加水印*/
            srcImgBais = new ByteArrayInputStream(image);// 读取原始图片字节流
            Image srcImg = ImageIO.read(srcImgBais);// 读进来
            // 宽、高
            int srcWidth = srcImg.getWidth(null);
            int srcHeight = srcImg.getHeight(null);
            // 获取原图片图形数据
            BufferedImage bufImg = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcWidth, srcHeight, null);
            // 水印图片数据 TODO 水印地址 config获取
            String watermarkImgPath = "C:\\Users\\asus\\Desktop\\组2.png";

            watermarkImgFis = new FileInputStream(watermarkImgPath);
            //BufferedImage watermarkImage = ImageIO.read(watermarkImgFis);
            BufferedImage watermarkImage = ImageIO.read(getClass().getClassLoader().getResource("./resources/组2.png"));
            // 水印图片缩放
            watermarkImage = zoomImg(watermarkImage,srcWidth);// 根据源宽度进行

            // 地点文字数据
            int locationNum = location.length();
            //地点水印对象--默认3行--包括时间水印
            WatermarkObj simpleImgObj = new WatermarkObj(srcWidth,srcHeight,watermarkImage.getWidth(),watermarkImage.getHeight(),3,locationNum);
            // 必须适配，调整字体大小和行数
            simpleImgObj.resize();
            // 地点水印格式数据
            int imgY = simpleImgObj.getImgY();
            int textX = simpleImgObj.getTextX();
            int imgX = simpleImgObj.getImgX();
            int fontSize = simpleImgObj.getFontSize();
            int singleHeight = simpleImgObj.getSingleHeight();// 一行高度
            int locationRowNum = simpleImgObj.getLocationRowNum();
            int rowSize = simpleImgObj.getRowSize();
            int textY = simpleImgObj.getTextY();//需要+上字的大小？
            // 图片水印
            this.drawImg(g,watermarkImage,1f,imgX,imgY);
            // 文字水印--需要先抗锯齿
            // 抗锯齿--为什么不封装，因为会打多次文字
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 时间水印阴影
            this.drawText(g,time,new Font("黑体",Font.PLAIN,fontSize),Color.gray,textX+1,textY+1);
            // 时间水印
            this.drawText(g,time,new Font("黑体",Font.PLAIN,fontSize),Color.white,textX,textY);
            // 地点水印处理
            if (!StringUtils.isEmpty(location)){// 如果有地点数据
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < locationRowNum; i++) {// 如何居中？
                    int endIndex = (rowSize*(i+1)>=locationNum)?locationNum:(i+1)*rowSize;// 字数是否已经超过
                    sb.append(location.substring(i*rowSize,endIndex));
                    // 地点水印阴影
                    this.drawText(g,sb.toString(),new Font("黑体",Font.PLAIN,fontSize),Color.gray,textX+1,textY+(i+1)*singleHeight+1);
                    // 地点水印
                    this.drawText(g,sb.toString(),new Font("黑体",Font.PLAIN,fontSize),Color.white,textX,textY+(i+1)*singleHeight);
                    sb.setLength(0);
                    if (endIndex==locationNum){break;}
                }
            }
            //改变底色变红
            BufferedImage newBufferedImage = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufImg, 0, 0, Color.WHITE, null);
            // 转成字节数组
            resultBaos = new ByteArrayOutputStream();
            ImageIO.write(newBufferedImage,"jpg",resultBaos);
            resultBaos.flush();
            result = resultBaos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO log
            return null;
        } finally {
            // 关闭流
            try {
                if (srcImgBais!=null){
                    srcImgBais.close();
                }
                if (watermarkImgFis!=null){
                    watermarkImgFis.close();
                }
                if (resultBaos!=null){
                    resultBaos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean endWith(byte[] bytes,String endStr){
        byte[] strBytes = endStr.getBytes();
        int length = bytes.length;
        int strByteLength = strBytes.length;
        if (length<strByteLength){return false;}
        int i = length-1;
        for (int j = 0; j < strByteLength; j++) {//如果是等于 $ 则判断前5个
            if (bytes[i]!=strBytes[strByteLength-1-j]){// 如果一一对应
                return false;
            }
            i--;
        }
        return true;
    }

    public int lastIndexOf(byte[] bytes,String lastStr){
        byte[] strBytes = lastStr.getBytes();
        int strLength = strBytes.length;
        for (int i = bytes.length-1; i >=0; i--) {
            int count = 0;
            if (bytes[i]==strBytes[strLength-1]){//36 对应 $
                for (int j = 0; j < strLength; j++) {//如果是等于 $ 则判断前5个
                    if (bytes[i]==strBytes[strLength-1-j]){// 如果一一对应
                        i--;
                        count++;
                    }else {
                        count=0;//置为0，重新开始
                        break;
                    }
                }
            }
            if (count==strLength){
                return i+1;// 因为当前 i是数组下标，而不是长度下标，比如如果a[0],运行后则变成 i=-1，显然不对
            }
        }
        return -1;
    }

    /**
     * 缩放水印比例，使得最终水印宽度=源图片宽度*0.4
     * @param watermarkImage
     * @param srcWidth
     * @return
     */
    private BufferedImage zoomImg(BufferedImage watermarkImage,int srcWidth){
        float srcW = srcWidth;
        int imgWidth = watermarkImage.getWidth();
        int imgHeight = watermarkImage.getHeight();
        float times = (float) (0.4*srcW/imgWidth);// 方法倍数
        System.out.println("放大倍数=======》"+times);
        int lastW = (int) (imgWidth*times);
        int lastH = (int) (imgHeight*times);
        BufferedImage newImage = new BufferedImage(lastW, lastH, watermarkImage
                .getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(watermarkImage, 0, 0, lastW, lastH,null);
        g.dispose();
        return newImage;
    }
    /**
     * 画图片水印
     * @param g
     * @param watermarkImage
     * @param clarity
     * @param imgX
     * @param imgY
     */
    public void drawImg(Graphics2D g,BufferedImage watermarkImage,float clarity, int imgX,int imgY){

        ImageIcon imgIcon = new ImageIcon(watermarkImage);
        Image con = imgIcon.getImage();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));//透明度
        g.drawImage(con, imgX, imgY, null);//水印的位置
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    /**
     * 文字水印
     * @param g
     * @param text
     * @param font
     * @param color
     * @param textX
     * @param textY
     */
    public void drawText(Graphics2D g,String text,Font font,Color color,int textX,int textY){
        //设置水印颜色
        g.setColor(color);
        g.setFont(font);
        // 抗锯齿 调用方法前先处理，因为可能涉及到多个数据
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(text, textX, textY);
    }


/*================未经处理代码=================*/

    /**
     * 判断图片是否大于目标尺寸
     *
     * @param srcPath
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static boolean isBigImage(String srcPath, int maxWidth, int maxHeight) {
        BufferedImage bufferedImage = null;
        try {
            File of = new File(srcPath);
            if (of.canRead()) {
                bufferedImage = ImageIO.read(of);
            }
        } catch (Exception e) {
            return false;
        }
        if (bufferedImage != null) {
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width > maxWidth && height > maxHeight) {
                return true;
            }
        }
        return false;
    }

    /**
     * 图片放大的方法（不会变色）
     * @param inputUrl 图片输入路劲
     * @param outputUrl 图片输出路劲
     * @param maxWidth 目标宽
     * @param maxHeight 目标高
     * @param proportion 是否等比缩放
     * @return
     */
    public static boolean zoomPicture(String inputUrl, String outputUrl,
                                      int maxWidth, int maxHeight, boolean proportion) {
        try {
            // 获得源文件
            File file = new File(inputUrl);
            if (!file.exists()) {
                return false;
            }
            Image img = ImageIO.read(file);
            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                return false;
            } else {
                int newWidth;
                int newHeight;
                // 判断是否是等比缩放
                if (proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null))
                            / (double) maxWidth;
                    double rate2 = ((double) img.getHeight(null))
                            / (double) maxHeight;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate2 : rate1;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = maxWidth; // 输出的图片宽度
                    newHeight = maxHeight; // 输出的图片高度
                }
                BufferedImage tag = new BufferedImage((int) newWidth,
                        (int) newHeight, BufferedImage.TYPE_INT_RGB);

                tag.getGraphics().drawImage(
                        img.getScaledInstance(newWidth, newHeight,
                                Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(outputUrl);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 对图片进行放大（部分图片会变红）
     * @param srcPath
     *            原始图片路径(绝对路径)
     * @param newPath
     *            放大后图片路径（绝对路径）
    times     * @param
     *            放大倍数
     * @return 是否放大成功
     */

    public static boolean zoomInImage(String srcPath, String newPath,
                                      int maxWidth, int maxHeight) {
        BufferedImage bufferedImage = null;
        try {
            File of = new File(srcPath);
            if (of.canRead()) {
                bufferedImage = ImageIO.read(of);
            }
        } catch (IOException e) {
            // TODO: 打印日志
            return false;
        }
        if (bufferedImage != null) {
            bufferedImage = zoomInImage(bufferedImage, maxWidth, maxHeight);
            try {
                // TODO: 这个保存路径需要配置下子好一点
                ImageIO.write(bufferedImage, "JPG", new File(newPath)); // 保存修改后的图像,全部保存为JPG格式
            } catch (IOException e) {
                // TODO 打印错误信息
                return false;
            }
        }
        return true;
    }

    /**
     * 对图片进行放大
     *
     * @param originalImage
     *            原始图片
     * @param maxWidth
     *            目标宽度
     * @param maxHeight
     *            目标高度
     * @return
     */
    private static BufferedImage zoomInImage(BufferedImage originalImage,
                                             int maxWidth, int maxHeight) {
        int times = 1; // 放大倍数
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        double sw = (maxWidth * 1.0) / (width * 1.0);
        double sh = (maxHeight * 1.0) / (height * 1.0);
        if (width > maxWidth && height > maxHeight) {
            return originalImage;
        } else if (width < maxWidth && height < maxHeight) {
            if (sw > sh) {
                times = (int) (sw + 0.8);
            } else {
                times = (int) (sh + 0.8);
            }
        } else if (width < maxWidth && height > maxHeight) {
            times = (int) (sw + 0.8);
        } else {
            times = (int) (sh + 0.8);
        }
        int lastW = times * width;
        int lastH = times * height;
        BufferedImage newImage = new BufferedImage(lastW, lastH, originalImage
                .getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, lastW, lastH, null);
        g.dispose();
        return newImage;
    }


}
