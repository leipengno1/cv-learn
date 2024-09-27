package com.huawei.l00379880.middle;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 通用图像处理程序
 *
 * @author l00379880
 * @time 2017-11-01 14:21
 */
public class ImageUI extends JComponent {
    private BufferedImage image;

    public ImageUI() {
        this.image = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (image == null) {
            graphics2D.setPaint(Color.BLACK);
            graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        } else {
            graphics2D.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    public void imshow(String title, Mat src) {
        this.image = convert2BufferedImage(src);
        JDialog ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(image.getWidth() + 16, image.getHeight() + 38);
        ui.setVisible(true);
        this.repaint();
    }

    public void imshow(String title, BufferedImage image) {
        this.image = image;
        JDialog ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(image.getWidth() + 16, image.getHeight() + 38);
        ui.setVisible(true);
        this.repaint();
    }

    private BufferedImage convert2BufferedImage(Mat src) {
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        // 用于存储图像的所有像素点的工具
        byte[] data = new byte[width * height * dims];
        src.get(0, 0, data);
        int[] pixels = new int[width * height];

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int r, g, b;
        int index = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // 获取该赋值的起始坐标
                index = (row * width + col) * dims;
                if (dims == 3) {
                    b = data[index] & 0xff;
                    g = data[index + 1] & 0xff;
                    r = data[index + 2] & 0xff;
                    pixels[row * width + col] = ((255 & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                } else if (dims == 1) {
                    b = data[index] & 0xff;
                    pixels[row * width + col] = ((255 & 0xff) << 24) | ((b & 0xff) << 16) | ((b & 0xff) << 8) | (b & 0xff);
                }
            }
        }
        bi.getRaster().setDataElements(0, 0, width, height, pixels);
        return bi;
    }
}
