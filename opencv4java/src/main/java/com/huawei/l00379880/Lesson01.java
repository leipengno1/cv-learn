package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson01 {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 创建图像
        Mat src = new Mat();
        src.create(300, 300, CvType.CV_8UC3);
        src.setTo(new Scalar(0, 255, 255));// B,G,R
        Imgcodecs.imwrite("D:\\l00379880\\GithubProjects\\images\\image_01.jpg", src);
    }
}
