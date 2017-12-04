package com.wongxd.carinsurance.aty.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class UpdatePasswordActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.oldpass)
    TextView oldpass;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.newpass)
    TextView newpass;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.newpass2)
    TextView newpass2;
    @BindView(R.id.et_check_new_pwd)
    EditText etCheckNewPwd;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private Context mContext;
    private AppCompatActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = getApplicationContext();
    }

    @OnClick({R.id.iv_return, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                UpdatePasswordActivity.this.finish();
                break;
            case R.id.btn_submit:
                String pwd = etPwd.getText().toString().trim();
                String newPwd = etNewPwd.getText().toString().trim();
                String checkNewPwd = etCheckNewPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.CustomToast(mContext, "请输入原始密码");
                } else if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(checkNewPwd)) {
                    ToastUtil.CustomToast(mContext, "新密码必须输入两次");
                } else if (!newPwd.equals(checkNewPwd)) {
                    ToastUtil.CustomToast(mContext, "两次新密码输入不一致");
                } else {
                    WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.UpdateUserPassword_URL)
                                    .addParams("token", App.token)
                                    .addParams("lastPassword", pwd)
                                    .addParams("newPassword", newPwd)
                                    .addParams("confirmPassword", checkNewPwd)
                            , UrlConfig.UpdateUserPassword_URL
                            , thisActivity, "修改密码中", new WNetUtil.WNetStringCallback() {
                                @Override
                                public void success(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optInt("code") == 100) {
                                            ToastUtil.CustomToast(mContext, "修改成功,请重新登录");
                                            App.IS_NEED_REBOOT=true;
                                            thisActivity.finish();
                                        } else ToastUtil.CustomToast(mContext, "修改失败");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void error(Call call, Exception e, int id) {
                                    ToastUtil.CustomToast(mContext, "修改失败");
                                }
                            }

                    );
                }
                break;
        }
    }
}
