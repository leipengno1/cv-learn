package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 自适应的阈值确定法,在OTSU和Triangle方法都不好用的时候用这部分方法(比如光线差异大的情况下)
 * 1.均值法(局部均值)
 * 2.高斯均值法
 * **2.1  b*b大小的像素块
 * **2.2  计算权重均值
 * **2.3  计算阈值T=均值M-常量C
 * **2.4  二值化
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 19:59
 */
public class Lesson21AutoFitThreshold {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "text1.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ImageUI grayUI = new ImageUI();
        grayUI.imshow("灰度图", gray);

        //
        // 1.局部平均值法
        Mat avgBinMat = new Mat();
        // 最后一个参数很重要,0和10的差异很大.最后是常量C,可以先都减去一个值C再去取平均
        // adaptiveThreshold函数的倒数第二个参数务必是奇数
        Imgproc.adaptiveThreshold(gray, avgBinMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 10);
        ImageUI avgBinUI = new ImageUI();
        avgBinUI.imshow("局部平均值法处理的图像", avgBinMat);

        // 2.高斯平均值法,常量C很重要
        Mat gaussBinMat = new Mat();
        // 最后一个参数很重要,0和10的差异很大.最后是常量C,可以先都减去一个值C再去取平均.一般取10左右基本就可以把噪点去掉
        // adaptiveThreshold函数的倒数第二个参数务必是奇数,要不会报错.blocksize是一个b*b的方阵
        Imgproc.adaptiveThreshold(gray, gaussBinMat, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 45, 10);
        ImageUI gaussBinUI = new ImageUI();
        gaussBinUI.imshow("高斯平均值法处理的图像", gaussBinMat);
    }
}
