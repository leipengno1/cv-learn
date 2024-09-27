package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 对象测量,计算图块的面积和周长,以像素单位.
 * @author      : 梁山广
 * @date        : 2017/11/9 15:20
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson32ContourMeasure {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "contours.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        measureObject(src);
    }

    /**
     * 计算指定图形的面积
     *
     * @param src
     */
    public static void measureObject(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 先对灰度图进行双边模糊
        Mat dst = new Mat();
        // 没有这个双边滤波的话,结果会有很多杂点的.所有双边滤波或者高斯模糊是很关键的
        Imgproc.bilateralFilter(gray, dst, 0, 50, 5);
        Mat edges = new Mat();
        // Canny边缘检测,把检测到的边缘在给轮廓检测函数就能大大提高边缘检测的准确度
        Imgproc.Canny(dst, edges, 30, 200);
        ImageUI edgesUI = new ImageUI();
        edgesUI.imshow("边缘提取图像", edges);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        // RETR_XXXX有5个参数,注意区分哦
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // 画出边框来并显示图像
        Mat result = src.clone();
        for (int i = 0; i < contours.size(); i++) {
            // 计算周长
            double arcLength = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);
            // 计算面积
            double area = Imgproc.contourArea(contours.get(i));
            String text = "length:" + (int) arcLength + " area:" + (int) area;
            // 计算几何矩,并据此得到中心和重心
            Moments moments = Imgproc.moments(contours.get(i));
            // 计算中心,并用圆点标出来
            int cx = (int) (moments.m10 / moments.m00);
            int cy = (int) (moments.m01 / moments.m00);
            // 最后一个参数为负数代表是实心的
            Imgproc.circle(result, new Point(cx, cy), 3, new Scalar(0, 255, 255), -1);
            Imgproc.drawContours(result, contours, i, new Scalar(0, 0, 255), 2);
            // 获取点的xy坐标
            int[] pt = new int[2];
            contours.get(i).get(0, 0, pt);
            Imgproc.putText(result, text, new Point(pt[0], pt[1]), Core.FONT_HERSHEY_PLAIN, 1, new Scalar(255, 255, 255));

            // 根据边数筛选不同类型的多边形
            MatOfPoint2f approxCurve = new MatOfPoint2f();
            // epsilon貌似越大越好
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), approxCurve, 4, true);
            // 输出检测到的各个多边形的边数,注意别写成cols哈
            System.out.println(approxCurve.rows());
            // 把图中的四边形标记出来.改成3、4、6可分别检验三角形、四边形、六边形,然后用把边框加粗显示出来.不过为啥圆是16边形呢
            if (approxCurve.rows() == 4) {
                Imgproc.drawContours(result, contours, i, new Scalar(0, 255, 0), 4);
            }
        }
        ImageUI resultUi = new ImageUI();
        resultUi.imshow("先Canny边缘检测,再把结果给轮廓发现函数后计算得到的图", result);
    }
}
