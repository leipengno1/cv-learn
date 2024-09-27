package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 边缘检测
 * @author      : 梁山广
 * @date        : 2017/11/9 14:41
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson31FindContours {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "rice.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        demo2(src);
    }

    /**
     * 直接进行轮廓发现,没先进行边缘检测,这样的话会导致一些细微的小轮廓检测不到.检测粒度比较粗
     *
     * @param src
     */
    public static void demo1(Mat src) {
        Mat gray = new Mat();
        Mat binary = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // 也可以先高斯模糊(GaussianBlur)再进行二值化,这样可以去掉一些噪点,这里因为图像比较干净,故不需先模糊了
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        ImageUI binUI = new ImageUI();
        binUI.imshow("二值化之后的图像", binary);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        // RETR_XXXX有5个参数,注意区分哦
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));


        // 画出边框来并显示图像
        Mat result = src.clone();
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(result, contours, i, new Scalar(0, 0, 255), 2);
        }
        ImageUI resultUi = new ImageUI();
        resultUi.imshow("检测到边缘的图", result);
    }

    /**
     * 先进行边缘检测,然后把边缘检测的结果给轮廓发现函数,这样可以发现很多更小的轮廓,检测粒度比较细
     *
     * @param src 输入图像
     */
    public static void demo2(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 先对灰度图进行双边模糊
        Mat dst = new Mat();
        // 没有这个双边滤波的话,结果会有很多杂点的.所有双边滤波或者高斯模糊是很关键的
        Imgproc.bilateralFilter(gray, dst, 0, 50, 5);
        Mat edges = new Mat();
        // Canny边缘检测,把检测到的边缘在给轮廓检测函数就能大大提高边缘检测的准确度
        Imgproc.Canny(dst, edges, 30, 200);
        ImageUI edgesUI = new ImageUI();
        edgesUI.imshow("边缘提取图像", edges);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        // RETR_XXXX有5个参数,注意区分哦
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // 画出边框来并显示图像
        Mat result = src.clone();
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(result, contours, i, new Scalar(0, 255, 0));
        }
        ImageUI resultUi = new ImageUI();
        resultUi.imshow("先Canny边缘检测,再把结果给轮廓发现函数后计算得到的图", result);
    }
}
