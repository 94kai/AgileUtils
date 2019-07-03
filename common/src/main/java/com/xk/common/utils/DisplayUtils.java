package com.xk.common.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author xuekai1
 * @date 2019-06-21
 */
public class DisplayUtils {
    private static Application app;
    private static WindowManager windowManager;
    private static DisplayMetrics displayMetrics;

    public DisplayUtils() {
    }

    public static void init(Application application) {
        app = application;
        getDisplayMetrics();
    }

    private static void getDisplayMetrics() {
        displayMetrics = new DisplayMetrics();
        windowManager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    }

    public static float getDensity() {
        return displayMetrics.density;
    }

    public static int dip2px(float var0) {
        float var1 = getDensity();
        return (int) (var0 * var1 + 0.5F);
    }

    public static int getWidth() {
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int getHeight() {
        return windowManager.getDefaultDisplay().getHeight();
    }
}