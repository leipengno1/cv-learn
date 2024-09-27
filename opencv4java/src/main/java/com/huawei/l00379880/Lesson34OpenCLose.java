package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 开(先腐蚀后膨胀)闭(先膨胀后腐蚀)操作
 *                开闭操作最好都是针对二值图像
 * @author      : 梁山广
 * @date        : 2017/11/9 18:38
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson34OpenCLose {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 图像是彩色还是单色都是可以的
        // Mat src = Imgcodecs.imread(rootPath + "bin2.png"); //这张图片用于demoOpenClose函数
        // Mat src = Imgcodecs.imread(rootPath + "morph01.png"); // 用于验证detectLine函数

        Mat src = Imgcodecs.imread(rootPath + "morph02.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        //demoOpenClose(src);
        //detectLine(src);
        removeLines(src);
    }


    /**
     * 针对bin2.png进行图片的开闭操作,用于去噪(开)和模糊掉大长方形内的小圆圈(闭操作)
     *
     * @param src
     */
    public static void demoOpenClose(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);

        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        // (一)开操作
        // 单次开操作
        Mat dstSingleOpen = new Mat();
        Imgproc.morphologyEx(binary, dstSingleOpen, Imgproc.MORPH_OPEN, kernel);
        ImageUI singleOpenUI = new ImageUI();
        singleOpenUI.imshow("单次开操作(先膨胀后腐蚀)", dstSingleOpen);
        // 多次开操作
        Mat dstManyOpen = new Mat();
        Imgproc.morphologyEx(binary, dstManyOpen, Imgproc.MORPH_OPEN, kernel, new Point(0, 0), 5);
        ImageUI mangOpenUI = new ImageUI();
        mangOpenUI.imshow("多次开操作", dstManyOpen);

        // (二)闭操作
        // 单次闭操作
        Mat dstSingleClose = new Mat();
        Imgproc.morphologyEx(binary, dstSingleClose, Imgproc.MORPH_CLOSE, kernel);
        ImageUI singleCloseUI = new ImageUI();
        singleCloseUI.imshow("单次闭操作(先腐蚀后膨胀)", dstSingleClose);
        // 多次开操作
        Mat dstManyClose = new Mat();
        Imgproc.morphologyEx(binary, dstManyClose, Imgproc.MORPH_CLOSE, kernel, new Point(0, 0), 5);
        ImageUI mangCloseUI = new ImageUI();
        mangCloseUI.imshow("多次闭操作", dstManyClose);
    }

    /**
     * 针对morph01.png,通过调整threshold的ksize参数,运用开闭操作进行直线提取
     *
     * @param src
     */
    public static void detectLine(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        // 腐蚀操作背景必须是黑色的,所以用THRESH_BINARY_INV
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);

        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        //new Size(40, 1) 检测横线,new Size(1, 40))检测竖线,具体为啥自己体会
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 40));
        Mat dst = new Mat();
        Imgproc.morphologyEx(binary, dst, Imgproc.MORPH_OPEN, kernel);

        ImageUI ui = new ImageUI();
        ui.imshow("直线检测", dst);
    }

    /**
     * 用于去除图片中的干扰线条,在验证码识别中很常用地,举例图片:morph02.png  通过调整new Size(x,y)中的xy即可去掉干扰的杂线
     *
     * @param src
     */
    public static void removeLines(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        // 腐蚀操作背景必须是黑色的,所以用THRESH_BINARY_INV
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);

        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        //多调整下new Size(xx,x)参数可以做很多事情的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dst = new Mat();
        Imgproc.morphologyEx(binary, dst, Imgproc.MORPH_OPEN, kernel);

        ImageUI ui = new ImageUI();
        ui.imshow("去除干扰直线", dst);
    }

}
