package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 鼠标响应事件(Pressed、Released、Dragged),实现一个画图板
 * @author      : 梁山广
 * @date        : 2017/11/16 8:58
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson07MouseListenerDrawBoard extends MouseAdapter {

    private int startX;
    private int startY;

    private List<Point> pointList;

    private JComponent mycanvas;

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public Lesson07MouseListenerDrawBoard(JComponent mycanvas) {
        this.startX = -1;
        this.startY = -1;
        this.pointList = new ArrayList<>();
        this.mycanvas = mycanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 单击清空所有的点
        pointList.clear();
        Point p = e.getPoint();
        startX = p.x;
        startY = p.y;
        this.mycanvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pointList.size() > 0) {
            pointList.add(e.getPoint());
        }
        this.mycanvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        pointList.add(p);
        this.mycanvas.repaint();
    }

}
