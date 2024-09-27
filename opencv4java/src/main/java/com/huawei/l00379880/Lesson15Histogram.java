package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * 图像直方图的绘制
 */
public class Lesson15Histogram {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);
        // 下面是三种方法,分别是灰度图、三通道分开的彩色图、三通道在一起的彩色图.可以自己都分别看一看
        HistogramUtil.showGrayHistogram(src);
        HistogramUtil.showColorHisogramSeperate(src);
        HistogramUtil.showColorHisogramInOne(src);
    }
}
