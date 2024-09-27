package com.huawei.l00379880.middle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * @Description : 测试本教程各课的通用面板
 * @author      : 梁山广
 * @date        : 2017/11/18 16:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class CommonPanel extends JComponent implements ActionListener {
    public static final String ROOT_PATH = "D:\\l00379880\\GithubProjects\\images\\";
    private BufferedImage image;
    private JButton lesson03Btn;
    private JButton lesson04Btn;
    private JButton lesson05Btn;
    private JButton lesson06Btn;
    private JButton lesson07Btn;
    private JButton lesson08Btn;
    private JButton saveBtn;

    public CommonPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) {
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 第三课图像处理:target.png(拉莫斯防守伊瓜因那张)
        if (e.getSource() == lesson03Btn) {
            Lesson03PixelOperation.process(image);
        }
        // 第四课:图像统计,lena的灰度图
        if (e.getSource() == lesson04Btn) {
            Lesson04PixelStatistic.process(image);
        }
        // 第五课:基本的图像运算(加减乘除开方取对数等),多次点击可以看到叠加效果
        if (e.getSource() == lesson05Btn) {
            Lesson05MathBasic.process(image);
        }
        // 第6课:美白
        if (e.getSource() == lesson06Btn) {
            Lesson06MathWhiteImage.process(image);
        }
        // 第7课:空间转换
        if (e.getSource() == lesson07Btn) {
            Lesson07ColorSpaceExchange.process(image);
        }
        // 第7课:空间转换
        if (e.getSource() == lesson08Btn) {
            Lesson08LookUpInTable.process(image);
        }
        if (e.getSource() == saveBtn) {
            CommonMethods.save(image, ROOT_PATH + "middle\\target_result.png");
        }
        this.repaint();
    }

    public JButton getLesson03Btn() {
        lesson03Btn = new JButton("3像素读写");
        lesson03Btn.addActionListener(this);
        return lesson03Btn;
    }

    public JButton getLesson04Btn() {
        lesson04Btn = new JButton("4像素统计");
        lesson04Btn.addActionListener(this);
        return lesson04Btn;
    }

    public JButton getLesson05Btn() {
        lesson05Btn = new JButton("5数学运算");
        lesson05Btn.addActionListener(this);
        return lesson05Btn;
    }

    public JButton getLesson06Btn() {
        lesson06Btn = new JButton("6美白");
        lesson06Btn.addActionListener(this);
        return lesson06Btn;
    }
    public JButton getLesson07Btn() {
        lesson07Btn = new JButton("7空间转换");
        lesson07Btn.addActionListener(this);
        return lesson07Btn;
    }
    public JButton getLesson08Btn() {
        lesson08Btn = new JButton("8查找表美白");
        lesson08Btn.addActionListener(this);
        return lesson08Btn;
    }

    public JButton getSaveBtn() {
        saveBtn = new JButton("保存");
        saveBtn.addActionListener(this);
        return saveBtn;
    }


    public static void main(String[] args) throws IOException {
        File file = new File(Lesson06MathWhiteImage.imgPath);
        BufferedImage image = ImageIO.read(file);
        JFrame frame = new JFrame("图像的基本操作");
        // 添加图片到面板
        CommonPanel panel = new CommonPanel(image);
        frame.add(panel, BorderLayout.CENTER);
        // 添加按钮面板,添加各节课的处理事件
        JPanel jPanel = new JPanel();
        jPanel.add(panel.getLesson03Btn());
        jPanel.add(panel.getLesson04Btn());
        jPanel.add(panel.getLesson05Btn());
        jPanel.add(panel.getLesson06Btn());
        jPanel.add(panel.getLesson07Btn());
        jPanel.add(panel.getLesson08Btn());
        jPanel.add(panel.getSaveBtn());
        frame.add(jPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth() + 16, image.getHeight() + 38);
        frame.setVisible(true);
    }

}
