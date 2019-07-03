package com.xk.common.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author xuekai1
 * @date 2019-06-21
 */
public class ToastUtil {
    private static Toast toast;

    private static Handler mainHandler;

    public static void init(Context context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        Looper mainLooper = Looper.getMainLooper();
        mainHandler = new Handler(mainLooper);
    }

    public static void show(String msg) {
        Looper mainLooper = Looper.getMainLooper();
        if (Thread.currentThread() == mainLooper.getThread()) {
            toast.setText(msg);
            toast.show();
        } else {
            mainHandler.post(() -> {
                toast.setText(msg);
                toast.show();
            });
        }
    }
}
