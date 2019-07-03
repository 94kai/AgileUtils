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

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = AppUtils.getApplication().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 存储String类型的值
     *
     * @param key   key值
     * @param value 要存储的String值
     */
    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * 获取String类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }


    /**
     * 存储Int类型的值
     *
     * @param key   key
     * @param value 要存储的Int值
     */
    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }


    /**
     * 获取Int类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }


    /**
     * 存储Boolean类型的值
     *
     * @param key   key
     * @param value 要存储Boolean值
     */
    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * 获取Boolean类型的值
     *
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    //删除 单个 key
    public static void deleShare(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    //删除全部 key
    public static void deleAll() {
        getSharedPreferences().edit().clear().apply();
    }
}
