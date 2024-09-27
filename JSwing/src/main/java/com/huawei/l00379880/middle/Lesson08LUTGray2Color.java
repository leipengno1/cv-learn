package com.huawei.l00379880.middle;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/***********************************************************
 * @Description : 利用查找表将灰度图像转换为彩色图像
 * @author      : 梁山广
 * @date        : 2017/11/21 20:55
 * @email       : liangshanguang2@gmail.com
 ***********************************************************/
public class Lesson08LUTGray2Color {
    @Getter
    @Setter
    BufferedImage colorCard;

    public BufferedImage process(BufferedImage image) {
        setupLookupTable();
        return null;
    }

    public void setupLookupTable() {

    }
}
