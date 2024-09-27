package com.huawei.l00379880;
/**
 * 图像的读取,灰度图和彩色的图
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lesson02 {

    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 创建图像
        Mat src = Imgcodecs.imread(rootPath + "lena.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Rect rect = new Rect(10, 10, 200, 200);
        Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(255, 0, 0), 2, 8, 0);
        Imgcodecs.imwrite(rootPath + "lena_color.jpg", src);
        src.release();
    }
}
