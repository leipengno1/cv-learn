package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 分水岭算法算法的基础:距离变换
 * @author      : 梁山广
 * @date        : 2017/11/13 20:10
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson36DistanceTransform {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 图像是彩色还是单色都是可以的
        Mat src = Imgcodecs.imread(rootPath + "morph.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值图像", binary);


        // 可以释放调整第二个参数的矩阵进行效果调整,比如调整成5*5的
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dst = new Mat();
        Imgproc.distanceTransform(binary, dst, Imgproc.DIST_L2, 3);
        // 值的范围转换为0~255
        Core.normalize(dst, dst, 0, 255, Core.NORM_MINMAX);
        Mat dst_8u = new Mat();
        // 转换为8位的彩色图
        dst.convertTo(dst_8u, CvType.CV_8U);

        ImageUI dstUI = new ImageUI();
        dstUI.imshow("距离转换后的图像", dst_8u);
    }
}
