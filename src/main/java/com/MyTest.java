package com;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 简单测试
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/7/9 16:36
 */
public class MyTest {
    public static void main(String[] args) throws IOException {
        int width = 100;
        int height = 100;
        String s1 = "时段";


        File file = new File("C:\\Users\\asus\\Desktop\\ds.jpg");

        //Font font = new Font("黑体", Font.BOLD, 10);
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.RED);

        FontRenderContext context = g2.getFontRenderContext();
        //Rectangle2D bounds = font.getStringBounds(s1 , context);
        //double x = (width - bounds.getWidth()) / 2;
        //double y = (height - bounds.getHeight()) / 2;
        //double ascent = -bounds.getY();
        //double baseY = y + ascent;

        g2.drawString(s1, 1, 1);

        ImageIO.write(bi, "jpg", file);
    }

}
