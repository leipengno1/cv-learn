package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/***********************************************************
 * @Description : 分水岭算法的实现,基本流程如下:
 *                输入图像-->灰度化-->二值化-->距离变换-->寻找种子
 *                -->生成Marker-->分水岭变换-->输出图像-->完
 * @author      : 梁山广
 * @date        : 2017/11/13 20:55
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson37WaterSheds {
    public static void main(String[] args) {

        // 1.输入图像并进行适当的预处理
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 图像是彩色还是单色都是可以的.circles.jpg也可以试试
        Mat src = Imgcodecs.imread(rootPath + "cards.png");
        //Mat src = Imgcodecs.imread(rootPath + "circles.jpg");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        // 白色背景换成黑色背景.因为白色会干扰比较大所以为了方便处理图片一般都把图像背景转为黑色
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        byte[] data = new byte[width * height * dims];
        src.get(0, 0, data);
        int index = 0;
        int b, g, r;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                index = (row * width + col) * dims;
                b = data[index] & 0xff;
                g = data[index + 1] & 0xff;
                r = data[index + 2] & 0xff;
                // 当一个是纯黑的时候就把这个点改为纯白
                if (b == 255 && g == 255 && r == 255) {
                    data[index] = (byte) 0;
                    data[index + 1] = (byte) 0;
                    data[index + 2] = (byte) 0;
                }
            }
        }
        src.put(0, 0, data);
        ImageUI srcReverse = new ImageUI();
        srcReverse.imshow("背景取反后的图像", src);

        // 2.灰度化
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ImageUI grayUI = new ImageUI();
        grayUI.imshow("灰度图像", gray);

        // 3.二值化
        Mat bin = new Mat();
        // 注意灰度图像才可以进行二值化
        Imgproc.threshold(gray, bin, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        ImageUI binUI = new ImageUI();
        binUI.imshow("二值化图像", bin);

        // 4.距离变换
        Mat dist = new Mat();
        Imgproc.distanceTransform(bin, dist, Imgproc.DIST_L2, 3);
        // 转换到0~255之间的图像
        Core.normalize(dist, dist, 0, 255, Core.NORM_MINMAX);
        Mat dist_8u = new Mat();
        // 转换为彩色图像
        dist.convertTo(dist_8u, CvType.CV_8U);
        ImageUI distUI = new ImageUI();
        distUI.imshow("距离变换后的图像", dist_8u);

        // 5.寻找种子
        // 进行二值化和腐蚀,去除尖锐边角和噪声(0.4~1)*255范围进行二值化
        Imgproc.threshold(dist, dist, 102, 255, Imgproc.THRESH_BINARY);
        Mat erode = new Mat();
        // 获得腐蚀算子
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(13, 13));
        Imgproc.erode(dist, erode, kernel);
        Mat erode_8u = new Mat();
        erode.convertTo(erode_8u, CvType.CV_8U);
        ImageUI erodeUI = new ImageUI();
        erodeUI.imshow("腐蚀后的图像", erode_8u);

        // 6.生成marker
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(erode_8u, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        Mat markers = Mat.zeros(src.size(), CvType.CV_32SC1);
        // 随机颜色数组
        Scalar[] colors = new Scalar[contours.size()];
        Random random = new Random();
        for (int i = 0; i < contours.size(); i++) {
            // 把Marker绘制上
            Imgproc.drawContours(markers, contours, i, new Scalar(i + 1), -1);
            colors[i] = new Scalar(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        Imgproc.circle(markers, new Point(5, 5), 3, new Scalar(255), -1);


        // 7.分水岭变换
        Imgproc.watershed(src, markers);
        Mat mark = Mat.zeros(markers.size(), CvType.CV_8UC1);
        markers.convertTo(mark, CvType.CV_8U);
        Core.bitwise_not(mark, mark);
        ImageUI watershedUI = new ImageUI();
        watershedUI.imshow("分水岭变换后的结果", mark);

        // 给各个边框填色
        Mat dst = new Mat(src.size(), CvType.CV_8UC3);
        int[] idxv = new int[1];
        for (int row = 0; row < markers.rows(); row++) {
            for (int col = 0; col < markers.cols(); col++) {
                // 获取各个点的颜色赋值
                markers.get(row, col, idxv);
                if (idxv[0] > 0 && idxv[0] <= contours.size()) {
                    double[] rgb = colors[idxv[0] - 1].val;
                    dst.put(row, col, new byte[]{(byte) rgb[0], (byte) rgb[1], (byte) rgb[2]});
                } else {
                    dst.put(row, col, new byte[]{(byte) 0, (byte) 0, (byte) 0});
                }
            }
        }
        ImageUI dstUI = new ImageUI();
        dstUI.imshow("显示出分割边缘的图像", dst);
    }
}
