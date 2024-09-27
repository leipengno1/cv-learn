package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 图像的一阶导数,用于检测图像边缘,Scharr算子,和
 *                Sobel算子只有名字不同.别的都完全一样.Scharr的
 *                感觉更明显、细致一些.Sobel线条粗一些
 * @author      : 梁山广
 * @date        : 2017/11/8 11:45
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson26Scharr {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        // Sobel 算子的x轴,并把图显示出来
        Mat xgrad = new Mat();
        // 求x方向的梯度
        Imgproc.Scharr(src, xgrad, CvType.CV_32F, 1, 0);
        // 梯度有负数,所以用绝对值函数转换为正数
        Core.convertScaleAbs(xgrad, xgrad);
        Mat ximage = new Mat();
        // 转换到0~255之间
        Core.normalize(xgrad, xgrad, 0, 255, Core.NORM_MINMAX);
        xgrad.convertTo(ximage, CvType.CV_8UC3);
        ImageUI xgradUI = new ImageUI();
        xgradUI.imshow("X 方向的Scharr算子", xgrad);

        // Sobel 算子的y轴,并把图显示出来
        Mat ygrad = new Mat();
        Imgproc.Scharr(src, ygrad, CvType.CV_32F, 0, 1);
        // 梯度有负数,所以用绝对值函数转换为正数
        Core.convertScaleAbs(ygrad, ygrad);
        Mat yimage = new Mat();
        // 转换到0~255之间
        Core.normalize(ygrad, ygrad, 0, 255, Core.NORM_MINMAX);
        xgrad.convertTo(yimage, CvType.CV_8UC3);
        ImageUI ygradUI = new ImageUI();
        ygradUI.imshow("Y 方向的Scharr算子", ygrad);

        // 把x和y轴的结合在一起
        Mat grad = new Mat();
        // 把x和y进行融合
        Core.add(xgrad, ygrad, grad);
        // 把结果转化到0~255的范围
        Core.normalize(grad, grad, 0, 255, Core.NORM_MINMAX);
        Mat xyimage = new Mat();
        // 转化到0~255之后就可以用彩色图表现出来
        grad.convertTo(xyimage, CvType.CV_8UC3);
        ImageUI gradUI = new ImageUI();
        gradUI.imshow("X和Y轴结合起来的图", xyimage);
    }
}
