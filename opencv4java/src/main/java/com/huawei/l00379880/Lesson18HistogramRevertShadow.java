package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * 直方图方向投影,用于根据局部特征找出所有符合此特征的原始图片的区域
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 17:55
 */
public class Lesson18HistogramRevertShadow {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "target.png");
        Mat sample = Imgcodecs.imread(rootPath + "sample.png");
        ImageUI srcUI = new ImageUI();
        srcUI.imshow("原始图片", src);
        ImageUI sampleUI = new ImageUI();
        sampleUI.imshow("采样图片", sample);


        // sample图像的直方图化处理
        Mat sampleHSV = new Mat();
        Imgproc.cvtColor(sample, sampleHSV, Imgproc.COLOR_BGR2HSV);
        Mat mask = Mat.ones(sample.size(), CvType.CV_8UC1);
        Mat mHist = new Mat();
        // 直接用
        Imgproc.calcHist(Arrays.asList(sampleHSV), new MatOfInt(0, 1), mask, mHist, new MatOfInt(30, 32), new MatOfFloat(0, 179, 0, 255));

        Mat srcHSV = new Mat();
        Imgproc.cvtColor(src, srcHSV, Imgproc.COLOR_BGR2HSV);
        Mat dst = new Mat();
        // mHist上面已经是HSV空间了,此处正好可以直接适用
        Imgproc.calcBackProject(Arrays.asList(srcHSV), new MatOfInt(0, 1), mHist, dst, new MatOfFloat(0, 179, 0, 255), 1);
        // 统一规划0~255之间
        Core.normalize(dst, dst, 0, 255, Core.NORM_MINMAX);

        ImageUI dstUI = new ImageUI();
        dstUI.imshow("最终的反向投影图", dst);
    }
}
