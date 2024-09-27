package com.huawei.l00379880.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 填充与描边
 * @author      : 梁山广
 * @date        : 2017/11/15 10:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson04FillAndStroke extends JComponent {
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

    /**
     * 绘制矩形
     */
    public void drawRect() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();

        // 填充矩形
        image2d.setColor(Color.YELLOW);
        image2d.fillRect(10, 10, 150, 150);
        // 描边(先填充后描边)
        image2d.setColor(Color.RED);
        image2d.drawRect(10, 10, 150, 150);
    }

    /**
     * 绘制扇形和圆弧
     */
    public void drawArc() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 填充弧形1
        image2d.setColor(Color.YELLOW);
        image2d.fillArc(10, 10, 200, 200, 0, 120);
        // 描边(先填充后描边)
        image2d.setColor(Color.RED);
        image2d.drawArc(10, 10, 200, 200, 0, 120);
        // 添加说明文字
        image2d.setColor(Color.GREEN);
        image2d.drawString("33.33%", 100, 50);

        // 填充弧形2
        image2d.setColor(Color.BLUE);
        image2d.fillArc(10, 10, 200, 200, 120, 90);
        // 描边(先填充后描边)
        image2d.setColor(Color.RED);
        image2d.drawArc(10, 10, 200, 200, 120, 90);
        // 添加说明文字
        image2d.setColor(Color.GREEN);
        image2d.drawString("25%", 50, 100);

        // 填充弧形3
        image2d.setColor(Color.CYAN);
        image2d.fillArc(10, 10, 200, 200, 210, 150);
        // 描边(先填充后描边)
        image2d.setColor(Color.RED);
        image2d.drawArc(10, 10, 200, 200, 210, 150);
        // 添加说明文字
        image2d.setColor(Color.MAGENTA);
        image2d.drawString("41.67%", 100, 150);

    }

    /**
     * 绘制椭圆
     */
    public void drawOval() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制圆和椭圆,通过调整宽高比即可,width=height为圆,width!=height为椭圆
        // width>height为躺着的椭圆,width<height为站着的椭圆
        image2d.setColor(Color.YELLOW);
        image2d.fillOval(10, 10, 150, 200);
        image2d.setColor(Color.RED);
        image2d.drawOval(10, 10, 150, 200);
    }

    /**
     * 绘制圆角矩形
     */
    public void drawRoundRect() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制圆角矩形
        image2d.setColor(Color.YELLOW);
        image2d.fillRoundRect(10, 10, 250, 200, 20, 20);
        image2d.setColor(Color.RED);
        image2d.drawRoundRect(10, 10, 250, 200, 20, 20);
    }

    /**
     * 绘制不规则多边形
     */
    void drawPolygon() {
        int[] xpoints = new int[]{10, 20, 30, 40, 50, 60, 100, 150, 80, 40, 10};
        int[] ypoints = new int[]{10, 300, 240, 200, 180, 170, 150, 80, 50, 10};
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        image2d.setColor(Color.YELLOW);
        image2d.fillPolygon(xpoints, ypoints, 10);
        image2d.setColor(Color.RED);
        image2d.drawPolygon(xpoints, ypoints, 10);
    }

    void drawLine() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        image2d.setColor(Color.RED);
        image2d.drawLine(10, 10, 200, 200);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        frame.setTitle("填充与描边");

        Lesson04FillAndStroke mycanvas = new Lesson04FillAndStroke();
        JButton okBtn = new JButton("绘图");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 这个调用各种drawXXX(drawRoundRect、drawOval、drawRect等)函数进行绘图即可
                mycanvas.drawPolygon();
                // 记得要重新绘图
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
