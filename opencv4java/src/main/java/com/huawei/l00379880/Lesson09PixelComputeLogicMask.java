package com.huawei.l00379880;

/**
 * 图像的逻辑操作,即与或非等,遮罩体 的深度应用
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

public class Lesson09PixelComputeLogicMask {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 默认是彩色读取
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("迪丽热巴", src);


        // 创建一个纯黑色的mat,即全是0,尺寸必须和待融合的图片完全相同
        Mat matMask = Mat.zeros(src.size(), CvType.CV_8UC1);
        // 构造一个矩形区域,并添加到上面的图形中
        Rect rect = new Rect(120, 20, 200, 250);
        // 添加一个单通道全白的(全为1)的小矩形,用于圈出脸
        matMask.submat(rect).setTo(new Scalar(255));

        // 创建融合对象,非操作
        Mat dstMask = new Mat();
        // 只有在mask区域里的全1小白矩形内的像素点才进行取反
        Core.bitwise_not(src, dstMask, matMask);
        ImageUI notMaskUI = new ImageUI();
        notMaskUI.imshow("遮罩内的非操作", dstMask);

        // 创建融合对象,与操作
        Mat redMaskMat = new Mat(src.size(), src.type());
        redMaskMat.setTo(new Scalar(0, 0, 255));
        Mat dstSingleRedAnd = new Mat();
        // 只有在mask区域里的全1小白矩形内的像素点才会进行src和readMaskMat的与操作,然后结果放到dstSingleRed中
        Core.bitwise_and(src, redMaskMat, dstSingleRedAnd, matMask);
        ImageUI readUIAnd = new ImageUI();
        readUIAnd.imshow("遮罩内的与操作", dstSingleRedAnd);
        Mat dstSingleRedOr = new Mat();
        // 只有在mask区域里的全1小白矩形内的像素点才会进行src和readMaskMat的与操作,然后结果放到dstSingleRed中
        Core.bitwise_or(src, redMaskMat, dstSingleRedOr, matMask);
        ImageUI readUIOr = new ImageUI();
        readUIOr.imshow("遮罩内的或操作", dstSingleRedOr);
    }

}
