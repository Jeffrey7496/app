package com;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/9 16:36
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        BufferedImage watermarkImage = ImageIO.read(MyTest.class.getClassLoader().getResource("/src/main/resources/组2.png"));
    }
}
