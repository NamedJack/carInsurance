package com.wongxd.carinsurance.aty.photoUpload;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.photoUpload.PhotoDetailBean;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideLoader;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AuditPhotoActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.textone)
    TextView textone;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.login_lin2)
    RelativeLayout loginLin2;
    @BindView(R.id.texttwo)
    TextView texttwo;
    @BindView(R.id.text33)
    TextView text33;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.login_lin3)
    RelativeLayout loginLin3;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.iv_mark)
    ImageView ivMark;
    @BindView(R.id.tv_mark1)
    TextView tvMark1;
    @BindView(R.id.tv_mark2)
    TextView tvMark2;
    private AppCompatActivity thisActivity;
    private Context mContext;
    private String policyId = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_photo);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
        policyId = getIntent().getStringExtra("policyId");
        boolean isUploadYet = getIntent().getBooleanExtra("isUploadYet", false);

        if (isUploadYet) {
            tvMark1.setVisibility(View.GONE);
            tvMark2.setVisibility(View.GONE);
        }

        final List<ImageView> imageViews1 = new ArrayList<>();
        imageViews1.add(iv1);
        imageViews1.add(iv2);
        imageViews1.add(iv3);


        final List<ImageView> imageViews2 = new ArrayList<>();
        imageViews2.add(iv4);
        imageViews2.add(iv5);


        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.QueryImgByPolicyId_URL)
                        .addParams("token", App.token)
                        .addParams("policyId", policyId)
                , UrlConfig.QueryImgByPolicyId_URL, thisActivity,
                "获取照片中", new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Gson gson = new Gson();
                        PhotoDetailBean photoDetailBean = gson.fromJson(response, PhotoDetailBean.class);
                        if (photoDetailBean.getCode() == 100) {
                            List<PhotoDetailBean.DataBean.ListVehiclesBean> listVehicles = photoDetailBean.getData().getListVehicles();
                            List<PhotoDetailBean.DataBean.ListDriverBean> listDrivers = photoDetailBean.getData().getListDriver();


//                            行驶证审核状态 0.待上传 1.待审核 2.审核通过 3.审核失败
                            for (int i = 0; i < listVehicles.size(); i++) {
                                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + listVehicles.get(i).getVehiclesImg(),
                                        imageViews1.get(i));

                            }


//                            驾驶证审核状态 0.待上传 1.待审核 2.审核通过 3.审核失败
                            for (int i = 0; i < listDrivers.size(); i++) {
                                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + listDrivers.get(i).getDriverImg(),
                                        imageViews2.get(i));
                            }


                        } else ToastUtil.CustomToast(mContext, photoDetailBean.getMsg());
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(mContext, "获取请求出错");
                    }
                });
    }

    @OnClick(R.id.iv_return)
    public void onClick() {
        thisActivity.finish();
    }
}
