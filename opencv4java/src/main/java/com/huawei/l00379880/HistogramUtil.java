package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * 直方图绘制的API
 */
public class HistogramUtil {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);
//        showGrayHistogram(src);
//        showColorHisogramSeperate(src);
        showColorHisogramInOne(src);
    }

    /**
     * 绘制彩色图像的三通道直方图,一共绘制三个
     *
     * @param src 原始图片
     */
    public static void showColorHisogramSeperate(Mat src) {
        List<Mat> mv = new ArrayList<>();
        // 把三个通道的数据分别放到各自的mat中
        Core.split(src, mv);
        int offsetx = 50;
        int offsety = 350;
        Scalar[] colorTab = new Scalar[]{new Scalar(255, 0, 0), new Scalar(0, 255, 0), new Scalar(0, 0, 255)};
        String[] colorArr = new String[]{"蓝色(Blue)", "绿色(Green)", "红色(Red)"};
        int index = 0;
        for (Mat gray : mv) {
            Mat plot = Mat.zeros(400, 600, src.type());
            List<Mat> images = new ArrayList<>();
            images.add(gray);
            Mat mask = Mat.ones(src.size(), CvType.CV_8UC1);
            Mat hist = new Mat();
            Imgproc.calcHist(images, new MatOfInt(0), mask, hist, new MatOfInt(256), new MatOfFloat(0, 255));
            // 计算完毕要立马进行归一化
            Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);
            int height = hist.rows();
            System.out.println("rows:" + height);
            // 下面开始绘图
            float[] histdata = new float[256];
            // 把获取到的大图片给histdata
            hist.get(0, 0, histdata);
            Imgproc.line(plot, new Point(offsetx, 0), new Point(offsetx, offsety), new Scalar(255, 255, 255));
            Imgproc.line(plot, new Point(offsetx, offsety), new Point(600, offsety), new Scalar(255, 255, 255));
            for (int i = 0; i < height - 1; i++) {
                int y1 = (int) histdata[i];
                int y2 = (int) histdata[i + 1];
                // 绘制点图
                // Imgproc.line(plot, new Point(offsetx + i, offsety - y1), new Point(offsetx + i + 1, offsety - y2), new Scalar(0, 0, 255));
                // 绘制实心图
                Rect rect = new Rect();
                // 拉长步长
                rect.x = offsetx + i * 2;
                rect.y = offsety - y1;
                rect.width = 1;
                rect.height = y1;
                Imgproc.rectangle(plot, rect.tl(), rect.br(), colorTab[index]);
            }
            ImageUI plotUI = new ImageUI();
            plotUI.imshow(colorArr[index] + "通道的直方图:", plot);
            index++;
        }
    }

    /**
     * 绘制彩色图像的三通道直方图,三个绘制在一个里面
     *
     * @param src 原始图片
     */
    public static void showColorHisogramInOne(Mat src) {
        List<Mat> mv = new ArrayList<>();
        // 把三个通道的数据分别放到各自的mat中
        Core.split(src, mv);
        Mat plot = Mat.zeros(400, 600, src.type());
        int offsetx = 50;
        int offsety = 350;
        Imgproc.line(plot, new Point(offsetx, 0), new Point(offsetx, offsety), new Scalar(255, 255, 255));
        Imgproc.line(plot, new Point(offsetx, offsety), new Point(600, offsety), new Scalar(255, 255, 255));
        Scalar[] colorTab = new Scalar[]{new Scalar(255, 0, 0), new Scalar(0, 255, 0), new Scalar(0, 0, 255)};
        int index = 0;
        for (Mat gray : mv) {
            List<Mat> images = new ArrayList<>();
            images.add(gray);
            Mat mask = Mat.ones(src.size(), CvType.CV_8UC1);
            Mat hist = new Mat();
            Imgproc.calcHist(images, new MatOfInt(0), mask, hist, new MatOfInt(256), new MatOfFloat(0, 255));
            // 计算完毕要立马进行归一化
            Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);
            int height = hist.rows();
            System.out.println("rows:" + height);
            // 下面开始绘图
            float[] histdata = new float[256];
            // 把获取到的大图片给histdata
            hist.get(0, 0, histdata);
            for (int i = 0; i < height - 1; i++) {
                int y1 = (int) histdata[i];
                int y2 = (int) histdata[i + 1];
                // 拉长步长,绘出来的步长更长
                int x1 = i * 2;
                int x2 = (i + 1) * 2;
                // 绘制点图
                Imgproc.line(plot, new Point(offsetx + x1, offsety - y1), new Point(offsetx + x2, offsety - y2), colorTab[index]);
            }
            index++;
        }
        ImageUI plotUI = new ImageUI();
        plotUI.imshow("三通道的的直方图:", plot);
    }

    /**
     * 绘制彩色图像的灰度图的直方图
     *
     * @param src 原始图片
     */
    public static void showGrayHistogram(Mat src) {
        // 先转换为灰度图
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        ImageUI grayUI = new ImageUI();
        grayUI.imshow("原图的灰度图", gray);
        List<Mat> images = new ArrayList<>();
        images.add(gray);
        Mat mask = Mat.ones(src.size(), CvType.CV_8UC1);
        Mat hist = new Mat();
        Imgproc.calcHist(images, new MatOfInt(0), mask, hist, new MatOfInt(256), new MatOfFloat(0, 255));
        // 计算完毕要立马进行归一化
        Core.normalize(hist, hist, 0, 255, Core.NORM_MINMAX);
        int height = hist.rows();
        System.out.println("rows:" + height);
        // 下面开始绘图
        Mat plot = Mat.zeros(400, 600, src.type());
        float[] histdata = new float[256];
        // 把获取到的大图片给histdata
        hist.get(0, 0, histdata);
        int offsetx = 50;
        int offsety = 350;
        Imgproc.line(plot, new Point(offsetx, 0), new Point(offsetx, offsety), new Scalar(255, 255, 255));
        Imgproc.line(plot, new Point(offsetx, offsety), new Point(600, offsety), new Scalar(255, 255, 255));
        for (int i = 0; i < height - 1; i++) {
            int y1 = (int) histdata[i];
            int y2 = (int) histdata[i + 1];
            // 绘制点图
            // Imgproc.line(plot, new Point(offsetx + i, offsety - y1), new Point(offsetx + i + 1, offsety - y2), new Scalar(0, 0, 255));
            // 绘制实心图
            Rect rect = new Rect();
            // 拉长步长
            rect.x = offsetx + 2 * i;
            rect.y = offsety - y1;
            rect.width = 1;
            rect.height = y1;
            Imgproc.rectangle(plot, rect.tl(), rect.br(), new Scalar(255, 255, 255));
        }
        ImageUI plotUI = new ImageUI();
        plotUI.imshow("灰度图的直方图:", plot);
    }
}
