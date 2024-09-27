package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/***********************************************************
 * @Description : 仿射变换(平移-translation、旋转-rotation、
 *                放缩--differential scale、倾斜--skew)
 * @author      : 梁山广
 * @date        : 2017/11/15 20:57
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson06AffineTransform extends JComponent {
    private BufferedImage image;
    private Shape myShape;

    public Lesson06AffineTransform() {
        this.myShape = new Rectangle(-100, -100, 200, 200);
    }

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
     * 仿射变换--平移到中间+旋转45度+缩小到原来的1/4(长宽各一半)
     */
    void affineTransformAll() {
        // 这里的this代指这个类new出来的JFrame对象,其宽度和高度是自己设置的
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 获取当前的transform对象
        AffineTransform saveXForm = image2d.getTransform();

        AffineTransform atf = new AffineTransform();
        atf.setToTranslation(this.getWidth() / 2, this.getHeight() / 2);
        // 旋转45度,去掉下面这一行就不旋转了.单独使用用setToRotate,会清除之前的效果
        atf.rotate(Math.PI / 4);
        // 缩小到原来的一半.注意不要用setToScale,setToXXX函数会清除前面的所有特效.类似的setToRotate也是同样的效果
        atf.scale(0.5, 0.5);
        Shape tempShape = atf.createTransformedShape(myShape);
        image2d.setPaint(Color.RED);
        image2d.draw(tempShape);
        // 恢复当前的transform对象
        image2d.setTransform(saveXForm);
    }

    /**
     * 以一个图片为例进行放缩、旋转和平移
     */
    void affineTransformImage() throws IOException {
        // 之所以一直返回为null是因为图片应放在编译后的class文件所在的路径里,即target里Lesson04FillStyle.class所在的路径
        // this.getClass().getResource是得到当前对象对应的类文件（*.class）所在的目录下的文件
        URL url = this.getClass().getResource("cat.jpg");
        image = ImageIO.read(url);
        // 创建绘图对象
        Graphics2D image2d = image.createGraphics();
        // 反锯齿效果,不加这一句圆弧的边全是锯齿,很难看地
        image2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        image2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 获取当前的transform对象
        AffineTransform saveXForm = image2d.getTransform();

        AffineTransform atf = new AffineTransform();
        atf.setToTranslation(this.getWidth() / 2, this.getHeight() / 2);
        // 旋转45度,去掉下面这一行就不旋转了.单独使用用setToRotate,会清除之前的效果
        atf.rotate(Math.PI / 4);
        // 缩小到原来的一半.注意不要用setToScale,setToXXX函数会清除前面的所有特效.类似的setToRotate也是同样的效果
        atf.scale(0.5, 0.5);
        image2d.drawImage(image, 100, 100, image.getWidth(), image.getHeight(), null);
        // 恢复当前的transform对象
        image2d.setTransform(saveXForm);
    }

    public static void main(String[] args) {
        // 新建组件的载体
        JFrame frame = new JFrame();
        frame.setTitle("图形变换");

        Lesson06AffineTransform mycanvas = new Lesson06AffineTransform();
        JButton okBtn = new JButton("绘图");
        okBtn.addActionListener(e -> {
            mycanvas.affineTransformAll();
            // 记得要重新绘图
            mycanvas.repaint();
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
