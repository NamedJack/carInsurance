package com.wongxd.carinsurance.aty.personal;

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

public class UpdateNameActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private AppCompatActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);
        ButterKnife.bind(this);
        thisActivity = this;
    }

    @OnClick({R.id.iv_return, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.btn_submit:
                final String name = etUsername.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.CustomToast(thisActivity.getApplicationContext(), "请输入新的名字");
                } else {
                    WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.UpdateUserName_URL)
                                    .addParams("token", App.token)
                                    .addParams("name", name)
                            ,
                            UrlConfig.UpdateUserName_URL, thisActivity, "修改名字中"
                            , new WNetUtil.WNetStringCallback() {
                                @Override
                                public void success(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optInt("code") == 100) {
                                            ToastUtil.CustomToast(thisActivity.getApplicationContext(), "修改成功");
                                            App.UserName = name;
                                            thisActivity.finish();
                                        } else
                                            ToastUtil.CustomToast(thisActivity.getApplicationContext(), "修改失败");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void error(Call call, Exception e, int id) {
                                    ToastUtil.CustomToast(thisActivity.getApplicationContext(), "修改失败");
                                }
                            });
                }
                break;
        }
    }
}
