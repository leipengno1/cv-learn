package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson08PixelCompute {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 默认是彩色读取
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("迪丽热巴", src);

        // 初始化一张黑的图片,各个像素点全都是原值
        Mat matBlack = Mat.zeros(src.size(), src.type());
        matBlack.setTo(new Scalar(50, 50, 50));

        // add
        Mat dstAdd = new Mat();
        // src和matBlack混合到dstAdd
        Core.add(src, matBlack, dstAdd);
        ImageUI addUI = new ImageUI();
        addUI.imshow("图像相加", dstAdd);

        // substract
        Mat dstSub = new Mat();
        // src和matBlack混合到dstSub
        Core.subtract(src, matBlack, dstSub);
        ImageUI subUI = new ImageUI();
        subUI.imshow("图像相减", dstSub);

        // 提高对比度:contrast,乘了之后,亮的地方更亮,暗的地方更暗了
        matBlack.setTo(new Scalar(2, 2, 2));
        Mat dstCon = new Mat();
        // src和matBlack混合到dstCon
        Core.multiply(src, matBlack, dstCon);
        ImageUI conUI = new ImageUI();
        conUI.imshow("提高对比度:图像相乘", dstCon);

        // 降低对比度:contrast,除了之后,亮的地方更亮,暗的地方更暗了
        matBlack.setTo(new Scalar(2, 2, 2));
        Mat dstDiv = new Mat();
        // src和matBlack混合到dstCon
        Core.divide(src, matBlack, dstDiv);
        ImageUI divUI = new ImageUI();
        divUI.imshow("降低对比度:图像相除", dstDiv);

        // 权重加减,提高对比度
        Mat dstWeight = new Mat();
        Core.addWeighted(src, 1.2, matBlack, 0.0, -10, dstWeight, src.type());
        ImageUI weightUI = new ImageUI();
        weightUI.imshow("权重混合", dstWeight);

        // 创建一个纯黑色的mat,即全是0
        Mat matMask = Mat.zeros(src.size(), CvType.CV_8UC1);
        // 截取一个矩形区域
        Rect rect = new Rect(100, 200, 100, 100);
    }

}
