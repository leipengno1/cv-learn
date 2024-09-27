package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 霍夫直线检测,前提条件:已经完成边缘检测(可以利用上节的Canny算法)
 * @author      : 梁山广
 * @date        : 2017/11/9 10:25
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson29HoughLineTransform {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "lines.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        Mat edges = new Mat();
        // 参数调整看效果
        Imgproc.Canny(src, edges, 50, 150, 3, true);

        ImageUI edgesUI = new ImageUI();
        edgesUI.imshow("显示边缘检测的结果", edges);

        Mat lines = new Mat();
        // 参数调整看效果
        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180.0, 50, 10, 10);

        Mat result = Mat.zeros(src.size(), src.type());
        for (int i = 0; i < lines.rows(); i++) {
            int[] online = new int[4];
            lines.get(i, 0, online);
            Imgproc.line(result, new Point(online[0], online[1]), new Point(online[2], online[3]),
                    new Scalar(0, 0, 255), 2, 8, 0);
            ImageUI houghUI = new ImageUI();
            houghUI.imshow("霍夫直线检测结果", result);

        }
    }
}
