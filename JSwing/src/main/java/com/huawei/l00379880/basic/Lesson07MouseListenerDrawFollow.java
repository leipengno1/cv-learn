package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 鼠标响应事件(Pressed、Released、Dragged),实现选择区域的功能
 * @author      : 梁山广
 * @date        : 2017/11/16 8:58
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson07MouseListenerDrawFollow extends MouseAdapter {

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private JComponent mycanvas;

    private Rectangle rect;

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }


    public Lesson07MouseListenerDrawFollow(JComponent mycanvas) {
        this.startX = -1;
        this.startY = -1;
        this.endX = -1;
        this.endY = -1;
        this.mycanvas = mycanvas;
        this.rect = new Rectangle();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        startX = p.x;
        startY = p.y;
        rect.x = startX;
        rect.y = startY;
        rect.width = 0;
        rect.height = 0;
        this.mycanvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 获取当前鼠标所在的点
        Point p = e.getPoint();
        this.endX = p.x;
        this.endY = p.y;
        if (startX < endX && startY < endY) {
            int width = endX - startX;
            int height = endY - startY;
            rect.setBounds(startX, startY, width, height);
        }
        this.mycanvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 获取当前鼠标所在的点
        Point p = e.getPoint();
        this.endX = p.x;
        this.endY = p.y;
        if (startX < endX && startY < endY) {
            int width = endX - startX;
            int height = endY - startY;
            rect.setBounds(startX, startY, width, height);
        }
        this.mycanvas.repaint();
    }

}
