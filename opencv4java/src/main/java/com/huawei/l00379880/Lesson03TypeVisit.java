package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 遍历图像,获取像素点与图像的额大小和类型等相关信息
 */
public class Lesson03TypeVisit {
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
        System.out.println("***************************图像的类型转换****************************");
        // 转换为浮点图像,进行位数扩展,方便进行大图像精确计算
        Mat dst = new Mat(src.size(), CvType.CV_32FC1);
        src.convertTo(dst,CvType.CV_32F);
    }
}
