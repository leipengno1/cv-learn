package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 直方图的均衡化,均衡化只能针对灰度图使用,不要对彩色图使用
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 16:11
 */
public class Lesson16HistogramBalance {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "flower.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ImageUI grayUI = new ImageUI();
        grayUI.imshow("灰度图", gray);

        Mat balanceMat = new Mat();
        Imgproc.equalizeHist(gray, balanceMat);
        ImageUI balanceUI = new ImageUI();
        balanceUI.imshow("均衡化之后的图", balanceMat);
    }
}
