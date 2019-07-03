package com.xk.limitapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.xk.common.utils.SpUtils;
import com.xk.netutils.ConvertCallback;
import com.xk.netutils.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

/**
 * 1.0.0-SNAPSHOT
 *
 * @author xuekai1
 * @date 2019-05-28
 */
public class LimitUtils {

    private static String host = "https://qim10ic7.api.lncld.net/1.1/classes/";
    private static String id = "Qim10Ic7wlTmTeM5Y26bsau3-gzGzoHsz";
    private static String key = "QUGwR69MfPTa82hHEkM1984I";

    public static void limit(final String packageName, final Activity context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isLimit;
                if (!SpUtils.getBoolean("isLimit", true)) {
                    //sp中返回了不限制，可以直接return了。
                    return;
                }
                getStateFromNet(packageName, context);
            }
        }).start();
    }

    private static void showWaring(final Activity context) {
        AlertDialog show = new AlertDialog
                .Builder(context)
                .setTitle("提示").
                        setMessage("试用版暂不可使用，请使用正版!")
                .show();
        show.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                context.finish();
            }
        });
        show.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                context.finish();
            }
        });
    }

    /**
     * 0.禁用
     * 1.可用
     * 2.给sp中保存可用标记
     *
     * @return isLimit
     */
    private static void getStateFromNet(String packageName, final Activity context) {

        Headers headers = new Headers.Builder()
                .add("X-LC-Id", id)
                .add("X-LC-Key", key)
                .build();
        NetUtil.getInstance().getSync(new ConvertCallback.Callback<String>() {
                                      @Override
                                      public void onSuccess(String response) {
                                          int state = 0;
                                          JSONObject jsonObject;
                                          try {
                                              jsonObject = new JSONObject(response);
                                              JSONArray results = jsonObject.getJSONArray("results");
                                              if (results.length() > 0) {
                                                  JSONObject o = (JSONObject) results.get(0);
                                                  Object limiteType = o.get("limitType");
                                                  state = (int) limiteType;
                                              }
                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                              showWaring(context);
                                              return;
                                          }
                                          boolean isLimit;
                                          if (state == 1) {
                                              isLimit = false;
                                          } else if (state == 2) {
                                              SpUtils.putBoolean("isLimit", false);
                                              isLimit = false;
                                          } else {
                                              isLimit = true;
                                          }
                                          if (!isLimit) {
                                              //通过网络返回了不限制，可以直接return了。
                                              return;
                                          }
                                          showWaring(context);
                                      }

                                      @Override
                                      public void onError(Exception e) {
                                          showWaring(context);
                                      }
                                  }, String.class,
                host + "LimitingApp?where={\"PackageName\":\"" + packageName + "\"}", headers);
    }
}
