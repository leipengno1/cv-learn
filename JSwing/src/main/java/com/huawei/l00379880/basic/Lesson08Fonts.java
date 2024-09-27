package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 文字与水印合成
 * @author      : 梁山广
 * @date        : 2017/11/17 20:31
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson08Fonts extends JComponent {
    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (image == null) {
            return;
        }
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void drawFonts() {
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (Font font : genv.getAllFonts()) {
            System.out.println(font.getFontName());
        }
        g2d.setPaint(Color.magenta);
        // 设置字体的大小与样式.第二个参数为字体的样式
        Font font = new Font("楷体", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.drawString("字体测试", this.getWidth() / 2 - 50, this.getHeight() / 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("显示不同的字体");
        Lesson08Fonts mycanvas = new Lesson08Fonts();
        JButton drawBtn = new JButton("绘图");
        drawBtn.addActionListener(e -> {
            mycanvas.drawFonts();
            mycanvas.repaint();
        });
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(mycanvas, BorderLayout.CENTER);
        frame.getContentPane().add(drawBtn, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
