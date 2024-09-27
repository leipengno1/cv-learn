package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

/**
 * 通道分离与合并
 *
 * @author l0037980
 * @datte 2017-11-01 19:09
 */
public class Lesson07SplitMix {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 默认是彩色读取
        Mat src = Imgcodecs.imread(rootPath + "lena.png");
        ImageUI ui = new ImageUI();
        ui.imshow("lena显示", src);

        // 拆分通道显示
        List<Mat> matList = new ArrayList<>();
        Core.split(src, matList);
        // 遍历list是引用传值
        for (int i = 0; i < matList.size(); i++) {
            ImageUI singleChannelUI = new ImageUI();
            singleChannelUI.imshow("channel--" + i, matList.get(i));
        }

        // 混合通道
        List<Mat> matListOther = new ArrayList<>();
        // 读一个灰度图
        matListOther.add(new Mat(src.size(), CvType.CV_8UC1));
        // 把src的第二个通道即r通道放过去
        Core.mixChannels(matList, matListOther, new MatOfInt(2, 0));
        ImageUI mixUI = new ImageUI();
        mixUI.imshow("混合后的图,红色混进去的", matListOther.get(0));
    }
}
