package com.huawei.l00379880;
/**
 * 泛洪填充,第一个函数,可以填充彩色图像
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-04
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lesson11ROIFloodColor {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("lena", src);


        // reatc无关紧要,哪怕是空的也无所谓
        Rect rect = new Rect(1, 1, src.cols(), src.rows());
        // 遮罩必须比原图像大两个像素.只能对二值图像进行填充
        Mat mask = Mat.zeros(src.rows() + 2, src.cols() + 2, CvType.CV_8UC1);
        // 下面的语句是核心,即对指定的区域开始泛洪填充,Point点的选择至关重要,下面是两个典型 FLOODFILL_MASK_ONLY 也是最后一个参数的可选类型,
        // 但是只对遮罩起作用,所以不太常用
        Imgproc.floodFill(src, mask, new Point(2, 2), new Scalar(0, 0, 255), rect,
                new Scalar(100, 100, 100), new Scalar(100, 100, 100), Imgproc.FLOODFILL_FIXED_RANGE);
        // 填充人像区域
        Imgproc.floodFill(src, mask, new Point(src.cols() / 2, src.rows() / 2), new Scalar(0, 255, 255), rect,
                new Scalar(100, 100, 100), new Scalar(100, 100, 100), Imgproc.FLOODFILL_FIXED_RANGE);
        ImageUI resultUI = new ImageUI();
        resultUI.imshow("填充后的图", src);
    }
}
