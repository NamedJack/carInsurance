package com.wongxd.carinsurance.permission;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.wongxd.carinsurance.R;

import java.util.List;


/**
 * 用法：
 * <p>
 * <p>
 * 奇淫技巧:
 * <p>
 * checkPermission(CheckPermListener listener, int resString, String... mPerms)
 * <p>
 * - listener：	权限全部通过接口回调，只检测没有后续行为可 null
 * - resString：	权限用途的说明提示（引导用户开启权限）
 * - mPerms：	申请的高危权限组（可同时申请多个）
 * 下面列出简单的使用方法
 * <p>
 * Activity中：
 * <p>
 * 首先需要检测权限的 Activity extends PermissionActivity
 * <p>
 * checkPermission(new CheckPermListener() {
 *
 * @Override public void superPermission() {
 * TODO : 需要权限去完成的功能
 * }
 * },R.string.camera,
 * Manifest.permission.CAMERA,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE);
 * Fragment中：
 * <p>
 * 首先Fragment依存的 Activity extends PermissionActivit
 * <p>
 * ((PermissionActivity)getActivity()).checkPermission(
 * new PermissionActivity.CheckPermListener() {
 * @Override public void superPermission() {
 * TODO : 需要权限去完成的功能
 * }
 * },R.string.camera, Manifest.permission.CAMERA);
 */


/*
 * @创建者     Jrking
 * @创建时间   2016/4/15 16:18
 * @描述	      ${Activity基类 }
 * @更新描述   ${适配6.0权限问题}
 */
public class PermissionActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks {

    protected static final int RC_PERM = 123;

    protected static int reSting = R.string.ask_again;//默认提示语句

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initStatusBar("#303F9F");//透明状态栏
    }

    public void initStatusBar(String strColor) {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor(strColor));
        StatusBarCompat.translucentStatusBar(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 权限回调接口
     */
    private CheckPermListener mListener;

    public interface CheckPermListener {
        //权限通过后的回调方法
        void superPermission();
    }

    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString),
                    RC_PERM, mPerms);
        }
    }

    /**
     *
     * @param listener
     * @param msg 提示信息
     * @param mPerms
     */
    public void checkPermission(CheckPermListener listener, String msg, String... mPerms) {
        mListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, msg,
                    RC_PERM, mPerms);
        }
    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            //设置返回
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //同意了某些权限可能不是全部
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.perm_tip),
                R.string.setting, R.string.cancel, null, perms);
    }


}
