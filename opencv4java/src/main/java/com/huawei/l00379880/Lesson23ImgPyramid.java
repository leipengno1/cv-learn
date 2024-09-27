package com.huawei.l00379880;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 图像金字塔
 * @author      : 梁山广
 * @date        : 2017/11/6 18:53
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson23ImgPyramid {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "dilireba.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);

        int level = 3;
        List<Mat> resultList = new ArrayList<>();
        // 金字塔的层数，缩小
        Mat temp = src.clone();
        for (int i = 0; i < level; i++) {
            Mat dst = new Mat();
            Imgproc.pyrDown(temp, dst);
            ImageUI uiTemp = new ImageUI();
            uiTemp.imshow("金字塔缩小的图片" + i, dst);
            // 每一个都是基于上一个进行放缩的
            dst.copyTo(temp);
            // 保存每一步缩小之后的结果
            resultList.add(dst);
        }

        //金字塔放大(图像还原)
        for (int i = resultList.size() - 1; i >= 0; i--) {
            Mat expand = new Mat();
            Imgproc.pyrUp(resultList.get(i), expand);
            ImageUI uiExp = new ImageUI();
            uiExp.imshow("图片由最小放大还原后的效果" + i, expand);
        }

        // 拉普拉斯算子(用于图片由小到大的还原)，原图片和Expand的图片相减得到，各个步骤都是这样的,比较像纹理图，哈哈哈
        // 拉普拉斯金字塔+对应层级的模糊图像就会得到原图像
        for (int i = resultList.size() - 1; i >= 0; i--) {
            Mat expand = new Mat();
            Mat lapalian = new Mat();
            if (i - 1 < 0) {
                // 处理金字塔的第一张图片
                Imgproc.pyrUp(resultList.get(i), expand, src.size());
                ImageUI ui1 = new ImageUI();
                // src减去expand得到拉普拉斯算子
                Core.subtract(src, expand, lapalian);
                // 下面数三行是为了显示清楚些才加的，实际并用不到，图像重建的时候千万不要用这三行
                Mat constant = new Mat(lapalian.size(), lapalian.type());
                constant.setTo(new Scalar(127, 127, 127));
                Core.add(constant, lapalian, lapalian);
                ui1.imshow("拉普拉斯金字塔" + i, lapalian);
            } else {
                Imgproc.pyrUp(resultList.get(i), expand, resultList.get(i - 1).size());
                ImageUI ui2 = new ImageUI();
                Core.subtract(resultList.get(i - 1), expand, lapalian);
                // 下面数三行是为了显示清楚些才加的，实际并用不到，图像重建的时候千万不要用这三行
                Mat constant = new Mat(lapalian.size(), lapalian.type());
                constant.setTo(new Scalar(127, 127, 127));
                Core.add(constant, lapalian, lapalian);
                ui2.imshow("拉普拉斯金字塔" + i, lapalian);
            }
        }

    }
}
