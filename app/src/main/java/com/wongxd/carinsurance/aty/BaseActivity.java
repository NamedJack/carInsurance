package com.wongxd.carinsurance.aty;

import android.content.Intent;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.LoginActivity;
import com.wongxd.carinsurance.permission.PermissionActivity;
import com.wongxd.carinsurance.utils.ToastUtil;

/**
 * Created by wxd1 on 2017/3/14.
 */

public class BaseActivity extends PermissionActivity {


    @Override
    protected void onResume() {
        super.onResume();
        if (App.IS_NEED_REBOOT) {
            ToastUtil.CustomToast(getApplicationContext(), "TOKEN 失效，请登录！");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }


//    @Override
//    public void onBackPressed() {
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//        super.onBackPressed();
//    }
//
//    @Override
//    public void finish() {
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//        super.finish();
//    }
}
