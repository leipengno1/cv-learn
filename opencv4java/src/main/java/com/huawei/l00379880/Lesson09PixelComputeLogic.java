package com.huawei.l00379880;

/**
 * 图像的逻辑操作,即与或非等
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson09PixelComputeLogic {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 默认是彩色读取
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("迪丽热巴", src);


        // 创建一个纯黑色的mat,即全是0,逻辑操作的图片类型必须相同
        Mat matMask = Mat.zeros(src.size(), src.type());
        // 构造一个矩形区域
        Rect rect = new Rect(150, 50, 150, 150);
        matMask.submat(rect).setTo(new Scalar(255, 255, 255));

        // 与操作,结果到dst中
        Mat dstAnd = new Mat();
        Core.bitwise_and(src, matMask, dstAnd);
        ImageUI andUI = new ImageUI();
        andUI.imshow("与操作", dstAnd);

        // 或操作,结果到dst中,有一个为1就为1,两个相同就为0
        Mat dstOr = new Mat();
        Core.bitwise_or(src, matMask, dstOr);
        ImageUI orUI = new ImageUI();
        orUI.imshow("或操作", dstOr);

        // 非操作
        Mat dstNot = new Mat();
        Core.bitwise_not(src, dstNot);
        ImageUI notUI = new ImageUI();
        notUI.imshow("非操作", dstNot);
    }

}
