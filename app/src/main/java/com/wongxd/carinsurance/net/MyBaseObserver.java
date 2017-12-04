package com.wongxd.carinsurance.net;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by json on 2017/11/2.
 */

public abstract class MyBaseObserver<T> implements Observer<T> {
    private Context context;
    private boolean isShowDialog;
    private String errorCode = "";
    private String errorMsg = "";
    private static final String RESPONSE_CODE_FAILED = -1 + "";
    private Dialog dialog;

    public MyBaseObserver(Context context, boolean isShowDialog, String tips) {
        this.context = context;
        if (isShowDialog) {
            if (tips.equals("") || tips == null) {
                tips = "数据请求中,请稍后...";
            }
            dialog = NetDialog.createDialog(context, tips);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
//        if(!NetUtils.isNetworkConnected(context)){
//            ToastUtils.cT("当前网络不可用,请检查网络设置");
//            return;
//        }
    }

    @Override
    public void onNext(T t) {
        NetDialog.closeDialog(dialog);
        _doNext(t);
    }

    @Override
    public void onError(Throwable e) {
        NetDialog.closeDialog(dialog);
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code() + "";
            errorMsg = httpException.getMessage();
            if (errorCode.equals("500")) {
                Toast.makeText(context, "服务器内部错误", Toast.LENGTH_SHORT).show();
//                ToastUtils.cT("服务器内部错误");
            } else if (errorCode.equals("404")) {
                Toast.makeText(context, "服务器资源不存在,请稍后再试~~", Toast.LENGTH_SHORT).show();
//                ToastUtils.cT("服务器资源不存在,请稍后再试~~");
            }
            _doError(errorCode, errorMsg);

//            getErrorMsg(httpException);
        } else if (e instanceof SocketTimeoutException
                || e instanceof ConnectException
                || e instanceof Resources.NotFoundException
                || e instanceof InternalError) { //VPN open
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "服务器响应超时";
            _doError(errorCode, errorMsg);
        } else {

            _doError(errorCode, e.toString());
        }

    }

    private void _doError(String errorCode, String errorMsg) {
        if (errorCode.equals("-1")) {
            Toast.makeText(context, errorMsg + "", Toast.LENGTH_SHORT).show();
//            ToastUtils.cT(errorMsg + "");
        } else {
            Log.e("netError", errorCode + " " + errorMsg);
        }
    }

    private void getErrorMsg(HttpException httpException) {

    }

    @Override
    public void onComplete() {
        NetDialog.closeDialog(dialog);
    }


    public abstract void _doNext(T t);
//    public abstract void _doError(String code, String msg);


}
