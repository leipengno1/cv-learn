package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 其他常见的形态学处理方法
 * @author      : 梁山广
 * @date        : 2017/11/9 19:53
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson35TopBlackHatAndGradient {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 图像是彩色还是单色都是可以的
        Mat src = Imgcodecs.imread(rootPath + "circles.jpg");
        // Mat src = Imgcodecs.imread(rootPath + "dilireba.png"); // 试试彩色图像的形态学梯度,效果非常好
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        // blackhatDemo(src);
        // 内部、中部、外部的差别看起来不大,但是圆的半径是不一样的,半径关系为:外>中>内
        // gradientBasicDemo(src);
        // gradientInternalDemo(src);
        gradientExternalDemo(src);
    }

    /**
     * 开操作与源图像的差值操作,例子是morph.png
     *
     * @param src
     */
    public static void tophatDemo(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Mat dst = new Mat();
        Imgproc.morphologyEx(binary, dst, Imgproc.MORPH_TOPHAT, kernel);

        ImageUI tophatUI = new ImageUI();
        tophatUI.imshow("TpoHat算法处理后的图像", dst);
    }

    /**
     * 闭操作与源图像的差值操作,例子是bin3.png
     *
     * @param src
     */
    public static void blackhatDemo(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Mat dst = new Mat();
        Imgproc.morphologyEx(binary, dst, Imgproc.MORPH_BLACKHAT, kernel);

        ImageUI tophatUI = new ImageUI();
        tophatUI.imshow("TpoHat算法处理后的图像", dst);
    }

    /**
     * 梯度测试: circles.jpg
     *  基本梯度
     * 基本梯度是用膨胀后的图像减去腐蚀后的图像得到差值
     * 图像，称为梯度图像也是opencv中支持的计算形态学
     * 梯度的方法，而此方法得到梯度有被称为基本梯度。
     * <p>
     *
     * @param src
     */
    public static void gradientBasicDemo(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dst = new Mat();
        // 第一个参数可以使灰度图像,也可以是彩色图像
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_GRADIENT, kernel);

        ImageUI tophatUI = new ImageUI();
        tophatUI.imshow("Gradient梯度图像", dst);
    }

    /**
     * 以circles.jpg为例
     * <p>
     *  内部梯度
     * 是用原图像减去腐蚀之后的图像得到差值图像，称为图
     * 像的内部梯度
     * <p>
     *
     * @param src
     */
    public static void gradientInternalDemo(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        Mat dst = new Mat();
        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat inter = new Mat();
        // 下面两行为核心原理
        Imgproc.erode(src, dst, kernel);
        Core.subtract(src, dst, inter);

        ImageUI tophatUI = new ImageUI();
        tophatUI.imshow("Gradient梯度图像", inter);
    }

    /**
     * 以circles.jpg为例
     * <p>
     *  外部梯度
     * 图像膨胀之后再减去原来的图像得到的差值图像，称为
     * 图像的外部梯度
     *
     * @param src
     */
    public static void gradientExternalDemo(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        Mat dst = new Mat();
        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat external = new Mat();
        // 下面两行为核心原理
        Imgproc.dilate(src, dst, kernel);
        Core.subtract(dst, src, external);

        ImageUI tophatUI = new ImageUI();
        tophatUI.imshow("Gradient梯度图像", external);
    }
}
