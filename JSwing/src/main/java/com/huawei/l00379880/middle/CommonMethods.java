package com.huawei.l00379880.middle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***********************************************************
 * @Description : 通用的图像处理方法
 * @author      : 梁山广
 * @date        : 2017/11/19 18:02
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class CommonMethods {
    /**
     * 保存图片
     */
    public static void save(BufferedImage image,String path) {
        File f = new File(path);
        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("保存成功!!");
    }

    /**
     * 获取指定区域的像素数组(每个点的RGB为一个组合起来的大整数),屏蔽了整型图片和非整型图片的区别
     *
     * @param image  原始图片
     * @param x      取样处起点横坐标
     * @param y      样处起始点纵坐标
     * @param width  取样宽度
     * @param height 取样高度
     * @param pixels 取样的像素点数据存储的数组,用于返回像素
     * @return 将取好值的pixels数组返回
     */
    public static int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
        }
        return image.getRGB(x, y, width, height, pixels, 0, width);
    }

    /**
     * 设置取样区的像素
     *
     * @param image  原始图片
     * @param x      取样处起点横坐标
     * @param y      样处起始点纵坐标
     * @param width  取样宽度
     * @param height 取样高度
     * @param pixels 取样的像素点数据存储的数组,用于给该区域设置像素
     */
    public static void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
        int type = image.getType();
        if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
            image.getRaster().setDataElements(x, y, width, height, pixels);
        } else {
            image.setRGB(x, y, width, height, pixels, 0, width);
        }
    }
}
