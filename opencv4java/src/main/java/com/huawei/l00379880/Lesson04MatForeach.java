package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 遍历图像,获取像素点与图像的额大小和类型等相关信息
 */
public class Lesson04MatForeach {
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
        // 每个像素点是3通道的
        byte[] pixel = new byte[channels];
        int r, g, b;
        //灰度图像只有一个通道
        int gray;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // 把从row行col列开始的往后channels个元素赋值给pixel,因为pixel数组的大小为channels,
                // 如果把数组声明更大的话实际会放更多元素的
                src.get(row, col, pixel);
                if (channels == 3) {
                    // 彩色图像,&0xff是为了转换为正的十进制
                    // byte 值 & 0xff 可以把负的byte值转成相对应的正的byte值，因为在16禁止里面byte没有负数
                    // 比如:byte -128 到 127,  做完  & 0xfff 操作以后就变成 0 - 255
                    b = pixel[0] & 0xff;
                    g = pixel[1] & 0xff;
                    r = pixel[2] & 0xff;

                    //图像取反
                    b = 255 - b;
                    g = 255 - g;
                    r = 255 - r;

                    //再转换为二进制后放回去
                    pixel[0] = (byte) b;
                    pixel[1] = (byte) g;
                    pixel[2] = (byte) r;
                } else {
                    // 灰度图像
                    gray = pixel[0] & 0xff;
                    gray = 255 - gray;
                    // 转换为二进制
                    pixel[0] = (byte) gray;
                }
                // 把点处理完了就放回去
                src.put(row, col, pixel);
            }
        }
        Imgcodecs.imwrite(rootPath + "lesson04_changePixels.jpg", src);
        src.release();
        dst.release();
    }
}
