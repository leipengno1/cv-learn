package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * @Description : 透明度与混合(AlphaComposite类已经实现了下面的各种规则)
 *                  透明度:
 *                        透明通道(α),0表示完全透明,1表示完全不透明
 *                        利用透明通道实现混合(alpha blend)
 *                  Porter-Duff合成规则:
 *                        α=Fs*αs+Fd*ad
 *                        4种概率:0,1,as,1-as
 *                        12种合成规则:2*2*2*2=16
 *                                        1.PorterDuff.Mode.CLEAR
 *                                        所绘制不会提交到画布上。
 *                                        2.PorterDuff.Mode.SRC
 *                                        显示上层绘制图片
 *                                        3.PorterDuff.Mode.DST
 *                                        显示下层绘制图片
 *                                        4.PorterDuff.Mode.SRC_OVER
 *                                        正常绘制显示，上下层绘制叠盖。
 *                                        5.PorterDuff.Mode.DST_OVER
 *                                        上下层都显示。下层居上显示。
 *                                        6.PorterDuff.Mode.SRC_IN
 *                                        取两层绘制交集。显示上层。
 *                                        7.PorterDuff.Mode.DST_IN
 *                                        取两层绘制交集。显示下层。
 *                                        8.PorterDuff.Mode.SRC_OUT
 *                                        取上层绘制非交集部分。
 *                                        9.PorterDuff.Mode.DST_OUT
 *                                        取下层绘制非交集部分。
 *                                        10.PorterDuff.Mode.SRC_ATOP
 *                                        取下层非交集部分与上层交集部分
 *                                        11.PorterDuff.Mode.DST_ATOP
 *                                        取上层非交集部分与下层交集部分
 *                                        12.PorterDuff.Mode.XOR
 *                                        13.PorterDuff.Mode.DARKEN
 *                                        14.PorterDuff.Mode.LIGHTEN
 *                                        15.PorterDuff.Mode.MULTIPLY
 *                                        16.PorterDuff.Mode.SCREEN

 *   分类: android
 * @author      : 梁山广
 * @date        : 2017/11/18 18:08
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson09OpacityAndBlend extends JComponent implements ActionListener {
    private static final String ROOT_PATH = "D:\\l00379880\\GithubProjects\\images\\";
    private JButton processBtn;
    private JButton processBtn2;
    private BufferedImage image;

    public Lesson09OpacityAndBlend(BufferedImage image) {
        this.image = image;
    }

    public JButton getButton() {
        processBtn = new JButton("Process");
        processBtn.addActionListener(this);
        return processBtn;
    }

    public JButton getButton2() {
        processBtn2 = new JButton("Process2");
        processBtn2.addActionListener(this);
        return processBtn2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) {
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == processBtn) {
            this.process();
        }
        if (e.getSource() == processBtn2) {
            this.process2();
        }
        this.repaint();
    }

    public void process() {
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("微软雅黑", Font.BOLD + Font.ITALIC, 34);
        // 设置透明度,第一个参数有16种模式可选,SRC_OVER最常用,此外SRC_ATOP、CLEAR(把交集部分从第一张中抠除)、
        // SRC_IN(如果两张图片相交把交集给第一张图片)、SRC_OUT(将交集扣除)、
        // 可以利用透明度实现淡入淡出动画
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(ac);
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString("欧冠决赛", 100, 100);
        BasicStroke bs = new BasicStroke(5);
        g2d.setStroke(bs);
        g2d.setColor(Color.GREEN);
        g2d.drawRoundRect(80, 62, 180, 50, 20, 20);
        try {
            saveImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("操作1完成");
    }

    public void process2() {
        Graphics2D g2d = image.createGraphics();
        // 设置透明度,第一个参数有16种模式可选
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(ac);
        Font font = new Font("微软雅黑", Font.BOLD + Font.ITALIC, 44);
        g2d.setFont(font);
        g2d.drawString("哈特防守伊瓜因", 100, 200);
        System.out.println("操作2完成!");
    }

    public void saveImage() throws IOException {
        File outFile = new File("D:\\dilireba.png");
        ImageIO.write(image, "png", outFile);
    }

    public static void main(String[] args) throws IOException {
        File f = new File(ROOT_PATH + "target.png");
        BufferedImage image = ImageIO.read(f);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Lesson09OpacityAndBlend imp = new Lesson09OpacityAndBlend(image);
        frame.getContentPane().add(imp, BorderLayout.CENTER);
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        flowPanel.add(imp.getButton());
        flowPanel.add(imp.getButton2());
        frame.getContentPane().add(flowPanel, BorderLayout.SOUTH);
        frame.setSize(image.getWidth() + 16, image.getHeight() + 50);
        frame.setTitle("演示");
        frame.setVisible(true);
    }

}
