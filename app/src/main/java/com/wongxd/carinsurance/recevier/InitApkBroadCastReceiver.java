package com.wongxd.carinsurance.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

public class InitApkBroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


//        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//            Toast.makeText(context, "有应用被添加", Toast.LENGTH_LONG).show();
//        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//            Toast.makeText(context, "有应用被删除", Toast.LENGTH_LONG).show();
//        } else if (Intent.ACTION_PACKAGE_CHANGED.equals(intent.getAction())) {
//            Toast.makeText(context, "有应用被改变", Toast.LENGTH_LONG).show();
//            deleteFile(intent);
//        } else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
//            Toast.makeText(context, "有应用被替换", Toast.LENGTH_LONG).show();
//            deleteFile(intent);
//        }
               /* else  if(Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())){
                    CustomToast.makeText(context, "有应用被重启", CustomToast.LENGTH_LONG).show();
            }*/
              /*  else  if(Intent.ACTION_PACKAGE_INSTALL.equals(intent.getAction())){
                    CustomToast.makeText(context, "有应用被安装", CustomToast.LENGTH_LONG).show();
            }*/
    }

    private void deleteFile(Intent intent) {
        String packageName = intent.getDataString();
        System.out.println(packageName);
        if (packageName.equals("com.wongxd.carinsurance")) {
            try {
                File dirs = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Wongxd");
                if (!dirs.exists()) return;
                File file = new File(dirs, "update.apk");
                if (file.exists()) file.delete();
            } catch (Exception e) {

            }

        }
    }


}