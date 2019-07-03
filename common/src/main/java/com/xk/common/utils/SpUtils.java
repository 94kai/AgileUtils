package com.xk.common.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author xuekai1
 * @date 2019-07-03
 */
public class SpUtils {

    private static SharedPreferences sharedPreferences;


    public static void init(Application application) {
        sharedPreferences = application.getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    /**
     * 存储String类型的值
     *
     * @param key      key值
     * @param value    要存储的String值
     */
    public static void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    /**
     * 获取String类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }


    /**
     * 存储Int类型的值
     *
     * @param key      key
     * @param value    要存储的Int值
     */
    public static void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }


    /**
     * 获取Int类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }


    /**
     * 存储Boolean类型的值
     *
     * @param key      key
     * @param value    要存储Boolean值
     */
    public static void putBoolean(String key, boolean value) {

        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取Boolean类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    //删除 单个 key
    public static void deleShare(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    //删除全部 key
    public static void deleAll() {
        sharedPreferences.edit().clear().apply();
    }
}
