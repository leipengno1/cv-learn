package com.huawei.l00379880.basic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * @Description : 图像裁剪
 * @author      : 梁山广
 * @date        : 2017/11/18 20:32
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson11Clip extends JComponent implements ActionListener {
    private static final String ROOT_PATH = "D:\\l00379880\\GithubProjects\\images\\";
    private JButton processBtn;
    private JButton processBtn2;
    private BufferedImage image;
    private boolean clip = false;

    public Lesson11Clip(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 裁剪只能放到paintComponent中执行,因为裁剪形状是在drawImage之前的.外部调用函数在paintComponent之后自然也就在drawImage之后了
        if (clip) {
            Shape shape = new Ellipse2D.Double(120, 50, 200, 200);
            g2d.setClip(shape);
        }
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

    public JButton getButton() {
        processBtn = new JButton("裁剪");
        processBtn.addActionListener(this);
        return processBtn;
    }

    public JButton getButton2() {
        processBtn2 = new JButton("Process2");
        processBtn2.addActionListener(this);
        return processBtn2;
    }

    /**
     * clip操作属于初始化操作,不能放到外面的函数中进行
     */
    public void process() {
        // 设置形状的操作必须放在上面进行
        this.clip = true;
        System.out.println("操作1完成");
    }

    /**
     * 添加水印文字
     */
    public void process2() {
        Graphics2D g2d = image.createGraphics();
        // 设置透明度,第一个参数有16种模式可选
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(ac);
        Font font = new Font("微软雅黑", Font.BOLD + Font.ITALIC, 24);
        g2d.setFont(font);
        g2d.drawString("迪丽热巴", 150, 230);
        g2d.setPaint(Color.GREEN);
        g2d.setStroke(new BasicStroke(10));
        g2d.draw(new Ellipse2D.Double(123, 53, 195, 195));
        try {
            saveImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("操作2完成!");
    }

    public void saveImage() throws IOException {
        File outFile = new File("D:\\dilireba.png");
        ImageIO.write(image, "png", outFile);
    }

    public static void main(String[] args) throws IOException {
        File f = new File(ROOT_PATH + "dilireba.png");
        BufferedImage image = ImageIO.read(f);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Lesson11Clip imp = new Lesson11Clip(image);
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