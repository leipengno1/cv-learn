package com.huawei.l00379880.middle;

import java.awt.image.BufferedImage;

/***********************************************************************
 * @Description : BufferedImage的像素统计:最大值,最小值,平均值和标准差
 *                可以在Lesson03PixelOperationImage中的process函数中调用
 *                针对灰度图来的,测试图片:lena_gray.png
 * @author      : 梁山广
 * @date        : 2017/11/19 18:16
 * @email       : liangshanguang2@gmail.com
 ***********************************************************************/
public class Lesson04PixelStatistic {

    public static String imgPath = CommonPanel.ROOT_PATH + "cat_gray.jpg";

    public static void process(BufferedImage image) {
        // 最小值要初始化为最大,最大值要初始化为最小.这个是为了防止第一个值出现意外情况
        int min = 255;
        int max = 0;
        double means = 0.0;
        double standardDiff = 0.0;

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        int index;
        double sum = 0.0;
        // 获取全部的像素
        CommonMethods.getRGB(image, 0, 0, width, height, pixels);
        // 循环对每一个像素点进行处理
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                // 1、读像素
                // 得到当前点的下标.32位二进制数,每8bit依次代表A、R、G、B四个通道
                index = row * width + col;
                // RGB三通道混合在一起的
                int pixel = pixels[index];
                // 灰度图只有单通道
                int channel = (pixel >> 16) & 0xff;
                min = Math.min(min, channel);
                max = Math.max(max, channel);
                sum += channel;
            }
        }
        // 平均值为对所有像素点取平均值
        means = sum / (width * height);
        System.out.println("最小值:" + min);
        System.out.println("最大值:" + max);
        System.out.println("平均值:" + means);
        // 下面计算方差
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                // 1、读像素
                // 得到当前点的下标.32位二进制数,每8bit依次代表A、R、G、B四个通道
                index = row * width + col;
                // RGB三通道混合在一起的
                int pixel = pixels[index];
                // 灰度图只有单通道
                int channel = (pixel >> 16) & 0xff;
                standardDiff += Math.pow((channel - means), 2);
            }
        }
        // 计算得到方差
        standardDiff = Math.sqrt(standardDiff / (width * height - 1));
        System.out.println("方差:" + standardDiff);
        // 利用均值实现二值化
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                // 1、读像素
                // 得到当前点的下标.32位二进制数,每8bit依次代表A、R、G、B四个通道
                index = row * width + col;
                // RGB三通道混合在一起的
                int pixel = pixels[index];
                // 灰度图只有单通道
                int channel = (pixel >> 16) & 0xff;
                if (channel > means) {
                    channel = 255;
                } else {
                    channel = 0;
                }
                pixels[index] =  (255 << 24) | (channel << 16) | (channel << 8) | (channel);
            }
        }
        CommonMethods.setRGB(image, 0, 0, width, height, pixels);
    }
}
