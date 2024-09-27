package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/***********************************************************
 * @Description : 简单的绘图工具
 * @author      : 梁山广
 * @date        : 2017/11/17 17:07
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson07MyPaintDraw extends JComponent {
    private Lesson07MouseListenerDrawBoard listener;

    public Lesson07MyPaintDraw() {
        listener = new Lesson07MouseListenerDrawBoard(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<Point> pointList = listener.getPointList();
        // 设置线宽
        BasicStroke bs = new BasicStroke(5);
        g2d.setStroke(bs);
        // 设置颜色
        g2d.setColor(Color.RED);
        for (int i = 1; i < pointList.size(); i++) {
            g2d.drawLine(pointList.get(i - 1).x, pointList.get(i - 1).y, pointList.get(i).x, pointList.get(i).y);
        }
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        frame.setTitle("鼠标响应事件");
        Lesson07MyPaintDraw mycanvas = new Lesson07MyPaintDraw();
        // 设置面板布局
        frame.getContentPane().setLayout(new BorderLayout());
        // 控件处于中间的位置
        frame.getContentPane().add(mycanvas, BorderLayout.CENTER);
        // 设置退出动作
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置面板的大小
        frame.setSize(400, 400);
        // 设置可见性
        frame.setVisible(true);
    }
}
