package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

/**
 * 超大图像的二值化
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 21:14
 */
public class Lesson22BigImgBinnary {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "case9.jpg");

        //图片太大没法完全显示,所以需要先进性图片缩小进行效果查看,但是处理的图片是实际的图片
        Mat smallSrc = new Mat();
        Imgproc.resize(src, smallSrc, new Size(src.cols() / 5, src.rows() / 5));
        ImageUI smallUI = new ImageUI();
        smallUI.imshow("缩小16倍的图片", smallSrc);

        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Mat dst = new Mat();
        // 下面用自定义的方法进行二值化处理
        int width = gray.cols();
        int height = gray.rows();
        // 想改善效果可以适当减小block,比如从256到128
        int block = 128;
        Rect rect = new Rect();
        for (int y = 0; y < height; y += block) {
            for (int x = 0; x < width; x += block) {
                rect.x = x;
                rect.y = y;
                rect.width = block;
                rect.height = block;
                // 防止宽和高太大
                if (rect.x + block >= width) {
                    // 剩下的宽度都给rect的width
                    rect.width = width - rect.x;
                }
                if (rect.y + block >= height) {
                    // 剩下的高度都给rect的height
                    rect.height = height - rect.y;
                }
                Mat roi = gray.submat(rect);
                MatOfDouble means = new MatOfDouble();
                MatOfDouble dev = new MatOfDouble();
                Core.meanStdDev(roi, means, dev);
                double[] means1 = new double[1];
                means.get(0, 0, means1);
                double[] dev1 = new double[1];
                dev.get(0, 0, dev1);
                System.out.println("means:" + means1[0]);
                System.out.println("dev:" + dev1[0]);
                if (dev1[0] < 10) {
                    // 小于10就认为是0
                    byte[] data = new byte[roi.rows() * roi.cols()];
                    roi.get(0, 0, data);
                    Arrays.fill(data, (byte) 255);
                    roi.put(0, 0, data);
                    continue;
                }
                Imgproc.threshold(roi, dst, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
                dst.copyTo(roi);
            }
        }
        // 图片写入文件
        Imgcodecs.imwrite(rootPath + "case9_binary.jpg", gray);

    }
}
