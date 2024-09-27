package com.huawei.l00379880.middle;

import java.awt.*;
import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 色彩空间转换
 * @author      : 梁山广
 * @date        : 2017/11/19 21:11
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson07ColorSpaceExchange {
    public static String imgPath = CommonPanel.ROOT_PATH + "myfuck.jpg";

    public static void process(BufferedImage image) {

        // 图像的的实际操作
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

                // RGB <==> YCbCr
                // 每一次两种空间的转换都会损失一定的图像精度，因为有double向int的强制转换，所以图像转换多了会越来越黑
                double[] yCbCr = rgb2CbCr(channelR, channelG, channelB);
                int[] rgb = YCbCr2rgb(yCbCr[0], yCbCr[1], yCbCr[2]);
                channelR = rgb[0];
                channelG = rgb[1];
                channelB = rgb[2];

                // RGB <===> HSB,因为是官方自带地，所以是无损转换地
                float[] hsbVal = new float[3];
                Color.RGBtoHSB(channelR, channelG, channelB, hsbVal);
                int color = Color.HSBtoRGB(hsbVal[0], hsbVal[1], hsbVal[2]);
                Color c = new Color(color);
                channelR = c.getRed();
                channelG = c.getGreen();
                channelB = c.getBlue();
                pixels[index] = (channelA << 24) | (channelR << 16) | (channelG << 8) | (channelB);
            }
        }
        CommonMethods.setRGB(image, 0, 0, width, height, pixels);
    }

    /**
     * RGB 到YCbCr空间的转换
     *
     * @param channelR R通道像素
     * @param channelG G通道像素
     * @param channelB B通道像素
     * @return 处理完的RGB通道像素值, 返回数组内的每个元素的意义也不再是RGB而是YCbCr了
     */
    public static double[] rgb2CbCr(int channelR, int channelG, int channelB) {
        double y = 0.183 * channelR + 0.614 * channelG + 0.062 * channelB;
        double Cb = -0.101 * channelR - 0.338 * channelG + 0.439 * channelB + 128;
        double Cr = 0.439 * channelR - 0.399 * channelG + 0.040 * channelB + 128;
        return new double[]{y, Cb, Cr};
    }

    /**
     * 把YCbCr空间转换为RGB色彩空间
     *
     * @param Y
     * @param Cb
     * @param Cr
     * @return
     */
    public static int[] YCbCr2rgb(double Y, double Cb, double Cr) {
        int channelR = (int) (1.164 * (Y - 16) + 1.793 * (Cr - 128));
        int channelG = (int) (1.164 * (Y - 16) - 0.534 * (Cr - 128) - 0.213 * (Cb - 128));
        int channelB = (int) (1.164 * (Y - 16) + 2.115 * (Cb - 128));

        return new int[]{clamp(channelR), clamp(channelG), clamp(channelB)};
    }

    /**
     * 把不在0~255范围内的进行合理取舍
     *
     * @param value
     * @return
     */
    public static int clamp(int value) {
        return value < 0 ? 0 : (value > 255 ? 255 : value);
    }
}
