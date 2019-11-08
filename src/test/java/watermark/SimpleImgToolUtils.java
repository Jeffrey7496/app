package watermark;

import clive.hua.app.simpleImageTool.SimpleImageTool;
import clive.hua.app.simpleImageTool.exception.MyImageException;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 基于SimpleImageTool的图片处理工具
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/23 13:01
 */
public class SimpleImgToolUtils {
    String srcDir = "C:\\Users\\asus\\Desktop\\linux\\mine\\素材\\健身动图";
    int targetSize = 450*1024;

    @Test
    public void testImg(){
        try {
            changeManyImgsSize(srcDir,targetSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testT(){
        String srcFileName = "1timg1.gif";
        try {
            File  file = new File(srcDir+"\\"+srcFileName);

            BufferedImage bufferedImage = ImageIO.read(getInputStream(srcDir+"\\"+srcFileName));
            bufferedImage = SimpleImageTool.of(srcDir+"\\"+srcFileName)
                    .size(bufferedImage.getWidth()/2,bufferedImage.getHeight()/2)// 最好的调节文件大小的方式
                    .toBufferedImage(); //gif png tiff jpg等多种格式
            toFile1(bufferedImage,srcDir+"\\"+"---"+srcFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyImageException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变该文件夹下所有动图
     * @param srcDir
     * @param targetSize
     */
    public void changeManyImgsSize(String srcDir,int targetSize) throws Exception {
        File file  = new File(srcDir);
        for (String name:file.list() ) {
            if (name.contains(".")){
                changeImgSize(srcDir,name,targetSize);
            }
        }
    }

    public void changeImgSize(String srcDir,String srcFileName,int targetSize) throws Exception {
        String filePath = srcDir+"\\"+srcFileName;
        InputStream inputStream = getInputStream(filePath);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int size = getImgSize(inputStream);
        while (size>targetSize){
            bufferedImage = SimpleImageTool.of(inputStream)
                    .size(bufferedImage.getWidth()/2,bufferedImage.getHeight()/2)// 最好的调节文件大小的方式
                    .toBufferedImage(); //gif png tiff jpg等多种格式
            // 重新指针
            inputStream = null;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut;
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufferedImage, "gif",imOut);
            inputStream= new ByteArrayInputStream(bs.toByteArray());
            size = getImgSize(inputStream);
        }
    }

    public void changeImgSize1(String srcDir,String srcFileName,int targetSize) throws Exception {
        int num = 0;
        String filePath = srcDir+"\\"+srcFileName;
        File  file = new File(filePath);
        BufferedImage bufferedImage = ImageIO.read(file);

        while (file.length()>targetSize){
            num++;
            SimpleImageTool.of(filePath)
                    .size(bufferedImage.getWidth()/2,bufferedImage.getHeight()/2)// 最好的调节文件大小的方式
                    .toFile(new File(srcDir+"\\"+num+"-"+srcFileName)); //gif png tiff jpg等多种格式
            if (file.length()>targetSize){
                // / 直接覆盖
                file.delete();
            }
            filePath = srcDir+"\\"+num+srcFileName;
            file = new File(filePath);
            bufferedImage = ImageIO.read(file);
        }
    }

    public InputStream getInputStream(String filePath) throws IOException {
        File file = new File(filePath);
        System.out.println(file.length());
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    /**
     * 获取图片大小--动图会有差别 !!!
     * @param inputStream
     * @return
     */
    public int getImgSize(InputStream inputStream){
        return getFileBytes(inputStream).length;
    }
    public byte[] getFileBytes(InputStream inputStream)  {
        ByteArrayOutputStream outputStream = null;
        try {
            if (inputStream==null){
                return null;
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            outputStream = new ByteArrayOutputStream();
            while ((len=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (outputStream!=null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void toFile1(BufferedImage bufferedImage,String targetFile) throws IOException {

        //改变底色变红
        BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

        OutputStream outImgStream = new FileOutputStream(targetFile);
        ImageIO.write(newBufferedImage, targetFile.substring(targetFile.lastIndexOf(".")+1), outImgStream);
        if(outImgStream != null){
            outImgStream.flush();
            outImgStream.close();
        }
        System.out.println(newBufferedImage.getWidth());
    }
}
