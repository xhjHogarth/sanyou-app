package com.app.sanyou.common;

public class OpencvJni {

    static {
        System.loadLibrary("sanyou");
    }

    public native byte[] process_data(byte[] data,int width, int height,int cameraId);
}
