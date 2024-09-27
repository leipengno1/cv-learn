package com.huawei.l00379880;
/**
 * 图像的读取,灰度图和彩色的图
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lesson02Gray {

    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 创建图像,灰度图像
        Mat src = Imgcodecs.imread(rootPath + "lena.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Rect rect = new Rect(10, 10, 200, 200);
        // tl:top left  br:bottom right 两个点确定一个长方形,Scalar代指颜色
        Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(255), 2, 8, 0);
        // 生成灰度图像
        Imgcodecs.imwrite(rootPath + "lena_gray.jpg", src);
        src.release();
    }
}
