package com.huawei.l00379880;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/***********************************************************
 * @Description : 多尺度模板匹配,当在原图上匹配不到时,很可能时因为
 *                图片大小和模板不匹配,通过高斯金字塔进行放缩,很可
 *                能在原图缩小一定比例的时候就能匹配上.然后再通过
 *                反向推导就可以得到在原图上找到对应比例的位置
 * @author      : 梁山广
 * @date        : 2017/11/7 10:42
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson25MScaleTemplateMatch {
    public static void main(String[] args) {
        String rootPath = "D:\\l00379880\\GithubProjects\\images\\";
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src = Imgcodecs.imread(rootPath + "traffic.jpg");
        Mat template = Imgcodecs.imread(rootPath + "flag.png");
        ImageUI ui = new ImageUI();
        ui.imshow("原始图片", src);
        // 三层高斯,获取各个层次的图
        List<Mat> scaleList = buildGaussianPyramid(src, 3);
        List<Mat> templateList = buildGaussianPyramid(template, 3);
        // TM_XXX下面有5中选择,但是TM_CCOEFF_NORMED的效果最好
        int method = Imgproc.TM_CCOEFF_NORMED;
        boolean found = false;
        for (int i = 0; i < scaleList.size(); i++) {
            for (int j = 0; j < templateList.size(); j++) {
                // 匹配到就退出
                if (found) {
                    break;
                }
                Core.MinMaxLocResult minMaxLocResult = matchImage(scaleList.get(i), templateList.get(j), method);

                Point maxLoc = minMaxLocResult.maxLoc;
                Point minLoc = minMaxLocResult.minLoc;

                Point matchLoc = null;
                double matchValue = 0.0;
                // Imgproc.TM_XX下五中方法的比较
                if (method == Imgproc.TM_SQDIFF || method == Imgproc.TM_SQDIFF_NORMED) {
                    matchLoc = minLoc;
                    matchValue = minMaxLocResult.minVal;
                } else {
                    matchLoc = maxLoc;
                    matchValue = minMaxLocResult.maxVal;
                }
                // 匹配度大于0.75才显示出来
                if (matchValue > 0.75) {
                    // 为了不影响原图,所以先clone一份给自己
                    Mat copy = scaleList.get(i).clone();
                    Imgproc.rectangle(copy, matchLoc, new Point(matchLoc.x + templateList.get(j).cols(), matchLoc.y + templateList.get(j).rows()), new Scalar(0, 0, 255), 2, 8, 0);
                    ImageUI matchUI = new ImageUI();
                    matchUI.imshow("把匹配的结果在原图上显示出来", copy);
                    found = true;
                    break;
                }
            }
        }

    }

    /**
     * 返回图像的匹配点
     *
     * @param src      原始图片
     * @param template 模板图片
     * @return 返回匹配位置的最大和最小点的封装数据类型MinMaxLocResult
     */
    public static Core.MinMaxLocResult matchImage(Mat src, Mat template, int method) {
        //下面注意:模板匹配的结果result的大大小必须是(原图片-模板大小+1),长款都必须是这个原则,否则无法运行
        int height = src.rows() - template.rows() + 1;
        int width = src.cols() - template.cols() + 1;
        Mat result = new Mat(height, width, CvType.CV_32FC1);
        // 开始匹配,并制定匹配方法
        Imgproc.matchTemplate(src, template, result, method);
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(result);
        return minMaxLocResult;
    }

    /**
     * 获取高斯算子,图片越来越小
     *
     * @param image
     * @return
     */
    public static List<Mat> buildGaussianPyramid(Mat image, int level) {
        List<Mat> pyramid = new ArrayList<>();
        Mat copy = image.clone();
        pyramid.add(image.clone());
        Mat dst = new Mat();
        for (int i = 0; i < level; i++) {
            Imgproc.pyrDown(copy, dst, new Size(copy.cols() / 2, copy.rows() / 2));
            dst.copyTo(copy);
            pyramid.add(dst.clone());
        }
        return pyramid;
    }

}
