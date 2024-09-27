# [OpenCV+TensorFlow 入门人工智能图像处理](https://www.bilibili.com/video/av51238267)

## 课程用到的库版本

+ Python3.7
+ TensorFlow 1.15.0
+ OpenCV 3.3.1

## 基础代码(Matplotlib+OpenCV进行图像处理和显示)

```python
from matplotlib import pyplot as plt
import cv2

def show(img_path):
    """
    读取彩色图像
    """
    img = cv2.imread(img_path, cv2.IMREAD_COLOR)
    # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB) 
    # 关闭坐标轴显示
    plt.axis("off")
    plt.imshow(img)

def show_img(img):
    """
    显示彩色图像
    """
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB) 
    # 关闭坐标轴显示
    plt.axis("off")
    plt.imshow(img)

def show_gray(img_path):
    """
    读取灰度图像
    """
    img = cv2.imread(img_path, cv2.IMREAD_GRAYSCALE)
    # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB) 
    # 关闭坐标轴显示
    plt.axis("off")
    plt.imshow(img)
```