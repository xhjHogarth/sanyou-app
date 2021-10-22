#include <jni.h>
#include <string>
#include <vector>

#include "opencv2/opencv.hpp"
#include "android/native_window_jni.h"

using namespace cv;
using namespace std;

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_com_app_sanyou_common_OpencvJni_process_1data(JNIEnv *env, jobject thiz, jbyteArray data_,
                                                   jint w, jint h, jint cameraId) {

    jbyte *data = env->GetByteArrayElements(data_,NULL);

    Mat src(h+h/2, w, CV_8UC1, data);
    cvtColor(src, src, COLOR_YUV2RGBA_NV21);

    if(cameraId == 1){
        //imwrite("/storage/emulated/0/src.jpg",src);
        rotate(src,src,ROTATE_90_COUNTERCLOCKWISE);
        //imwrite("/storage/emulated/0/src2.jpg",src);
        //1-水平翻转 0-垂直翻转
        flip(src, src, 1);
        //imwrite("/storage/emulated/0/src3.jpg",src);
    }else{
        rotate(src, src, ROTATE_90_CLOCKWISE);
    }

    // 灰度化
    Mat gray;
    cvtColor(src, gray, COLOR_BGR2GRAY);
    //imwrite("/storage/emulated/0/src4.jpg",gray);

    //二值化
    Mat shold, blurred;
    threshold(gray, shold, 0, 255, THRESH_OTSU | THRESH_BINARY);

    //形态学操作:闭操作
    Mat close;
    Mat element = getStructuringElement(MORPH_RECT, Size(12, 12));
    morphologyEx(shold, close, MORPH_CLOSE, element);

    //找轮廓
    vector<vector<Point>> contours;
    findContours(close, //输入图像
                 contours, //输出轮廓
                 RETR_EXTERNAL, //外轮廓
                 CHAIN_APPROX_NONE //轮廓上所有像素点
    );
    RotatedRect rotatedRect;
    vector<RotatedRect> vec_sobel_rects;
    // 遍历并判断矩形尺寸
    for (int i = 0; i < contours.size(); i++)
    {
        rotatedRect = minAreaRect(contours[i]);
        rectangle(src, rotatedRect.boundingRect(), Scalar(0, 0, 255));

        //容错率
        float error = 0.75f;
        //理想宽高比
        float aspect = float(12) / float(12);
        //真实宽高比
        float realAspect;
        if(float(rotatedRect.size.height) == NAN || float(rotatedRect.size.height) == 0){
            realAspect = 0;
        }else{
            realAspect = float(rotatedRect.size.width) / float(rotatedRect.size.height);
            if (realAspect < 1) realAspect = (float)rotatedRect.size.height / (float)rotatedRect.size.width;
        }
        //真实面积
        float area = rotatedRect.size.height * rotatedRect.size.width;
        //最小 最大面积 不符合的丢弃
        //给个大概就行 随时调整
        //尽量给大一些没关系， 这还是初步筛选。
        int areaMin = 100 * aspect * 100;
        int areaMax = 1000 * aspect * 1000;

        //比例浮动 error认为也满足
        //最小宽高比
        float aspectMin = aspect - aspect * error;
        //最大宽高比
        float aspectMax = aspect + aspect * error;

        if ((area > areaMin && area < areaMax) && (realAspect > aspectMin && realAspect < aspectMax))
            vec_sobel_rects.push_back(rotatedRect);

    }

    vector<Mat> dst_plates;
    //循环要处理的矩形
    //tortuosity(src, vec_sobel_rects, dst_plates);
    for (RotatedRect roi_rect : vec_sobel_rects) {
        //矩形角度
        float roi_angle = roi_rect.angle;
        float r = (float)roi_rect.size.width / (float)roi_rect.size.height;
        if (r < 1) {
            roi_angle = 90 + roi_angle;
        }

        //矩形大小
        Size roi_rect_size = roi_rect.size;

        //让rect在一个安全的范围(不能超过src)
        Rect2f  safa_rect;

        //safeRect(src, roi_rect, safa_rect);
        //RotatedRect 没有坐标
        //转为正常的带坐标的边框
        Rect2f boudRect = roi_rect.boundingRect2f();

        //左上角 x,y
        float tl_x = boudRect.x > 0 ? boudRect.x : 0;
        float tl_y = boudRect.y > 0 ? boudRect.y : 0;
        //这里是拿 坐标 x，y 从0开始的 所以-1
        //比如宽长度是10，x坐标最大是9， 所以src.clos-1
        //右下角
        float br_x = boudRect.x + boudRect.width < src.cols
                     ? boudRect.x + boudRect.width - 1
                     : src.cols - 1;

        float br_y = boudRect.y + boudRect.height < src.rows
                     ? boudRect.y + boudRect.height - 1
                     : src.rows - 1;

        float  w = br_x - tl_x;
        float h = br_y - tl_y;
        if (w > 0 && h > 0)
            safa_rect = Rect2f(tl_x, tl_y, w, h);

        //抠图  这里不是产生一张新图片 而是在src身上定位到一个Mat 让我们处理
        //数据和src是同一份
        Mat src_rect = src(safa_rect);
        Mat dst = src_rect.clone();

        //调整大小
        Mat plate_mat;
        //高+宽
        plate_mat.create(120, 120, CV_8UC3);
        resize(dst, plate_mat, plate_mat.size());

        dst_plates.push_back(plate_mat);
        dst.release();
    }

    if(dst_plates.size() == 0){
        return NULL;
    }else{
        Mat area = dst_plates[0];
        for(int i=0;i<vec_sobel_rects.size();i++)
        {
            area = shold(vec_sobel_rects[i].boundingRect());
        }

        //图像分割
        int irows = area.rows, icols = area.cols;
        int row = irows / 10, col = icols / 10;
        int delt_y = (irows % 10) / 2, delt_x = (icols % 10) / 2;
        int result[10][10];
        vector<vector<Mat>> array(10,vector<Mat>(10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = j * col + delt_x, y = i * row + delt_y;
                array[i][j] = area(Rect(x, y, row, col));
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Mat pointArea = array[i][j];
                int rowNumber = pointArea.rows;
                int colNumber = pointArea.cols;
                int count = 0;
                int total = rowNumber * colNumber;
                for (int a = 0; a < rowNumber; a++) {
                    for (int b = 0; b < colNumber; b++) {
                        if (pointArea.at<uchar>(a, b) == 255)
                            count++;
                    }
                }
                if (count / (total * 1.0) > 0.11) {
                    result[i][j] = 1;
                }else {
                    result[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = j * col + delt_x, y = i * row + delt_y;
                for (int a = x; a < x + col; a++) {
                    for (int b = y; b < y + row; b++) {
                        if (result[i][j] == 1)
                            area.at<uchar>(b, a) = 0;
                        else
                            area.at<uchar>(b, a) = 255;

                    }
                }
            }
        }

        Mat result2 = area(Rect(delt_x, delt_y, row * 10, col * 10));

        jbyteArray resultData;
        cvtColor(result2, result2, COLOR_RGB2YUV);
        int nFlag = result2.channels() * 8;//一个像素的bits
        int nHeight = result2.rows;
        int nWidth = result2.cols;

        int nBytes = nHeight * nWidth * nFlag / 8;//图像总的字节
        memcpy(resultData, result2.data, nBytes);//转化函数,注意Mat的data成员

        area.release();
        result2.release();

        return resultData;
    }

//    Mat area;
//    for(int i=0;i<vec_sobel_rects.size();i++)
//    {
//        area = shold(vec_sobel_rects[i].boundingRect());
//    }
//
//    //图像分割
//    int irows = area.rows, icols = area.cols;
//    int row = irows / 10, col = icols / 10;
//    int delt_y = (irows % 10) / 2, delt_x = (icols % 10) / 2;
//    int result[10][10];
//    vector<vector<Mat>> array(10,vector<Mat>(10));
//    for (int i = 0; i < 10; i++) {
//        for (int j = 0; j < 10; j++) {
//            int x = j * col + delt_x, y = i * row + delt_y;
//            array[i][j] = area(Rect(x, y, row, col));
//        }
//    }
//
//    for (int i = 0; i < 10; i++) {
//        for (int j = 0; j < 10; j++) {
//            Mat pointArea = array[i][j];
//            int rowNumber = pointArea.rows;
//            int colNumber = pointArea.cols;
//            int count = 0;
//            int total = rowNumber * colNumber;
//            for (int a = 0; a < rowNumber; a++) {
//                for (int b = 0; b < colNumber; b++) {
//                    if (pointArea.at<uchar>(a, b) == 255)
//                        count++;
//                }
//            }
//            if (count / (total * 1.0) > 0.11) {
//                result[i][j] = 1;
//            }else {
//                result[i][j] = 0;
//            }
//        }
//    }
//
//    for (int i = 0; i < 10; i++) {
//        for (int j = 0; j < 10; j++) {
//            int x = j * col + delt_x, y = i * row + delt_y;
//            for (int a = x; a < x + col; a++) {
//                for (int b = y; b < y + row; b++) {
//                    if (result[i][j] == 1)
//                        area.at<uchar>(b, a) = 0;
//                    else
//                        area.at<uchar>(b, a) = 255;
//
//                }
//            }
//        }
//    }
//
//    Mat result2 = area(Rect(delt_x, delt_y, row * 10, col * 10));
//
//    jbyteArray resultData;
//    cvtColor(result2, result2, COLOR_RGB2YUV);
//    int nFlag = result2.channels() * 8;//一个像素的bits
//    int nHeight = result2.rows;
//    int nWidth = result2.cols;
//
//    int nBytes = nHeight * nWidth * nFlag / 8;//图像总的字节
//    memcpy(resultData, result2.data, nBytes);//转化函数,注意Mat的data成员

    src.release();
    gray.release();
    shold.release();
    blurred.release();
    close.release();
//    area.release();
//    result2.release();
    env->ReleaseByteArrayElements(data_,data,0);

    //return resultData;
}