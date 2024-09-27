package com.huawei.l00379880.middle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 图像的显示类.仅能显示BufferedImage,
 *                OpenCV那边的ImageUI类既能显示Mat,又能显示BufferedImage
 * @author      : 梁山广
 * @date        : 2017/11/19 21:01
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
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

    public void imshow(String title, BufferedImage image) {
        this.image = image;
        JDialog ui = new JDialog();
        ui.setTitle(title);
        ui.getContentPane().setLayout(new BorderLayout());
        ui.getContentPane().add(this, BorderLayout.CENTER);
        ui.setSize(image.getWidth() + 16, image.getHeight() + 38);
        ui.setVisible(true);
        repaint();
    }
}
