package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;

/***********************************************************
 * @Description : Graphics2D与Graphics的Demo,前者有更多的细节函数
 * @author      : 梁山广
 * @date        : 2017/11/14 21:09
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson02Graphics extends JComponent {
    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        // 设置面板布局
        frame.getContentPane().setLayout(new BorderLayout());
        // 控件处于中间的位置
        frame.getContentPane().add(new Lesson02Graphics(), BorderLayout.CENTER);
        // 设置退出动作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置面板的大小
        frame.setSize(400, 400);
        // 设置可见性
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 向下转型
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawString("Hello,Java Graphics", 100, 100);
    }


}
