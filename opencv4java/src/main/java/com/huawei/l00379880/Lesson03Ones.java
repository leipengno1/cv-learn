package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson03Ones {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 初始化创建一个300*300的图像,各个像素点的值初始化为1
        Mat src = Mat.ones(300, 300, CvType.CV_8UC3);
        Imgcodecs.imwrite(rootPath + "lesson3_03.jpg", src);
        src.release();
    }
}
