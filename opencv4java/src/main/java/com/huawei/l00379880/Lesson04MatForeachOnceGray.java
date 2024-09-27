package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 一次性遍历图像,获取像素点与图像的额大小和类型等相关信息
 */
public class Lesson04MatForeachOnceGray {
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
        // 转换为灰度图像,下面的函数是非常常用的
        Mat dst = new Mat();
        // 把三色图像转换为灰度图,还有很多其他的转换方式,自己都尝试下
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        channels = dst.channels();
        System.out.println("***************************循环遍历图像******************************");
        // 读取并修改每个像素点的像素值
        // 用于获取全部的图像数据
        byte[] data = new byte[channels * width * height];
        //把全部元素放到data中
        dst.get(0, 0, data);
        int r, g, b;
        //灰度图像只有一个通道
        int gray;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
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
        dst.put(0, 0, data);
        Imgcodecs.imwrite(rootPath + "lesson04_changePixels_Once_Gray.jpg", dst);
        System.out.println("图像转换完成");
        src.release();
        dst.release();
    }
}
