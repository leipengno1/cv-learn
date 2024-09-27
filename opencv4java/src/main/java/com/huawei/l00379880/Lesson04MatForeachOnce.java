package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 一次性遍历图像,获取像素点与图像的额大小和类型等相关信息
 */
public class Lesson04MatForeachOnce {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        System.out.println("************************输出图像的基本信息****************************");
        //图像类型,就是上面的CvType
        int type = src.type();
        System.out.println("CvType.8UC3:" + CvType.CV_8UC3);
        System.out.println("CvType.8UC1:" + CvType.CV_8UC1);
        System.out.println("type:" + type);

        // 宽度
        int width = src.width();
        System.out.println("width:" + width);
        // 高度
        int height = src.height();
        System.out.println("height:" + height);
        // 通道数
        int channels = src.channels();
        System.out.println("channels:" + channels);
        // 深度
        int depth = src.depth();
        System.out.println("depth:" + depth);
        System.out.println("***************************图像的类型转换****************************");
        // 转换为浮点图像,进行位数扩展,方便进行大图像精确计算
        Mat dst = new Mat(src.size(), CvType.CV_32FC1);
        src.convertTo(dst, CvType.CV_32F);
        System.out.println("转换完成");

        System.out.println("***************************循环遍历图像******************************");
        // 读取并修改每个像素点的像素值
        // 用于获取全部的图像数据
        byte[] data = new byte[channels * width * height];
        //把全部元素放到data中
        src.get(0, 0, data);
        int r, g, b;
        //灰度图像只有一个通道
        int gray;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // 赋值给pixel
                if (channels == 3) {
                    // 彩色图像,&0xff是为了转换为十进制
                    b = data[(row * width + col) * channels];
                    g = data[(row * width + col) * channels + 1];
                    r = data[(row * width + col) * channels + 2];

                    //图像取反
                    b = 255 - b;
                    g = 255 - g;
                    r = 255 - r;

                    //再转换为二进制后放回去
                    data[(row * width + col) * channels] = (byte) b;
                    data[(row * width + col) * channels + 1] = (byte) g;
                    data[(row * width + col) * channels + 2] = (byte) r;
                } else {
                    // 灰度图像,channels为1,乘不乘都可以
                    gray = data[(row * width + col) * channels] & 0xff;
                    gray = 255 - gray;
                    // 转换为二进制
                    data[(row * width + col) * channels] = (byte) gray;
                }
            }
        }
        // 把点处理完了就全放回去
        src.put(0, 0, data);
        Imgcodecs.imwrite(rootPath + "lesson04_changePixels_Once.jpg", src);
        System.out.println("图像转换完成");
        src.release();
        dst.release();
    }
}
