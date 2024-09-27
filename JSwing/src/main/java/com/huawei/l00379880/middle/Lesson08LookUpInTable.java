package com.huawei.l00379880.middle;

import edu.princeton.cs.algs4.Stopwatch;

import java.awt.image.BufferedImage;


/**************************************************************************
 * @Description : 像素查找表,利用空间换时间,这样可以大大加快美白算法的速度
 * @author      : 梁山广
 * @date        : 2017/11/21 20:28
 * @email       : liangshanguang2@gmail.com
 *************************************************************************/
public class Lesson08LookUpInTable {
    public static String imgPath = CommonPanel.ROOT_PATH + "example.jpg";

    public static void process(BufferedImage image) {
        Stopwatch timer = new Stopwatch();
        // 图像美白的参数,越接近1美白效果越好但是为1的话就会一片黑了
        double beta = 1.02;

        // 图像的的实际操作
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        int index = 0;
        int[] lut = new int[256];
        for (int i = 0; i < 256; i++) {
            // 提前对所有的情况都做好处理,0~255都处理了,下面直接用下标调用即可,可以省去很大的运算量
            lut[i] = imgMath(i, beta);
        }
        // 获取全部的像素
        CommonMethods.getRGB(image, 0, 0, width, height, pixels);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                // 1、读像素
                // 得到当前点的下标.32位二进制数,每8bit依次代表A、R、G、B四个通道
                index = row * width + col;
                // RGB三通道混合在一起的
                int pixel = pixels[index];
                // 右移24位,相当于把32位中最高8位的值(Alpha通道)移到最低8bit,
                // &0xff是为了取出最低8bit,把结果转换为0~255之间的值,
                int channelA = (pixel >> 24) & 0xff;
                // R、G、B依次类推,分别为17~24,9~16,1~8位
                int channelR = (pixel >> 16) & 0xff;
                int channelG = (pixel >> 8) & 0xff;
                int channelB = pixel & 0xff;

                // 直接调用像素查找表中预处理的好的值即可
                channelR = lut[channelR];
                channelG = lut[channelG];
                channelB = lut[channelB];
                pixels[index] = (channelA << 24) | (channelR << 16) | (channelG << 8) | (channelB);
            }
        }
        CommonMethods.setRGB(image, 0, 0, width, height, pixels);
        System.out.println("利用LUT查找表的美白时间为:" + timer.elapsedTime() + "s");
    }

    public static int imgMath(int pixel, double beta) {
        // 把结果归一化到0~255之间
        double scale = 255 / Math.log(255 * (beta - 1) + 1) * Math.log(beta);
        double numerator = Math.log(pixel * (beta - 1) + 1);
        double denominator = Math.log(beta);

        return (int) (scale * numerator / denominator);
    }
}
