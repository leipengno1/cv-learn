package com.huawei.l00379880;
/**
 * ROI:range of interest 感兴趣区域的获取
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Lesson10ROI {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("lena", src);

        // 开始截取图像
        Rect rect = new Rect(120, 20, 200, 250);
        Mat roi = src.submat(rect);
        ImageUI roiUI = new ImageUI();
        roiUI.imshow("截取头像", roi);
        Imgcodecs.imwrite(rootPath + "lesson10dilirebaHead.png", roi);

        //给ROI进行二次处理
        Mat mixMat = Mat.zeros(roi.size(), roi.type());
        mixMat.setTo(new Scalar(50, 50, 50));
        Core.add(roi, mixMat, roi);
        ImageUI mixRoiUI = new ImageUI();
        mixRoiUI.imshow("对截取后的图像进行加50处理", roi);

        ImageUI srcAfterUI = new ImageUI();
        srcAfterUI.imshow("截取的ROI被处理后会影响原来的图片,即ROI和原图是引用关系", src);

        // 利用上面ROI和原图是引用关系的原理,实现两张图片的融合
        Rect rectToReplace = new Rect(105, 300, 200, 200);
        // 用于替换原图中的rect区域的自己的图像
        Mat imageToCling = Imgcodecs.imread(rootPath + "roi.png");
        // 拷贝到原图中
        imageToCling.copyTo(src.submat(rectToReplace));
        ImageUI mergeUI = new ImageUI();
        mergeUI.imshow("利用上面ROI和原图是引用关系的原理,实现两张图片的融合", src);

        // 截选出一块来用于融合操作
        Mat roiFade = src.submat(rectToReplace);
        // 对读入的小图片做模糊化
        Core.addWeighted(imageToCling,0.5,roiFade,0.5,0,imageToCling);
        // 拷贝到原图中
        imageToCling.copyTo(src.submat(rectToReplace));
        ImageUI fadeUI = new ImageUI();
        fadeUI.imshow("实现两张图片的无缝融合", src);
    }
}
