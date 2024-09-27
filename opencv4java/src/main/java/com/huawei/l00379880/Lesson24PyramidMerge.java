package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 金字塔融合
 * @author      : 梁山广
 * @date        : 2017/11/6 20:08
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson24PyramidMerge {
    private static int level = 4;
    private static Mat smallestLevel;

    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat ma = Imgcodecs.imread(rootPath + "apple.png");
        Mat mb = Imgcodecs.imread(rootPath + "orange.png");
        Mat mc = Imgcodecs.imread(rootPath + "mask.png");
        ImageUI appleUI = new ImageUI();
        appleUI.imshow("苹果", ma);
        ImageUI orangeUI = new ImageUI();
        orangeUI.imshow("橘子", mb);

        List<Mat> la = buildLapacianPyramid(ma);
        Mat leftsmallestlevel = new Mat();
        smallestLevel.copyTo(leftsmallestlevel);

        List<Mat> lb = buildLapacianPyramid(mb);
        Mat rightsmalestlevel = new Mat();
        smallestLevel.copyTo(rightsmalestlevel);

        Mat mask = new Mat();
        Imgproc.cvtColor(mc, mask, Imgproc.COLOR_BGR2GRAY);

        List<Mat> maskPyramid = buildGaussianPyramid(mask);
        Mat smallmask = new Mat();
        smallestLevel.copyTo(smallmask);

        Mat currentImage = blend(leftsmallestlevel, rightsmalestlevel, smallmask);
        Imgcodecs.imwrite(rootPath + "mask_result.png", currentImage);
        ImageUI smallUI = new ImageUI();
        smallUI.imshow("最小金字塔混合图像", currentImage);

        List<Mat> ls = new ArrayList<>();
        for (int i = 0; i < level; i++) {
            Mat a = la.get(i);
            Mat b = lb.get(i);
            Mat m = maskPyramid.get(i);
            ls.add(blend(a, b, m));
        }

        Mat temp = new Mat();
        for (int i = level - 1; i >= 0; i--) {
            Imgproc.pyrUp(currentImage, temp, ls.get(i).size());
            Core.add(temp, ls.get(i), currentImage);
        }
        ImageUI blendUI = new ImageUI();
        blendUI.imshow("高斯算子图像重建", currentImage);
    }

    /**
     * 图像融合
     *
     * @param a
     * @param b
     * @param m
     * @return
     */
    private static Mat blend(Mat a, Mat b, Mat m) {
        int width = a.cols();
        int height = a.rows();
        Mat dst = Mat.zeros(a.size(), a.type());
        byte[] rgb1 = new byte[3];
        byte[] rgb2 = new byte[3];
        byte[] gray = new byte[1];
        int b1, g1, r1;
        int b2, g2, r2;
        int blue, green, red;
        int w = 0;
        float w1, w2;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                a.get(row, col, rgb1);
                b.get(row, col, rgb2);
                m.get(row, col, gray);
                w = gray[0] & 0xff;
                w2 = w / 255.0f;
                w1 = 1.0f - w2;

                b1 = rgb1[0] & 0xff;
                g1 = rgb1[1] & 0xff;
                r1 = rgb1[2] & 0xff;

                b2 = rgb2[0] & 0xff;
                g2 = rgb2[1] & 0xff;
                r2 = rgb2[2] & 0xff;

                blue = (int) (b1 * w1 + b2 * w2);
                green = (int) (g1 * w1 + g2 * w2);
                red = (int) (r1 * w1 + r2 * w2);
                dst.put(row, col, new byte[]{(byte) blue, (byte) green, (byte) red});
            }
        }
        return dst;
    }

    /**
     * 获取高斯算子,图片越来越小
     *
     * @param image
     * @return
     */
    public static List<Mat> buildGaussianPyramid(Mat image) {
        List<Mat> pyramid = new ArrayList<>();
        Mat copy = image.clone();
        pyramid.add(image.clone());
        Mat dst = new Mat();
        for (int i = 0; i < level; i++) {
            Imgproc.pyrDown(copy, dst, new Size(copy.cols() / 2, copy.rows() / 2));
            dst.copyTo(copy);
            pyramid.add(dst.clone());
        }
        smallestLevel = dst;
        return pyramid;
    }

    /**
     * 拉普拉斯算子,图片越来越大
     *
     * @param image
     * @return
     */
    public static List<Mat> buildLapacianPyramid(Mat image) {
        List<Mat> lp = new ArrayList<>();
        Mat temp = new Mat();
        Mat copy = image.clone();
        Mat dst = new Mat();
        for (int i = 0; i < level; i++) {
            Imgproc.pyrDown(copy, dst, new Size(copy.cols() / 2, copy.rows() / 2));
            Imgproc.pyrUp(dst, temp, copy.size());
            Mat lapaian = new Mat();
            Core.subtract(copy, temp, lapaian);
            lp.add(lapaian);
            copy = dst.clone();
        }
        smallestLevel = dst;
        return lp;
    }
}

