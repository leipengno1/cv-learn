package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * 动画效果
 *   分类: android
 * @author      : 梁山广
 * @date        : 2017/11/18 18:08
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson10Animation extends JComponent implements ActionListener {

    /**
     * 小球运动的点
     */
    private int x;
    private int y;
    /**
     * 定时变化的透明度
     */
    private float alpha;

    private JButton btnDraw;

    public Lesson10Animation() {
        this.x = 0;
        this.y = 0;
        this.alpha = 1.0f;
    }

    public JButton getButton() {
        btnDraw = new JButton("Process");
        btnDraw.addActionListener(this);
        return btnDraw;
    }

    /**
     * 按钮的处理事件
     */
    void process() {
        System.out.println("小球开始运动啦!!");
        Thread thread = new Thread(() -> {
            while (true) {
                x += 10;
                y += 20;
                if (x > getWidth() - 30) {
                    x = (int) (Math.random() * 300);
                }
                if (y > getHeight() - 50) {
                    y = (int) (Math.random() * 300);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                alpha = (float) Math.random();
                repaint();
            }
        });
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        // 设置透明度
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(ac);
        g2d.setPaint(Color.GREEN);
        g2d.fillOval(x, y, 50, 50);
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Lesson10Animation imp = new Lesson10Animation();
        frame.getContentPane().add(imp, BorderLayout.CENTER);
        frame.add(imp.getButton(), BorderLayout.SOUTH);
        frame.setSize(400, 420);
        frame.setTitle("演示");
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDraw) {
            this.process();
        }
    }
}
