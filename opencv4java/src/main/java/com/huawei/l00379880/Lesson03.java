package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson03 {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = new Mat();
        //8UC3的含义:8位的unsigned char 型,3通道(即彩色). 类比来说,8UC1是灰度图
        src.create(300, 300, CvType.CV_8UC3);
        src.setTo(new Scalar(0, 255, 0));//设置为绿色的
        Imgcodecs.imwrite(rootPath + "lesson3_01.jpg", src);
        src.release();
    }
}
