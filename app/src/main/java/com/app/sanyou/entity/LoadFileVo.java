package com.app.sanyou.entity;

import android.graphics.Bitmap;

import java.io.File;

public class LoadFileVo {
    private File file;
    private boolean isUpload;
    private Bitmap bitmap;

    public LoadFileVo() {
    }

    public LoadFileVo(File file, boolean isUpload, Bitmap bitmap) {
        this.file = file;
        this.isUpload = isUpload;
        this.bitmap = bitmap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
