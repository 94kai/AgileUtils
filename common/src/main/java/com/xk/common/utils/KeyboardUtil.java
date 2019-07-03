package com.xk.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class KeyboardUtil {

    /**
     * 调起软键盘
     *
     * @param view
     */
    public static void showSoftInput(View view) {
        if (view != null) {
            try {
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(view, 0);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 收起软键盘
     *
     * @param view
     */
    public static void hideSoftInput(View view) {
        if (view != null) {
            try {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } catch (Exception e) {
            }
        }
    }
}
