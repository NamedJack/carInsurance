package com.wongxd.carinsurance;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.NormalDialog;
import com.wongxd.carinsurance.utils.FileUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    int flag = -1; //0取消  1删除   2 安装不成功且返回

    File dirs = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wongxd");
    File file = new File(dirs, "update.apk");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.MainActivityTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Glide.with(this.getApplicationContext()).load(R.drawable.icon)
                .centerCrop()
                .into(iv);
        AlphaAnimation anim = new AlphaAnimation(0.7f, 1f);
        anim.setDuration(500);
        anim.start();
        iv.setAnimation(anim);


        //检查有无版本下载而未安装
        if (checkIsApkDownloaded()) {
            // 创建一个开启安装App界面的意图
//            PackageManager pm = getPackageManager();
//            PackageInfo info = pm.getPackageArchiveInfo(file.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
//            if (info != null) {
//                ApplicationInfo appInfo = info.applicationInfo;
//                String appName = pm.getApplicationLabel(appInfo).toString();
//                String packageName = appInfo.packageName;  //得到安装包名称
//                String version = info.versionName;       //得到版本信息
//                Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息
//                int versionCode = info.versionCode;
//
//
//                if (versionCode > getVersionCode()) {

            if (getVersionCode() >= getVersionCodeFromApk(this, file.getAbsolutePath())) {
                FileUtil.delete(file);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();
            }

            final NormalDialog dialog = new NormalDialog(this);
            dialog.style(NormalDialog.STYLE_TWO)//
                    .btnNum(2)
                    .btnText("确定", "删除安装包")//
                    .isTitleShow(false)//
                    .bgColor(Color.parseColor("#383838"))//
                    .cornerRadius(5)//
                    .content("有新版本安装包存在\n是否安装?")//
                    .contentGravity(Gravity.CENTER)//
                    .contentTextColor(Color.parseColor("#ffffff"))//
                    .dividerColor(Color.parseColor("#222222"))//
                    .btnTextSize(15.5f, 15.5f)//
                    .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                    .btnPressColor(Color.parseColor("#2B2B2B"))//
                    .widthScale(0.85f)//
                    .showAnim(new BounceTopEnter())//
                    .setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            dialog.setOnBtnClickL(
                    () -> {
                        Intent installIntent = new Intent();
                        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        installIntent.setAction(Intent.ACTION_VIEW);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //判断版本是否在7.0以上
                            Uri apkUri =
                                    FileProvider.getUriForFile(getApplicationContext(),
                                            "com.wongxd.carinsurance" + ".fileprovider",
                                            file);
                            //添加这一句表示对目标应用临时授权该Uri所代表的文件
                            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");

                        } else {
                            installIntent.setDataAndType(Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                        }

                        startActivity(installIntent);
                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                        flag = 2;
                        dialog.dismiss();
                    },

                    () -> {
                        FileUtil.delete(file);
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                        dialog.dismiss();
                        finish();
                    });


//                }
//            }
        } else {

            iv.postDelayed(() -> {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                        finish();
                    }

                    , 800);
        }

    }

    /**
     * 检查是否有下载的更新文件
     *
     * @return
     */
    private boolean checkIsApkDownloaded() {
        File dirs = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wongxd");
        if (!dirs.exists()) {
            return false;
        }
        File file = new File(dirs, "update.apk");
        return file.exists();
    }


    /**
     * 从一个apk文件去获取该文件的版本信息
     *
     * @param context         本应用程序上下文
     * @param archiveFilePath APK文件的路径。如：/sdcard/download/XX.apk
     * @return
     */
    public static int getVersionCodeFromApk(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo packInfo = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);
        String version = packInfo.versionName;
        int code = packInfo.versionCode;
        return code;
    }

    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int packageCode = packageInfo.versionCode;
            return packageCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (flag == 2) {
            flag = -1;
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
