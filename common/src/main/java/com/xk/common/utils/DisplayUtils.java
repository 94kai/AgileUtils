package com.xk.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author xuekai1
 * @date 2019-06-21
 */
public class DisplayUtils {
    private static WindowManager windowManager;
    private static DisplayMetrics displayMetrics;
    private static Display display;

    private static DisplayMetrics getDisplayMetrics() {
        if (displayMetrics == null) {
            displayMetrics = new DisplayMetrics();
            windowManager = (WindowManager) AppUtils.getApplication().getSystemService(Context.WINDOW_SERVICE);
            getDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public static Display getDisplay() {
        if (display == null) {
            windowManager = (WindowManager) AppUtils.getApplication().getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }
        return display;
    }

    private static float getDensity() {
        return getDisplayMetrics().density;
    }

    public static int dip2px(float var0) {
        float var1 = getDensity();
        return (int) (var0 * var1 + 0.5F);
    }

    public static int getWidth() {
        return getDisplay().getWidth();
    }

    public static int getHeight() {
        return getDisplay().getHeight();
    }
}