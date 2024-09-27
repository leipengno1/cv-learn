package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/***********************************************************
 * @Description : 图片的各种仿射变换,注意图片要放在target目录里
 *                对应的class文件所在的目录
 * @author      : 梁山广
 * @date        : 2017/11/17 19:02
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson06AffineTransformImage extends JComponent {
    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
        // 向下转换
        Graphics2D g2d = (Graphics2D) g;
        // 反锯齿特效,Graphics2D有,但是Graphics没有.有了反锯齿效果即使图片被无线放大也不会失真.但是Graphics的会
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        URL url = this.getClass().getResource("cat.jpg");
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果图片为空就不绘图了
        if (image == null) {
            return;
        }
        // 获取当前的transform对象
        AffineTransform saveXForm = g2d.getTransform();

        AffineTransform atf = new AffineTransform();
        atf.setToTranslation(this.getWidth() / 2, this.getHeight() / 2);
        // 先旋转后放缩还是先放缩后旋转的结果是不同地,坐标会有细微差别(距离上下测的距离)
        // 旋转45度,去掉下面这一行就不旋转了.单独使用用setToRotate,会清除之前的效果
        atf.rotate(Math.PI / 4);
        // 缩小到原来的一半.注意不要用setToScale,setToXXX函数会清除前面的所有特效.类似的setToRotate也是同样的效果
        atf.scale(2, 2);
        g2d.setTransform(atf);
        g2d.drawImage(image, -100, -100, image.getWidth(), image.getHeight(), null);
        // 恢复当前的transform对象
        g2d.setTransform(saveXForm);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        // 设置面板布局
        frame.getContentPane().setLayout(new BorderLayout());
        // 控件处于中间的位置
        frame.getContentPane().add(new Lesson06AffineTransformImage(), BorderLayout.CENTER);
        // 设置退出动作
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 设置面板的大小
        frame.setSize(400, 400);
        // 设置可见性
        frame.setVisible(true);
    }
}
