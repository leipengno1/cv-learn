package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图像二值化,利用Core里的InRange函数
 */
public class Lesson06InRange {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "tinygreen.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        Mat dst = new Mat();
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2HSV);
        Mat binary = new Mat();
        // 二值化之后映射到
        Core.inRange(dst, new Scalar(30, 45, 45), new Scalar(180, 255, 255), binary);
        ImageUI binaryUI = new ImageUI();
        binaryUI.imshow("二值化之后的图像", binary);
    }
}
