# OpenCV Python零基础入门

> 课程基于jupyter lab完成，启动Anaconda Navigator后，里面有Jupyter选项，记得修改下默认启动目录

## 参考博客

+ [使用python开启你的opencv之旅---图像的读入,存储](https://www.cnblogs.com/lynsyklate/p/7720045.html)

## OpenCV和Matplotlib实现图像处理结果展示

```python
import cv2 as cv
from matplotlib import pyplot as plt


class CvUtils:

    @staticmethod
    def show(img_path):
        """
        读取彩色图像
        :param img_path: 图片路径
        """
        img = cv.imread(img_path, cv.IMREAD_COLOR)
        # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
        img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
        # 关闭坐标轴显示
        plt.axis("off")
        plt.imshow(img)

    @staticmethod
    def show_img(img):
        """
        根据opencv的图片对象显示图片，灰度还是彩色才外面就定下了
        :param img: opencv读取到的图片对象
        """
        img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
        # 关闭坐标轴显示
        plt.axis("off")
        plt.imshow(img)

    @staticmethod
    def show_gray(img_path):
        """
        灰度方式读取图像
        :param img_path: 图片路径
        """
        img = cv.imread(img_path, cv.IMREAD_GRAYSCALE)
        # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
        img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
        # 关闭坐标轴显示
        plt.axis("off")
        plt.imshow(img)
```

## 1.课程概述

### 1.1 OpenCV的主要模块

> ![OpenCV的主要模块](https://i.loli.net/2019/11/18/zb2Gd3uSQw1qUk8.jpg)

### 1.2.安装opencv

+ 安装基础库：`pip install opencv-python`
+ 安装扩展库：`pip install opencv-contrib-python`

## 2.第一个opencv的程序
> 测试图片来源：https://raw.githubusercontent.com/opencv/opencv/master/samples/data/lena.jpg

把上面的CvUtils.py放到utils包中，在utils包同级目录放入lena.jpg,然后新建notebook(xxx.ipynb),在notebook中添加代码如下即可测试：
```python
from utils.CvUtils import CvUtils

# 彩色读取
CvUtils.show('lena.jpg')

# 灰度读取
CvUtils.show_gray('lena.jpg')

# 读取为彩色图
img = cv.imread('lena.jpg', cv.IMREAD_COLOR)
CvUtils.show_img(img)

# 读取为灰度图
img = cv.imread('lena.jpg', cv.IMREAD_GRAYSCALE)
CvUtils.show_img(img)
```

