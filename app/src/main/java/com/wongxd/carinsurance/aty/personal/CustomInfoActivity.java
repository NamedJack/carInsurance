package com.wongxd.carinsurance.aty.personal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.personal.CustomDetailBean;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CustomInfoActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_car_code)
    TextView tvCarCode;
    @BindView(R.id.car_cade)
    TextView carCade;
    @BindView(R.id.engine)
    TextView engine;
    @BindView(R.id.tv_engine_code)
    TextView tvEngineCode;
    @BindView(R.id.brand)
    TextView brand;
    @BindView(R.id.tv_car_brand)
    TextView tvCarBrand;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rg2)
    RadioGroup rg2;
    @BindView(R.id.car_name_text)
    TextView carNameText;
    @BindView(R.id.tv_people_name)
    TextView tvPeopleName;
    @BindView(R.id.car_idcar_text)
    TextView carIdcarText;
    @BindView(R.id.tv_id_card)
    TextView tvIdCard;
    @BindView(R.id.car_iphone_text)
    TextView carIphoneText;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.regist_img)
    ImageView registImg;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    @BindView(R.id.tv_force_start_time)
    TextView tvForceStartTime;
    @BindView(R.id.rl_force_start_time)
    RelativeLayout rlForceStartTime;
    @BindView(R.id.tv_force_end_time)
    TextView tvForceEndTime;
    @BindView(R.id.rl_force_end_time)
    RelativeLayout rlForceEndTime;
    @BindView(R.id.tv_guohu_time)
    TextView tvGuohuTime;
    @BindView(R.id.rl_guohu_time)
    RelativeLayout rlGuohuTime;
    @BindView(R.id.v_guohu_time)
    View vGuohuTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_adress)
    EditText etAdress;
    @BindView(R.id.tv_detail_adress)
    TextView tvDetailAdress;
    @BindView(R.id.et_detail_adress)
    EditText etDetailAdress;
    private String id;
    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_info);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getInfo(id);

        etAdress.setFocusable(false);
        etAdress.setFocusableInTouchMode(false);
        etDetailAdress.setFocusable(false);
        etDetailAdress.setFocusableInTouchMode(false);

    }

    private void getInfo(String id) {
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.CustomDetailInfo_URL)
                        .addParams("policyNo", id)
                        .addParams("token", App.token),
                UrlConfig.CustomDetailInfo_URL,
                thisActivity, "获取客户信息"
                , new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Gson gson = new Gson();
                        CustomDetailBean detailBean = gson.fromJson(response, CustomDetailBean.class);
                        if (detailBean.getCode() == 100) {
                            CustomDetailBean.DataBean.CarBean car = detailBean.getData().getCar();
                            final CustomDetailBean.DataBean.PeopleBean people = detailBean.getData().getPeople();
                            tvCarCode.setText(car.getVehicleFrameNo());
                            tvEngineCode.setText(car.getEngineNo());
                            tvCarBrand.setText(car.getBrandName());
                            tvRegist.setText(car.getRegisterTime());

                            tvForceStartTime.setText(car.getForceBeginDate());
                            tvForceEndTime.setText(car.getForceEndDate());

                            tvStartTime.setText(car.getBizBeginDate());
                            tvEndTime.setText(car.getBizEndDate());
                            if (car.getSpecialCarFlag() == 1) {
                                //是否过户 1 是
                                rb1.setChecked(true);
                                tvGuohuTime.setText(car.getSpecialCarDate());
                                rlGuohuTime.setVisibility(View.VISIBLE);
                                vGuohuTime.setVisibility(View.VISIBLE);

                            } else rb2.setChecked(true);

                            if (car.getLoanCarFlag() == 1) {
                                //是否贷款 1 是
                                rb3.setChecked(true);
                            } else rb4.setChecked(true);
                            rb1.setClickable(false);
                            rb2.setClickable(false);
                            rb3.setClickable(false);
                            rb4.setClickable(false);


                            tvPeopleName.setText(people.getOwnerName());
                            tvPhone.setText(people.getOwnerPhoneNo());
                            tvIdCard.setText(people.getOwnerIdNo());

                            etAdress.setText(people.getAddress());
                            etDetailAdress.setText(people.getDetailedAddress());

                            tvCall.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkPermission(new CheckPermListener() {
                                                        @Override
                                                        public void superPermission() {
                                                            Uri uri = Uri.parse("tel:" + people.getOwnerPhoneNo());
                                                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                                                            startActivity(intent);
                                                        }
                                                    }, "请赋予打电话的权限",
                                            Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }
                            });

                        } else ToastUtil.CustomToast(mContext, "获取失败");
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(mContext, "获取失败");
                    }
                }
        );
    }

    @OnClick({R.id.iv_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;

        }
    }
}
