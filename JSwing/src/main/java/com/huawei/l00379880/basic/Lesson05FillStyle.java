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
 * @Description : 填充的风格
 * @author      : 梁山广
 * @date        : 2017/11/15 16:37
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson05FillStyle extends JComponent {
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
     * 实体填充
     */
    public void fillSolid() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 实体填充
        image2d.setColor(Color.RED);
        image2d.fillRect(20, 20, 200, 200);
    }

    /**
     * 渐进填充
     */
    void fillGradient() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 渐变的画笔.(x1,y1)和(x2,y2)决定填充的方向,下面的是三个方向的例子
        // 自上而下 GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, 200, Color.YELLOW, false);
        // 自左而右 GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 0, 200, Color.YELLOW, false);
        // 左上角到右下角
        GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, 200, 200, Color.YELLOW, false);
        image2d.setPaint(gp);
        image2d.fillRect(20, 20, 200, 200);
    }

    /**
     * 雷达式渐进填充,有立体感
     */
    void fillGradientRadial() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //
        RadialGradientPaint gp = new RadialGradientPaint(120, 120, 50, 120, 120, new float[]{0.0f, 0.5f, 1.0f},
                new Color[]{Color.BLACK, Color.WHITE, Color.BLACK}, MultipleGradientPaint.CycleMethod.REPEAT);
        image2d.setPaint(gp);
        image2d.fillRect(20, 20, 200, 200);
    }

    /**
     * 纹理填充
     */
    void fillTexture() throws IOException {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 之所以一直返回为null是因为图片应放在编译后的class文件所在的路径里,即target里Lesson04FillStyle.class所在的路径
        // this.getClass().getResource是得到当前对象对应的类文件（*.class）所在的目录下的文件
        URL url = this.getClass().getResource("texture.jpg");
        BufferedImage textureImage = ImageIO.read(url);
        Rectangle2D rectangle2D = new Rectangle();
        rectangle2D.setRect(0, 0, textureImage.getWidth(), textureImage.getHeight());
        TexturePaint txp = new TexturePaint(textureImage, rectangle2D);
        image2d.setPaint(txp);
        image2d.fillRect(20, 20, 300, 300);
        image2d.setColor(Color.YELLOW);
        image2d.drawRect(20, 20, 300, 300);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        frame.setTitle("填充与描边");

        Lesson05FillStyle mycanvas = new Lesson05FillStyle();
        JButton okBtn = new JButton("绘图");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mycanvas.fillTexture();
                    // 记得要重新绘图
                    mycanvas.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
