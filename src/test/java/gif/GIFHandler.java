package gif;

/**
 * gif处理类
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/30 21:17
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 方法简介
 * 整个工程主要就两个java文件，一个用于解码的GifDecoder类，一个用于编码的AnimatedGifEncoder，另外有两个辅助类复制进来即可。
 * GifDecoder类
 * 主要方法有：
 * public int read(String name)
 * 指定需要解码的GIF图片路径，还有两个重载方法，支持读取InputStream和BufferedInputStream
 * public int getDelay(int n)
 * 获取指定帧的延迟时间。
 * public int getFrameCount()
 * 获取GIF图片的帧数
 * public int getLoopCount()
 * 获取GIF图片的播放次数，0表示无限循环播放
 * public BufferedImage getFrame(int n)
 * 获取指定帧的图像数据
 * AnimatedGifEncoder类
 * 另一个是用于编码的AnimatedGifEncoder类，方法和解码基本相反：
 * public void setDelay(int ms)
 * 设置每帧之间的延迟时间，GifDecoder能获取到任意两帧之间的延迟，而AnimatedGifEncoder貌似没有提供两帧之间的延迟设定，按照同一延迟时间处理。
 * public void setRepeat(int iter)
 * 设置播放次数，0表示无限循环播放
 * public boolean addFrame(BufferedImage im)
 * 添加帧
 * public void setFrameRate(float fps)
 * 设置帧率，与delay作用类似，相对设置delay为Math.round(100f / fps)
 * public boolean finish()
 * 添加帧并配置好之后调用这个方法关闭文件输出流等
 * public boolean start(String file)
 * 设置输出的文件路径，另一个重载方法以流的形式输出
 * 另外AnimatedGifEncoder还提供一些其他方法，包括设置透明度、背景颜色、图片质量、尺寸等。
 * ————————————————
 * 版权声明：本文为CSDN博主「liuconen」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_26918031/article/details/76794854
 */
public class GIFHandler {
    private String baseOutPath = "C:\\Users\\asus\\Desktop\\linux\\mine\\素材\\健身动图\\处理后";
    //存放解码出来的所有帧
    private BufferedImage[] bi;
    //延迟时间
    private int delay;

    public static void main(String[] args) throws IOException {
        GIFHandler handler = new GIFHandler();
        handler.start();
    }

    private void start() {
        //"D:/p/1",要转换的GIF图片目录
        File gifPath = new File("C:\\Users\\asus\\Desktop\\linux\\mine\\素材\\健身动图\\");
        File[] gifs = null;
        //读取出所有GIF图片
        if (gifPath.isDirectory()) {
            gifs = gifPath.listFiles();
        }
        if (gifs != null && gifs.length != 0) {
            for (File f : gifs) {
                trans(f);
                System.out.println(f.getName());
            }
        }
        System.out.println("转换完成");
    }

    /**
     *  转换
     */
    private void trans(File transFile) {
        decode(transFile);
        encode(baseOutPath + transFile.getName());
    }

    /**
     * 编码
     * @param outPath
     */
    private void encode(String outPath) {
        int total = 50;
        int fen = 10;
        int eachSize = total/fen;

        // 50帧以内
        int len = bi.length;
        System.out.println("帧数"+len);
        // 分成10份，每个里面抽出5个来
        if (len>total){
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            // 设置循环模式，0为无限循环 这里没有使用源文件的播放次数
            encoder.setRepeat(0);
            // 设置输出路径
            encoder.start(outPath);
            int x = len/fen; // 平均分成10份，然后在10份 均匀选出5个数
            int[] arr = new int[eachSize];// 每段5张
            double y = x/eachSize;//
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) Math.round(y*(i+1));
            }
            for (int i = 0; i <fen; i++) {// 10份
                for (int j = 0; j < eachSize; j++) {
                    encoder.setDelay(delay);
                    encoder.addFrame(bi[i*x+arr[j]]);
                }
            }
            encoder.finish();
        }else {// 全部打出-- 不操作
            /*for (int i = 0; i <len; i++) {// 10份
                for (int j = 0; j < eachSize; j++) {
                    encoder.setDelay(delay);
                    encoder.addFrame(bi[i]);
                }
            }*/
        }

    }

    /**
     * 解码
     * @param f
     */
    private void decode(File f) {
        GifDecoder decoder = new GifDecoder();
        try {
            decoder.read(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 获取到帧数
        int frameCount = decoder.getFrameCount();
        bi = new BufferedImage[frameCount];
        // 获取到每一帧的数据保存到bi
        for (int i = 0; i < frameCount; i++) {
            bi[i] = decoder.getFrame(i);
        }
        // 获取到每帧之间的延迟时间，这里只取第一帧的
        delay = decoder.getDelay(0);
        // ImageIO.write(frame, "jpeg", out); 该方法用于输出分解得到的单个图片文件
    }
}
