package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 各种图片的模糊,均值模糊,中值模糊,自定义模糊
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-04 14:51
 */
public class Lesson12Blur {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 读入含有噪声的lena图像 lesson12noiselena
        Mat src = Imgcodecs.imread(rootPath + "lenanoise.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图像", src);

        // 1.均值模糊(均值卷积),效果是全局模糊
        Mat avgMat = new Mat();
        // 最后一个参数为卷积核,最好是奇数,数字越大模糊效果越强
        Imgproc.blur(src, avgMat, new Size(5, 5));
        ImageUI avgUI = new ImageUI();
        avgUI.imshow("均值模糊后的图像", avgMat);

        // 2.中值模糊,效果是局部模糊,中值模糊去局部噪声的效果非常好
        Mat meanMat = new Mat();
        Imgproc.medianBlur(src, meanMat, 5);
        ImageUI meanUI = new ImageUI();
        meanUI.imshow("中值模糊后的图像", meanMat);


        // 3. Filter2D函数非常重要,下面是其典型的三种应用
        // 3.1.自定义锐化,用于对上面模糊的图片进行锐化处理,可用于还原模糊的图片.构造卷积矩阵非常关键
        Mat selfSharpMat = new Mat();
        // 下面两行构造卷积矩阵
        Mat kSharpMat = new Mat(3, 3, CvType.CV_32FC1);
        // 初始化一个3*3的矩阵
        float[] dataSharp = new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0};
        // 用数组给上面的3*3的矩阵初始化
        kSharpMat.put(0, 0, dataSharp);
        // 最重要:自定义过滤器.
        // 对第二部得到的图像进行第二次的锐化过滤
        Imgproc.filter2D(meanMat, selfSharpMat, CvType.CV_8U, kSharpMat);
        ImageUI selfSharpUI = new ImageUI();
        selfSharpUI.imshow("自定义锐化过滤器处理后的图像", selfSharpMat);

        // 3.2 自定义均值模糊
        Mat selfAvgMat = new Mat();
        // 下面两行构造卷积矩阵
        Mat kAvgMat = new Mat(3, 3, CvType.CV_32FC1);
        // 初始化一个3*3的矩阵
        float[] dataAvg = new float[]{1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f};
        // 用数组给上面的3*3的矩阵初始化
        kAvgMat.put(0, 0, dataAvg);
        // 最重要:自定义均值模糊.
        Imgproc.filter2D(src, selfAvgMat, CvType.CV_8U, kAvgMat);
        ImageUI selfAvgUI = new ImageUI();
        selfAvgUI.imshow("自定义均值模糊处理后的图像", selfAvgMat);

        // 3.3 自定义边缘过过滤
        Mat selfEdgeMat = new Mat();
        // 下面两行构造卷积矩阵
        Mat kEdgeMat = new Mat(2, 2, CvType.CV_32FC1);
        // 初始化一个3*3的矩阵
        float[] dataEdge = new float[]{0, 1, -1, 0};
        // 用数组给上面的3*3的矩阵初始化
        kEdgeMat.put(0, 0, dataEdge);
        // 最重要:自定义边缘检测器.
        Imgproc.filter2D(src, selfEdgeMat, CvType.CV_8U, kEdgeMat);
        ImageUI selfEdgeUI = new ImageUI();
        selfEdgeUI.imshow("自定义边缘检测处理后的图像", selfEdgeMat);
    }
}
