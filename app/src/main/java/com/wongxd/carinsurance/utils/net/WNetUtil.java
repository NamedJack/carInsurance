package com.wongxd.carinsurance.utils.net;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.WeiboDialogUtils;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by wxd1 on 2017/3/15.
 */

//网络访问Utils
public class WNetUtil {


    //回调
    public interface WNetStringCallback {

        public void success(String response, int id);

        public void error(Call call, Exception e, int id);
    }

    //回调
    public interface WNetFileCallback {
        /**
         * 进度，仅 FileCallback 才有
         *
         * @param progress 进度 0-100
         */
        public void inProgress(int progress);

        public void FileSuccess(File file, int id);


        public void error(Call call, Exception e, int id);
    }

    //网络请求准备回调
    public interface WNetCanGoCallback {
        void canGo();
    }

    private static HashSet<String> urls = new HashSet<>();//所有请求中的路径

    private static Map<String, Dialog> dialogMap = new HashMap<>();//包含所有提示框的map


    /***
     * StringCallBack 的访问
     *
     * @param builder        OkHttpRequestBuilder
     * @param url            url
     * @param c              activity
     * @param msg            dialog提示信息
     * @param isShowDialog   是否显示dialog
     * @param stringCallback 回调
     */
    public static void StringCallBack(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, String msg, boolean isShowDialog, final WNetStringCallback stringCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            ToastUtil.CustomToast(c, "网络不可用");
            return;
        }

        if (urls.contains(url)) {
            ToastUtil.CustomToast(c, "请求进行中，请稍后");
            return;
        }
        urls.add(url);
        Dialog dialog = null;
        if (isShowDialog) {
            dialog = WeiboDialogUtils.createLoadingDialog(c, msg);
        } else dialog = new Dialog(c);
        dialogMap.put(url, dialog);
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != dialogMap.get(url)) {
                            WeiboDialogUtils.closeDialog(dialogMap.get(url));
                            dialogMap.remove(url);
                            urls.remove(url);
                        }
                        try {
                            stringCallback.error(call, e, id);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 400) {
                                App.IS_NEED_REBOOT = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                try {
//                    response = new String(response.getBytes("GBK"),"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                        try {
                            stringCallback.success(response, id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != dialogMap.get(url)) {
                                WeiboDialogUtils.closeDialog(dialogMap.get(url));
                                dialogMap.remove(url);
                                urls.remove(url);
                            }
                        }
                    }
                });
    }


    /**
     * @param builder           OkHttpRequestBuilder
     * @param url               url
     * @param c                 activity
     * @param msg               dialog提示信息
     * @param isShowDialog      是否显示dialog
     * @param stringCallback    回调
     * @param wNetCanGoCallback 网络请求可以进行
     */
    public static void StringCallBack(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, String msg
            , boolean isShowDialog, WNetCanGoCallback wNetCanGoCallback, final WNetStringCallback stringCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            ToastUtil.CustomToast(c, "网络不可用");
            return;
        }

        if (urls.contains(url)) {
            ToastUtil.CustomToast(c, "请求进行中，请稍后");
            return;
        }


        wNetCanGoCallback.canGo();

        urls.add(url);
        Dialog dialog = null;
        if (isShowDialog) {
            dialog = WeiboDialogUtils.createLoadingDialog(c, msg);
        } else dialog = new Dialog(c);
        dialogMap.put(url, dialog);
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != dialogMap.get(url)) {
                            WeiboDialogUtils.closeDialog(dialogMap.get(url));
                            dialogMap.remove(url);
                            urls.remove(url);
                        }
                        try {
                            stringCallback.error(call, e, id);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 400) {
                                App.IS_NEED_REBOOT = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                try {
//                    response = new String(response.getBytes("GBK"),"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                        try {
                            stringCallback.success(response, id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != dialogMap.get(url)) {
                                WeiboDialogUtils.closeDialog(dialogMap.get(url));
                                dialogMap.remove(url);
                                urls.remove(url);
                            }
                        }
                    }
                });
    }


    /***
     * StringCallBack 的访问
     *
     * @param builder        OkHttpRequestBuilder
     * @param url            url
     * @param c              activity
     * @param msg            dialog提示信息
     * @param stringCallback 回调
     */
    public static void StringCallBack(OkHttpRequestBuilder builder, final String url, AppCompatActivity c, String msg, final WNetStringCallback stringCallback) {

//        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
//            ToastUtil.CustomToast(c, "网络不可用");
//            return;
//        }

        if (urls.contains(url)) {
            ToastUtil.CustomToast(c, "请求进行中，请稍后");
            return;
        }
        urls.add(url);

        Dialog dialog = WeiboDialogUtils.createLoadingDialog(c, msg);
        dialogMap.put(url, dialog);
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != dialogMap.get(url)) {
                            WeiboDialogUtils.closeDialog(dialogMap.get(url));
                            dialogMap.remove(url);
                            urls.remove(url);
                        }
                        try {
                            stringCallback.error(call, e, id);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 400) {
                                App.IS_NEED_REBOOT = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                try {
//                    response = new String(response.getBytes("GBK"),"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                        try {
                            stringCallback.success(response, id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != dialogMap.get(url)) {
                                WeiboDialogUtils.closeDialog(dialogMap.get(url));
                                dialogMap.remove(url);
                                urls.remove(url);
                            }
                        }
                    }
                });
    }


    /***
     * FileCallBack 的访问
     *
     * @param builder          OkHttpRequestBuilder
     * @param url              url
     * @param c                context
     * @param fileStorePath    file存储路径
     * @param fileStoreName    file存储名称
     * @param msg              dialog提示信息
     * @param wNetFileCallback 回调
     */
    public static void FileCallBack(PostFormBuilder builder, final String url, String fileStorePath, String fileStoreName,
                                    AppCompatActivity c, String msg, final WNetFileCallback wNetFileCallback) {

        if (!NetworkAvailableUtils.isNetworkAvailable(c)) {
            ToastUtil.CustomToast(c, "网络不可用");
            return;
        }

        if (urls.contains(url)) {
            ToastUtil.CustomToast(c, "请求进行中，请稍后");
            return;
        }
        urls.add(url);

        Dialog dialog = WeiboDialogUtils.createLoadingDialog(c, msg);
        dialogMap.put(url, dialog);


        builder.addHeader("Content-Type", "multipart/form-data")
                .build().execute(new FileCallBack(fileStorePath, fileStoreName) {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (null != dialogMap.get(url)) {
                    WeiboDialogUtils.closeDialog(dialogMap.get(url));
                    dialogMap.remove(url);
                    urls.remove(url);
                }
                try {
                    wNetFileCallback.error(call, e, id);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

            @Override
            public void onResponse(File response, int id) {

                try {
                    wNetFileCallback.FileSuccess(response, id);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != dialogMap.get(url)) {
                        WeiboDialogUtils.closeDialog(dialogMap.get(url));
                        dialogMap.remove(url);
                        urls.remove(url);
                    }
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                int p = (int) (progress * 100);
                wNetFileCallback.inProgress(p);

            }
        });


    }


}
