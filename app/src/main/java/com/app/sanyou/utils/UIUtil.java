package com.app.sanyou.utils;

import android.content.Context;

public class UIUtil {

    public static int dip(Context context, int pixels) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }
}
