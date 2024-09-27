package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 边缘保留滤波,包括高斯双边和均值迁移.,属于非线性滤波.非常好的美化效果
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-04
 */
public class Lesson14EPF {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "example.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);

        // 1.双边模糊滤波,效果超级牛逼
        Mat biLiteralMat = new Mat();
        // 参数怎么选很有讲究的,参数d建议选0。
        Imgproc.bilateralFilter(src, biLiteralMat, 0, 100, 15);
        ImageUI biliteralUI = new ImageUI();
        biliteralUI.imshow("双边模糊滤波", biLiteralMat);


        // 2.均值迁移
        Mat meanShiftMat = new Mat();
        // 参数调整:调大一些即可,第一个小于第二个参数
        Imgproc.pyrMeanShiftFiltering(src, meanShiftMat, 20, 50);
        ImageUI meanShiftUI = new ImageUI();
        meanShiftUI.imshow("均值迁移滤波", meanShiftMat);
    }
}
