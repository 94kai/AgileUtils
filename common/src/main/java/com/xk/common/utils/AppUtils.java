package com.xk.common.utils;

import android.app.Application;

/**
 * @author xuekai1
 * @date 2019-07-03
 */
public class AppUtils {
    private static Application application;

    public static void init(Application _application) {
        application = _application;
    }

    public static Application getApplication() {
        return application;
    }
}
