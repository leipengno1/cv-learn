package com.huawei.l00379880.middle;

import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 图像的数学运算
 * @author      : 梁山广
 * @date        : 2017/11/19 19:35
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson05MathBasic {
    public static String imgPath = CommonPanel.ROOT_PATH + "example.jpg";

    public static void process(BufferedImage image) {

        // 把元素进行数学处理,0-加,1-减,2-乘,3-除,4-取对数,5-开平方,6-取平方
        int opType = 6;
        int opValue = 20;

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

                channelR = imgMath(channelR, opType, opValue);
                channelG = imgMath(channelG, opType, opValue);
                channelB = imgMath(channelB, opType, opValue);
                pixels[index] = (channelA << 24) | (channelR << 16) | (channelG << 8) | (channelB);
            }
        }
        CommonMethods.setRGB(image, 0, 0, width, height, pixels);
    }

    /**
     * 对图像进行各种操作,0表示纯黑,255表示纯白
     *
     * @param channel 读取的指定通道的像素值
     * @param opType  操作类型 0-加,1-减,2-乘,3-除,4-取对数,5-开平方,6-取平方
     * @param opValue 对需要操作的像素值改变的幅度
     * @return 把改后的像素值返回
     */
    public static int imgMath(int channel, int opType, int opValue) {
        int result = 0;
        switch (opType) {
            case 0:
                result = channel + opValue;
                break;
            case 1:
                result = channel - opValue;
                break;
            case 2:
                result = channel * opValue;
                break;
            case 3:
                result = channel / opValue;
                break;
            case 4:
                result = (int) (Math.log(channel));
                break;
            case 5:
                result = (int) Math.sqrt(channel);
                break;
            case 6:
                result = channel * channel;
                break;
            default:
                break;
        }
        if (result > 255) {
            result = 255;
        }
        if (result < 0) {
            result = 0;
        }
        return result;
    }
}
