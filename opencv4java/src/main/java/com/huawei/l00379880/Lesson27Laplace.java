package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/***********************************************************
 * @Description : 图像的二阶倒数----拉普拉斯算子
 * @author      : 梁山广
 * @date        : 2017/11/8 20:31
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson27Laplace {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);
        laplaceDemo(src);
        laplaceFunc(src);
    }

    /**
     * 自定义拉普拉斯运算进行图像二阶求导
     *
     * @param src
     */
    public static void laplaceDemo(Mat src) {
        Mat kernel = new Mat(3, 3, CvType.CV_32SC1);
        // data数组是指进行拉普拉斯运算的算子(多项式的参数)
//        int[] data = new int[]{0, 1, 0, 1, -4, 1, 0, 1, 0};
        int[] data = new int[]{1, 1, 1, 1, -8, 1, 1, 1, 1};
        // 给算子赋值
        kernel.put(0, 0, data);

        Mat dst = new Mat();
        // 利用算子矩阵进行拉普拉斯运算.2D就是指求二阶导数,一定要用CV_32F,这样才能保证精度.不要用8U
        Imgproc.filter2D(src, dst, CvType.CV_32F, kernel);
        // 把负值进行绝对值转化,要不无法在图片中显示负像素地
        Core.convertScaleAbs(dst, dst);
        Mat image = new Mat();
        // 缩放到0~255的范围内
        Core.normalize(dst, dst, 0, 255, Core.NORM_MINMAX);
        // 可以把矩阵转换为彩色图像了
        dst.convertTo(image, CvType.CV_8UC3);
        ImageUI ui = new ImageUI();
        ui.imshow("拉普拉斯算子的结果,提取出边缘来", image);
    }

    /**
     * 用官方的自带函数进行求导
     *
     * @param src
     */
    public static void laplaceFunc(Mat src) {
        Mat kernel = new Mat(3, 3, CvType.CV_32SC1);
        // data数组是指进行拉普拉斯运算的算子(多项式的参数)
//        int[] data = new int[]{0, 1, 0, 1, -4, 1, 0, 1, 0};
        int[] data = new int[]{1, 1, 1, 1, -8, 1, 1, 1, 1};
        // 给算子赋值
        kernel.put(0, 0, data);

        Mat dst = new Mat();
        // 用官方自带的函数取代自定义的算子矩阵,和上面的类似,ksize是算子矩阵的元素个数,
        // 一定要是奇数才有中心点,比上面的自定义的要好.增大ksize,可以使印记加深,一定要是奇数哦
        // 一定要用CV_32F,这样才能保证精度.不要用8U
        Imgproc.Laplacian(src, dst, CvType.CV_32F, 5, 1.0, 0);
        Mat image = new Mat();
        // 缩放到0~255的范围内
        Core.normalize(dst, dst, 0, 255, Core.NORM_MINMAX);
        // 可以把矩阵转换为彩色图像了
        dst.convertTo(image, CvType.CV_8UC3);
        ImageUI ui = new ImageUI();
        ui.imshow("拉普拉斯算子的结果,提取出边缘来", image);
    }
}
