package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson03Gray {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 初始化为300*300的图像,各个像素点的初始值为0
        Mat src = Mat.zeros(300, 300, CvType.CV_8UC1);
        Imgcodecs.imwrite(rootPath + "lesson3_03_Ones.jpg", src);
        src.release();
    }
}
