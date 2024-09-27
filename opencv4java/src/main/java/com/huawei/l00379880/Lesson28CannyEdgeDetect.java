package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : Canny边缘检测和提取算法,五个步骤如下
 *                1.高斯模糊---GaussianBlur
 *                2.灰度转换---cvtColor
 *                3.计算梯度---Sobel/Scharr
 *                4.非最大信号抑制
 *                5.高低阈值输出二值图像
 * @author      : 梁山广
 * @date        : 2017/11/8 21:16
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson28CannyEdgeDetect {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        // (一)第一种边缘检测方法
        // 1.高斯模糊---GaussianBlur
        Mat dst = new Mat();
        Imgproc.GaussianBlur(src, dst, new Size(3, 3), 0);
        // 2.灰度转换---cvtColor
        Mat gray = new Mat();
        Imgproc.cvtColor(dst, gray, Imgproc.COLOR_BGR2GRAY);
        // 3、4、5一起,进行Canny边缘检测
        Mat output = new Mat();
        // 根据灰度图进行Canny边缘检测,是否选L2gradient
        Imgproc.Canny(gray, output, 50, 150, 3, true);
        ImageUI result = new ImageUI();
        result.imshow("利用灰度图进行Canny边缘检测的结果", output);

        // (二)第二种边缘检测方法,利用彩色图像做Canny边缘检测效果会很差
        // Sobel 算子的x轴
        Mat xgrad = new Mat();
        // 求x方向的梯度.src替换为gray效果更好
        Imgproc.Sobel(gray, xgrad, CvType.CV_16S, 1, 0);
        // Sobel 算子的y轴,src替换为gray效果更好.所以对灰度图做边缘检测而尽量不用彩色图是有道理的
        Mat ygrad = new Mat();
        Imgproc.Sobel(gray, ygrad, CvType.CV_16S, 0, 1);
        // 获取结果
        Mat result2Mat = new Mat();
        Imgproc.Canny(xgrad, ygrad, result2Mat, 50, 150);
        ImageUI result2UI = new ImageUI();
        result2UI.imshow("利用Sobel算子的结果进行Canny边缘检测", result2Mat);

        // (三)直接对彩色图像进行Canny检测
        Mat result3Mat = new Mat();
        Imgproc.Canny(src, result3Mat, 50, 100);
        ImageUI result3UI = new ImageUI();
        result3UI.imshow("直接对彩色图像进行边缘检测",result3Mat);
        Mat edges = new Mat();
        // 把边缘检测结果转换为彩色图
        src.copyTo(edges, output);
        ImageUI colorUI = new ImageUI();
        colorUI.imshow("边缘检测结果的彩色图像", edges);

    }
}
