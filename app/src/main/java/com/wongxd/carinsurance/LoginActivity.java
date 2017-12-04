package com.wongxd.carinsurance;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.wongxd.carinsurance.aty.MainActivity;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.WeiboDialogUtils;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.NetworkAvailableUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_user;
    private EditText edt_pass;
    private Button btn_login;
    private Context mContext;
    private boolean isNetRequest = false;//是否有网络请求中
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this.getApplicationContext();

        preCheckApk(this);
        initView();

    }

    private void preCheckApk(LoginActivity loginActivity) {

    }

    //初始化view
    private void initView() {
        edt_user = (EditText) findViewById(R.id.edt_user);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_login = (Button) findViewById(R.id.btn_login);

//        edt_user.setText("cs99");
//        edt_pass.setText("1234");

        btn_login.setOnClickListener(v -> {
            String userName = edt_user.getText().toString().trim();
            String password = edt_pass.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                ToastUtil.CustomToast(mContext, R.string.noUserInput);
            } else if (TextUtils.isEmpty(password)) {
                ToastUtil.CustomToast(mContext, R.string.noPasswordInput);
            } else {
                //doLogin

                if (!NetworkAvailableUtils.isNetworkAvailable(mContext)) {
                    ToastUtil.CustomToast(mContext, "网络不可用");
                    return;
                }

                if (isNetRequest) {
                    ToastUtil.CustomToast(mContext, "网络请求中，请稍后");
                    return;
                }
                isNetRequest = true;
                dialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "登录中");

                OkHttpUtils.post().url(UrlConfig.Login_URL)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK")
                        .addParams("loginName", userName)
                        .addParams("loginPassword", password)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                isNetRequest = false;
                                if (dialog != null)
                                    WeiboDialogUtils.closeDialog(dialog);
                                ToastUtil.CustomToast(mContext, "登录失败了呀");

                            }

                            @Override
                            public void onResponse(String response, int id) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.optInt("code");
                                    if (code != 100) {
                                        ToastUtil.CustomToast(mContext, "登录出错\n" + jsonObject.optString("msg"));
                                    } else {
                                        App.IS_NEED_REBOOT = false;
                                        JSONObject data = jsonObject.optJSONObject("data");
                                        App.token = data.optString("token");
                                        ToastUtil.CustomToast(mContext, jsonObject.optString("msg"));

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } finally {

                                    isNetRequest = false;
                                    if (dialog != null)
                                        WeiboDialogUtils.closeDialog(dialog);
                                }
                            }
                        });
            }
        });
    }


}

