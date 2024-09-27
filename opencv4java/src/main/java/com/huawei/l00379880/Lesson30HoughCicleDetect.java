package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 霍夫圆检测
 *                1.因为霍夫圆检测对噪声比较敏感,所以首先要对图像
 *                  做中值滤波
 *                2.基于效率考虑,OpenCV中实现的霍夫变换圆检测是基
 *                  于图像梯度的实现,具体分为两步:
 *                  2.1 边缘检测,发现可能的圆心
 *                  2.2 基于第一步的基础上从候选圆心开始计算最佳半
 *                      径大小
 * @author      : 梁山广
 * @date        : 2017/11/9 11:24
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson30HoughCicleDetect {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "circles.jpg");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        Mat gray = new Mat();
        // 转换为灰度图
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // 高斯模糊,去噪声
        Imgproc.GaussianBlur(gray, gray, new Size(3, 3), 0);

        //检测圆
        Mat circles = new Mat();
        // 调整最后两个参数可以检测极大或极小的圆.比如把maxRadius由50调成00才能检测到circles.jpg里面的下面两个大圆
        Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, 10, 150, 50, 10, 100);
        Mat result = src.clone();
        for (int i = 0; i < circles.cols(); i++) {
            float[] info = new float[3];
            circles.get(0, i, info);
            // 第二个参数为圆心,第三个参数为半径
            Imgproc.circle(result, new Point(info[0], info[1]), (int) info[2], new Scalar(0, 255, 0), 2, 8, 0);
        }
        ImageUI resultUI = new ImageUI();
        resultUI.imshow("霍夫圆检测", result);
    }
}
