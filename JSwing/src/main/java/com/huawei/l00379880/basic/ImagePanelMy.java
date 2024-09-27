package com.huawei.l00379880.basic;
/**
 * 图像的基本处理
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanelMy extends JComponent implements ActionListener {
    private static final String ROOT_PATH = "D:\\l00379880\\GithubProjects\\images\\";
    private JButton processBtn;
    private JButton processBtn2;
    private BufferedImage image;

    public ImagePanelMy(BufferedImage image) {
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
        g2d.setFont(font);
        g2d.drawString("迪丽热巴", 100, 100);
        try {
            saveImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process2() {
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("微软雅黑", Font.BOLD + Font.ITALIC, 44);
        g2d.setFont(font);
        g2d.drawString("迪丽热巴2", 100, 200);
    }

    public void saveImage() throws IOException {
        File outFile = new File("D:\\dilireba.png");
        ImageIO.write(image, "png", outFile);
    }

    public static void main(String[] args) throws IOException {
        File f = new File(ROOT_PATH + "lena.png");
        BufferedImage image = ImageIO.read(f);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImagePanelMy imp = new ImagePanelMy(image);
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
