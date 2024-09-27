import cv2 as cv
from matplotlib import pyplot as plt


class CvUtils:

#     @staticmethod
#     def show(img_path):
#         """
#         读取彩色图像
#         :param img_path: 图片路径
#         """
#         img = cv.imread(img_path, cv.IMREAD_COLOR)
#         # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
#         img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
#         # 关闭坐标轴显示
#         plt.axis("off")
#         plt.imshow(img)

    @staticmethod
    def show_img(img):
        """
        根据opencv的图片对象显示图片，灰度还是彩色才外面就定下了
        :param img: opencv读取到的图片对象
        """
        img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
        # 关闭坐标轴显示
#         plt.axis("off")
        plt.imshow(img)

#     @staticmethod
#     def show_gray(img_path):
#         """
#         灰度方式读取图像
#         :param img_path: 图片路径
#         """
#         img = cv.imread(img_path, cv.IMREAD_GRAYSCALE)
#         # opencv默认是BGR存储图像,正常是RGB，所以需要进行转换
#         img = cv.cvtColor(img, cv.COLOR_BGR2RGB)
#         # 关闭坐标轴显示
#         plt.axis("off")
#         plt.imshow(img)