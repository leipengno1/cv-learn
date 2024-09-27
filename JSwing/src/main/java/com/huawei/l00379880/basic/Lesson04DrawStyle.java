package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/***********************************************************
 * @Description : 描边的风格
 * @author      : 梁山广
 * @date        : 2017/11/15 20:22
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson04DrawStyle extends JComponent {
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
     * 描边风格(Stroke)
     */
    void strokeBasic() throws IOException {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 基础描边风格,传参为描边宽度
        BasicStroke bs = new BasicStroke(5);
        image2d.setStroke(bs);
        image2d.setColor(Color.RED);
        image2d.drawRect(20, 20, 200, 200);
    }

    /**
     * 虚线描边风格(Stroke):line-gap-line-line......
     */
    void strokeDash() throws IOException {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // line-gap-line-gap
        float[] dashPattern = {30, 20, 20, 20};

        // 虚线描边风格.第2、3个参数记住用这两个就行了
        BasicStroke bs = new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0);
        image2d.setStroke(bs);
        image2d.setColor(Color.RED);
        image2d.drawRect(20, 20, 200, 200);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        frame.setTitle("填充与描边");

        Lesson04DrawStyle mycanvas = new Lesson04DrawStyle();
        JButton okBtn = new JButton("绘图");
        okBtn.addActionListener(e -> {
            try {
                mycanvas.strokeDash();
                // 记得要重新绘图
                mycanvas.repaint();
            } catch (IOException e1) {
                e1.printStackTrace();
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
