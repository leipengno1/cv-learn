
package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 图像的显示,见ImageUI和ImageUITest
 */
public class Lesson05Mat2BufferedImage {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 灰度图形式加载
        Mat src = Imgcodecs.imread(rootPath + "lena.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        ImageUI ui = new ImageUI();
        ui.imshow("lena图像显示", src);

        // 灰度图形式加载
        Mat src2 = Imgcodecs.imread(rootPath + "example.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        ImageUI ui2 = new ImageUI();
        ui2.imshow("美女图像显示", src2);
    }
}
