#文件使用说明

### *安装*
1.区opencv官网下载opencv的安装包,我的是windows下的,opencv-3.3.0-vc14.exe,运行解压<hr>
2.找到Java编译过的文件夹,我的是<br>"D:\l00379880\OpenCV\opencv\build\java"<br>把x64或x86下的dll文件拷贝到工程使用的JDK版本中的jre中bin文件夹里
,比如我的是<br> *D:\Program Files\Java\jdk1.8.0_144\jre\bin* <br><hr>
3.把opencv-330.jar引入项目,建议用Maven安装在本地后再使用,安装命令为:<br>
*mvn install:install-file -Dfile=opencv-330.jar -DgroupId=com.huawei.l00379880 -DartifactId=opencv -Dversion=3.3.0 -Dpackaging=jar*
