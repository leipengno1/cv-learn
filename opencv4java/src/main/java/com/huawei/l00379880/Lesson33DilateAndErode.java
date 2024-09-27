package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 图像的膨胀(Dilate)与腐蚀(Erode)
 *                1.膨胀的作用
 *                  1.1 对象大小增加一个像素(3*3)
 *                  1.2 平滑对象边缘
 *                  1.3 减少或者填充对象之间的距离
 *                2.腐蚀的作用
 *                  2.1 对象大小减少一个像素(3*3)
 *                  2.2 平滑对象边缘
 *                  2.3 弱化或者分割图像之间的半岛型连接
 * @author      : 梁山广
 * @date        : 2017/11/9 16:21
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson33DilateAndErode {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 图像是彩色还是单色都是可以的
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        // (一)膨胀
        Mat dilateSingle = new Mat();
        // MORPH_RECT(用矩形进行膨胀),类似还有MORPH_ELLIPSE(椭圆)、MORPH_CROSS(十字)等等.矩形最常用
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        // 单次膨胀
        Imgproc.dilate(src, dilateSingle, kernel);
        ImageUI dilateSingleUI = new ImageUI();
        dilateSingleUI.imshow("单次膨胀", dilateSingle);
        // 多次膨胀
        Mat dilateMany = new Mat();
        // Point用(0,0)一般就没问题了.iterations代表要迭代5次
        Imgproc.dilate(src, dilateMany, kernel, new Point(0, 0), 5);
        ImageUI dilateManyUI = new ImageUI();
        dilateManyUI.imshow("多次膨胀", dilateMany);

        // (二)腐蚀
        Mat erodeSingle = new Mat();
        Imgproc.erode(src, erodeSingle, kernel);
        ImageUI erodeSingleUI = new ImageUI();
        erodeSingleUI.imshow("单次腐蚀的结果", erodeSingle);
        Mat erodeMany = new Mat();
        Imgproc.erode(src, erodeMany, kernel, new Point(0, 0), 5);
        ImageUI erodeManyUI = new ImageUI();
        erodeManyUI.imshow("多次腐蚀的结果", erodeMany);
    }
}
