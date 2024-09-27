package com.huawei.l00379880.middle;

import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : BufferedImage的像素操作(依托于一个自己创建的操作面板)
 *                  读像素
 *                  写像素
 *                  通道数据(ARGB)
 *                  像素值范围
 * @author      : 梁山广
 * @date        : 2017/11/19 18:20
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson03PixelOperation {
    public static String imgPath = CommonPanel.ROOT_PATH + "fuck.jpg";
    public static void process(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        int index = 0;
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

                // 把元素设置回去
                channelA = 255 - channelA;
                channelR = 255 - channelR;
                channelG = 255 - channelG;
                channelB = 255 - channelB;
                pixels[index] = (channelA << 24) | (channelR << 16) | (channelG << 8) | (channelB);
            }
        }
        CommonMethods.setRGB(image, 0, 0, width, height, pixels);
        System.out.println("像素读写完成");
    }
}
