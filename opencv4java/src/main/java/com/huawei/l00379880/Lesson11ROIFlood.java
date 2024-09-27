package com.huawei.l00379880;
/**
 * 泛洪填充,第一个函数,只能填充二值图像
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lesson11ROIFlood {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("lena", src);

        Mat gray = new Mat();
        Mat bin = new Mat();
        // 把原图转换为灰度图像
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // 把原图转换为二值图像,用Threshold函数
        Imgproc.threshold(gray, bin, 127, 255, Imgproc.THRESH_BINARY);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二进制图像", bin);

        // 遮罩必须比原图像大两个像素.只能对二值图像进行填充
        Mat mask = Mat.zeros(src.rows() + 2, src.cols() + 2, CvType.CV_8UC1);
        // 把灰度图转化为彩色图,方便看出填充的效果来
        Imgproc.cvtColor(bin, bin, Imgproc.COLOR_GRAY2BGR);
        // 下面的语句是核心,即对指定的区域开始泛洪填充,Point点的选择至关重要,下面是两个典型
        Imgproc.floodFill(bin, mask, new Point(2, 2), new Scalar(0, 0, 255));
        // 填充人像区域
        Imgproc.floodFill(bin, mask, new Point(src.cols() / 2, src.rows() / 2), new Scalar(0, 255, 255));
        ImageUI resultUI = new ImageUI();
        resultUI.imshow("填充后的图", bin);
    }
}
