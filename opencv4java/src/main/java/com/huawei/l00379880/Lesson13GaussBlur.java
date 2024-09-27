package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Random;

/**
 * 高斯模糊
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-04
 */
public class Lesson13GaussBlur {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        byte[] data = new byte[width * height * dims];
        src.get(0, 0, data);
        int b, g, r;
        // 对每个点加一下高斯噪声
        Random random = new Random();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // 产生0到50的随机数
                double bf = random.nextGaussian() * 30;
                double gf = random.nextGaussian() * 30;
                double rf = random.nextGaussian() * 30;
                // 转换为0到255的十进制数
                b = data[(row * width + col) * dims] & 0xff;
                g = data[(row * width + col) * dims + 1] & 0xff;
                r = data[(row * width + col) * dims + 2] & 0xff;
                // 加上高斯模糊的参数
                b = chomp(b + bf);
                g = chomp(g + gf);
                r = chomp(r + rf);
                // 把结果返回去
                data[(row * width + col) * dims] = (byte) b;
                data[(row * width + col) * dims + 1] = (byte) g;
                data[(row * width + col) * dims + 2] = (byte) r;
            }
        }
        // 把处理后的数据放回去
        src.put(0, 0, data);
        ImageUI gaussSelfUI = new ImageUI();
        gaussSelfUI.imshow("自定义高斯模糊后的图像", src);

        // 下面用OpenCV提供的高斯处理函数进行高斯铝箔,处理掉上面产生的高斯噪声.
        // 总结:椒盐噪声用中值滤波+锐化,高斯噪声用高斯滤波+锐化
        Mat gaussMat = new Mat();
        // 给高斯函数传参,处理上面已经处理过的有随机高斯噪声的图片,会发现像是打上严重马赛克了
        Imgproc.GaussianBlur(src, gaussMat, new Size(5, 5), 0);
        ImageUI gaussUI = new ImageUI();
        gaussUI.imshow("高斯滤波函数处理后的结果", gaussMat);


        // 最后,下面再进行锐化处理一下,尽量还原为清晰的样子,参考地上一个类
        Mat selfSharpMat = new Mat();
        // 下面两行构造卷积矩阵
        Mat kSharpMat = new Mat(3, 3, CvType.CV_32FC1);
        // 初始化一个3*3的矩阵
        float[] dataSharp = new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0};
        // 用数组给上面的3*3的矩阵初始化
        kSharpMat.put(0, 0, dataSharp);
        // 最重要:自定义过滤器.
        // 对高斯滤波得到的图像进行锐化过滤
        Imgproc.filter2D(gaussMat, selfSharpMat, CvType.CV_8U, kSharpMat);
        ImageUI selfSharpUI = new ImageUI();
        selfSharpUI.imshow("自定义锐化过滤器处理后的图像", selfSharpMat);
    }

    /**
     * 限制参数d 在0-255之间
     *
     * @param d 传入的double参数
     * @return 一个0到255之间的整数
     */
    public static int chomp(double d) {
        if (d > 255) {
            return 255;
        } else if (d < 0) {
            return 0;
        } else {
            return (int) d;
        }
    }
}
