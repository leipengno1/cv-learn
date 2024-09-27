package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * 计算图片的直方图
 *
 * @author 梁山广
 * @eamil liangshanguang2@gmail.com
 * @date 2017-11-05 17:52
 */
public class CalculateHist {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src1 = Imgcodecs.imread(rootPath + "lena.png");
        Mat src2 = Imgcodecs.imread(rootPath + "lenanoise.png");
        ImageUI ui1 = new ImageUI();
        ui1.imshow("图片1", src1);
        ImageUI ui2 = new ImageUI();
        ui2.imshow("图片2", src2);

        Mat h1 = calculateHist(src1);
        Mat h2 = calculateHist(src2);

        // 直方图比较,下面是最常用的三种比较方式
        // 1.相似度比较(两个图片的相关度),越接近1,两个图片匹配度越高.结果范围为0~1
        double match1 = Imgproc.compareHist(h1, h2, Imgproc.CV_COMP_CORREL);
        // 2.卡方比较,值越小,图片相似度越高,值越大匹配度越低.结果范围为0~正无穷
        double match2 = Imgproc.compareHist(h1, h2, Imgproc.CV_COMP_CHISQR);
        // 3.巴斯举例法,值越接近0,匹配度越高,越接近1,相似度越低.结果范围为0~1
        double match3 = Imgproc.compareHist(h1, h2, Imgproc.CV_COMP_BHATTACHARYYA);
        System.out.println(match1);
        System.out.println(match2);
        System.out.println(match3);
    }

    /**
     * 计算出图片的直方图,把三通道的直方图合成一个通道,是老师对OpenCV官方函数的改进,OpenCV官方的calculateHist不好用(只能计算单通道的,而且参数很麻烦)
     *
     * @param src
     * @return 返回计算完的直方图Mat
     */
    public static Mat calculateHist(Mat src) {
        int width = src.cols();
        int height = src.rows();
        int dims = src.channels();
        byte[] data = new byte[width * height * dims];
        src.get(0, 0, data);
        //
        Mat h = Mat.zeros(16 * 16 * 16, 1, CvType.CV_32FC1);
        int bsize = 256 / 16;
        int b, g, r;
        int index = 0;
        float[] count = new float[1];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                b = data[(row * width + col) * dims] & 0xff;
                g = data[(row * width + col) * dims + 1] & 0xff;
                r = data[(row * width + col) * dims + 2] & 0xff;
                index = (b / bsize) * 16 * 16 + (g / bsize) * 16 + (r / bsize);
                h.get(index, 0, count);
                count[0]++;
                h.put(index, 0, count);
            }
        }
        return h;
    }
}
