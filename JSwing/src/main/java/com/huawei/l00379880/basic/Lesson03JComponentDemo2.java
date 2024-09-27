package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : JComponent画图学习
 *                优点:轻量级的画布,自动双缓冲处理,效率更高,避免闪烁
 *                过程:
 *                    1、先创建一个BufferedImage对象
 *                    2、在BufferedImage对象上绘制
 *                    3、绘制BufferedImage对象到缓存
 * @author      : 梁山广
 * @date        : 2017/11/14 21:29
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson03JComponentDemo2 extends JComponent {
    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        // 向下转换
        Graphics2D g2d = (Graphics2D) g;
        // 反锯齿特效,Graphics2D有,但是Graphics没有.有了反锯齿效果即使图片被无线放大也不会失真.但是Graphics的会
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 如果图片为空就不绘图了
        if (image == null) {
            return;
        }
        // 因为引用的关系,image2d的改变实际已经改变了image,所以只需把image绘制到传进来的g向下转型后的g2d即可
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void drawGreets() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        image2d.setColor(Color.RED);
        image2d.drawString("Hello JComponent!!", 100, 100);
        image2d.drawString("你好,梁山广!!!", 100, 140);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        Lesson03JComponentDemo2 mycanvas = new Lesson03JComponentDemo2();
        JButton okBtn = new JButton("绘图");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 记得要重新绘图
                mycanvas.drawGreets();
                mycanvas.repaint();
            }
        });
        // 设置面板布局
        frame.getContentPane().setLayout(new BorderLayout());
        // 控件处于中间的位置
        frame.getContentPane().add(mycanvas, BorderLayout.CENTER);
        frame.add(okBtn, BorderLayout.SOUTH);
        // 设置退出动作
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置面板的大小
        frame.setSize(400, 400);
        // 设置可见性
        frame.setVisible(true);
    }
}
