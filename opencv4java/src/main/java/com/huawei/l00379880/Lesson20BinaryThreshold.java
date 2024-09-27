package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 图像二值化,一般是借助于直方图进行二值化,有如下几种方法,12属于局部阈值法,3属于全局阈值法(下一讲讲)
 * 1.OTSU
 * 2.Triangle
 * 3.自适应阈值
 * * 3.1 平均值法
 * * 3.2 高斯法
 * 4.根据直方图自己观察确定
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 19:29
 */
public class Lesson20BinaryThreshold {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "test1.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ImageUI grayUI = new ImageUI();
        grayUI.imshow("灰度图", gray);

        // 1.手动确定阈值
        Mat binMat = new Mat();
        // 这种是手工确定的阈值,不是自动地,不好,Imgproc.THRESH_BINARY_INV可以二值化反过来
        Imgproc.threshold(gray, binMat, 127, 255, Imgproc.THRESH_BINARY);
        // Imgproc.threshold(gray, binMat, 127, 255, Imgproc.THRESH_BINARY_INV);
        ImageUI binUI = new ImageUI();
        binUI.imshow("手动确定阈值法", binMat);

        // 2.自动二值化阈值确定法

        // 2.1 OTSU,适合有多个波峰的,最常用.
        Mat binAutoOTSUMat = new Mat();
        // Imgproc.THRESH_BINARY_INV可以二值化反过来
        // 当最后一个参数"|Imgproc.THRESH_OTSU"后,则第三个参数thresh是多少就无所谓,都会被忽略而使用OTSU自动计算出来的thresh
        Imgproc.threshold(gray, binAutoOTSUMat, 127, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        // Imgproc.threshold(gray, binMat, 127, 255, Imgproc.THRESH_BINARY_INV);
        ImageUI otsuUI = new ImageUI();
        otsuUI.imshow("OTSU方法", binAutoOTSUMat);

        // 2.2 Triangle 三角形找高,前提是图像的直方图只有一个波峰,美女拿花的图片有两个波峰,所以不如OTSU效果好.多个波峰的还是用OTSU
        Mat binAutoTriangleMat = new Mat();
        // Imgproc.THRESH_BINARY_INV可以二值化反过来
        // 当最后一个参数"|Imgproc.THRESH_TRIANGLE"后,则第三个参数thresh是多少就无所谓,都会被忽略而使用三角形的高自动计算出来的thresh
        Imgproc.threshold(gray, binAutoTriangleMat, 127, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_TRIANGLE);
        // Imgproc.threshold(gray, binMat, 127, 255, Imgproc.THRESH_BINARY_INV);
        ImageUI triangleUI = new ImageUI();
        triangleUI.imshow("三角形找高法", binAutoTriangleMat);
    }
}
