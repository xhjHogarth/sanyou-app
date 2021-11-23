package com.app.sanyou.utils;

public class MediaTypeUtil {
    public static String getMimeType(String fileName){
        String mediaType = "image/jpg";

        if(fileName.contains(".png") || fileName.contains(".PNG"))
            mediaType = "image/png";
        if(fileName.contains(".gif") || fileName.contains(".GIF"))
            mediaType = "image/gif";

        return mediaType;
    }
}
