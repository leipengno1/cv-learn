package com.huawei.l00379880.middle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * @Description : 显示图片的面板
 * @author      : 梁山广
 * @date        : 2017/11/18 15:54
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson02ShowImage extends JComponent {
    public Lesson02ShowImage(BufferedImage image) {
        this.image = image;
    }

    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) {
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    public static void main(String[] args) throws IOException {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        File f = new File(rootPath + "lena.png");
        BufferedImage image = ImageIO.read(f);
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片显示", image);
    }
}
