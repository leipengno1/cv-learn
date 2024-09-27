package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 常用的图像控件的转换
 */
public class Lesson06SpaceChange {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 默认是彩色读取
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        ImageUI ui = new ImageUI();
        ui.imshow("lena显示", src);

        // 下面进行图像空间转换
        Mat gray = new Mat();
        Mat hsv = new Mat();
        Mat hls = new Mat();
        Mat yuv = new Mat();
        // 灰度图方便进行图像定位
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // HSV方便进行滤镜,因为HSV空间下色差明显,对比度大,容易区分,HSV也是0~255区间的
        Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(src, hls, Imgproc.COLOR_BGR2HLS);
        Imgproc.cvtColor(src, yuv, Imgproc.COLOR_BGR2YUV);


        ImageUI grayUI = new ImageUI();
        grayUI.imshow("gray", gray);

        ImageUI hsvUI = new ImageUI();
        hsvUI.imshow("hsv", hsv);

        ImageUI hlsUI = new ImageUI();
        hlsUI.imshow("hls", hls);

        ImageUI yuvUI = new ImageUI();
        yuvUI.imshow("yuv", yuv);

    }
}
