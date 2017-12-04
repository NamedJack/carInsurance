package com.wongxd.carinsurance.fgt;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.LoginActivity;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.MainActivity;
import com.wongxd.carinsurance.aty.personal.CustomManagerActivity;
import com.wongxd.carinsurance.aty.personal.DataAnyAnalysisActivity;
import com.wongxd.carinsurance.aty.personal.UserInfoActivity;
import com.wongxd.carinsurance.bean.personal.PersonalBean;
import com.wongxd.carinsurance.downLoadApk.DownloadFileService;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideCircleTransform;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class PersonalFragment extends BaseFragment {

    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_infor)
    ImageView userInfor;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.tv_notpay)
    TextView tvNotpay;
    @BindView(R.id.rl_notpay)
    RelativeLayout rlNotpay;
    @BindView(R.id.xian)
    View xian;
    @BindView(R.id.tv_complite)
    TextView tvComplite;
    @BindView(R.id.rl_complite)
    RelativeLayout rlComplite;
    @BindView(R.id.rl_customer_manager)
    RelativeLayout rlCustomerManager;
    @BindView(R.id.rl_data_analysis)
    RelativeLayout rlDataAnalysis;
    @BindView(R.id.rl_updatecheck)
    RelativeLayout rlUpdatecheck;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    private MainActivity mainActivity;
    private PersonalBean personalBean;
    private boolean isDownloadNewApk = false;

    public PersonalFragment() {
    }

    @Override
    public BaseFragment newFragment() {
        return new PersonalFragment();
    }


    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_personal;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    boolean isGetInfo = false;

    //获取个人信息后展示
    private void initInfo(PersonalBean bean) {
        if (null == bean) return;
        personalBean = bean;
        tvComplite.setText(bean.getData().getPayCount() + "");
        tvNotpay.setText(bean.getData().getNoPayCount() + "");

        userName.setText(bean.getData().getUser().getRelName());
//        GlideLoader.LoadAsCircleImage(getContext().getApplicationContext(),
//                UrlConfig.Host_URL + bean.getData().getUser().getHeadUrl()
//                , ivUser);

        Glide.with(getContext().getApplicationContext())
                .load(bean.getData().getUser().getHeadUrl())
                .transform(new GlideCircleTransform(getContext().getApplicationContext()))
                .placeholder(R.drawable.user_img)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .into(ivUser);
        Logger.e("头像加载地址--" + bean.getData().getUser().getHeadUrl());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            getUserInfo();
        }
    }

    /**
     * 获取信息
     */
    private void getUserInfo() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.getSelectedSheet() != 3) return; //如果底部菜单不是个人
        WNetUtil.StringCallBack(
                OkHttpUtils.post().url(UrlConfig.GetPersonalInfo_URL).addParams("token", App.token)
                , UrlConfig.GetPersonalInfo_URL, mainActivity, "更新个人信息"
                , new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        isGetInfo = false;
                        PersonalBean bean = gson.fromJson(response, PersonalBean.class);
                        if (null != bean && bean.getCode() == 100) {
                            bean.getData().getUser().setHeadUrl(UrlConfig.Host_URL + bean.getData().getUser().getHeadUrl());
                            isGetInfo = true;
                            initInfo(bean);
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(getActivity(), "获取个人信息失败");
                        isGetInfo = false;
                    }

                }

        );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != App.UserName) userName.setText(App.UserName);
        if (App.uploadNewHeader) {
            getUserInfo();  //setUserVisibleHint 与 onresume 深入理解
            App.uploadNewHeader = false;
        }
    }

    @Override
    public void initData(View view, Bundle saveInstance) {

    }

    @Override
    public void initView(View view, Bundle saveInstance) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    @OnClick({R.id.rl_user, R.id.rl_notpay, R.id.rl_complite, R.id.rl_data_analysis, R.id.rl_updatecheck, R.id.rl_logout, R.id.rl_customer_manager})
    public void onClick(View view) {

        if (!isGetInfo) {
            ToastUtil.CustomToast(mainActivity, "未获取到用户信息");
            return;
        }

        if (null == App.token) {
            ToastUtil.CustomToast(mainActivity.getApplicationContext(), "Token失效了，请重新登录。");
            startActivity(new Intent(mainActivity, LoginActivity.class));
            mainActivity.finish();
            return;
        }
        switch (view.getId()) {
            case R.id.rl_user:
                Intent infoIntent = new Intent(mainActivity, UserInfoActivity.class);
                infoIntent.putExtra("person", personalBean);
                startActivity(infoIntent);
                break;
            case R.id.rl_notpay:
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                App.ORDER_VIEWPAGER_SELECTED_POSTION = 0;
                viewPager.setCurrentItem(1);
                break;
            case R.id.rl_complite:
                ViewPager viewPager1 = (ViewPager) getActivity().findViewById(R.id.viewpager);
                App.ORDER_VIEWPAGER_SELECTED_POSTION = 1;
                viewPager1.setCurrentItem(1);
                break;
            case R.id.rl_customer_manager:
                startActivity(new Intent(mainActivity, CustomManagerActivity.class));
                break;
            case R.id.rl_data_analysis:
                startActivity(new Intent(mainActivity, DataAnyAnalysisActivity.class));
                break;
            case R.id.rl_updatecheck:
                if (isDownloadNewApk) {
                    ToastUtil.CustomToast(mainActivity, "新版本下载中，请查看通知栏进度。");
                    return;
                }
                WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.GetVesionNo_URL).addParams("token", App.token),
                        UrlConfig.GetVesionNo_URL,
                        (AppCompatActivity) getActivity(), "查询版本中",
                        new WNetUtil.WNetStringCallback() {
                            @Override
                            public void success(String response, int id) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.optInt("code") == 100) {
                                        final JSONObject i = jsonObject.optJSONObject("data");
                                        String version = i.optString("vesionNo");
                                        final String url = "http://" + i.optString("androidUrl");
                                        if (version.equals(getVersion())) {
                                            ToastUtil.CustomToast(getContext(), "已经是最新版本");
                                        } else {
                                            final NormalDialog dialog = new NormalDialog(getActivity());
                                            dialog.isTitleShow(false)//
                                                    .bgColor(Color.parseColor("#383838"))//
                                                    .cornerRadius(5)//
                                                    .content("检测到新版本\n确定下载吗？")//
                                                    .contentGravity(Gravity.CENTER)//
                                                    .contentTextColor(Color.parseColor("#ffffff"))//
                                                    .dividerColor(Color.parseColor("#222222"))//
                                                    .btnTextSize(15.5f, 15.5f)//
                                                    .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                                                    .btnPressColor(Color.parseColor("#2B2B2B"))//
                                                    .widthScale(0.85f)//
                                                    .showAnim(new Swing())//
                                                    .show();

                                            dialog.setOnBtnClickL(
                                                    () -> dialog.dismiss(),
                                                    () -> {

                                                        isDownloadNewApk = true;
                                                        //使用intetnservice下载
                                                        Intent downloadIntent = new Intent(getActivity(), DownloadFileService.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("url", url);
//                                                            bundle.putString("url", "http://www.appchina.com/market/d/1603385/cop.baidu_0/com.google.android.apps.maps.apk");
                                                        downloadIntent.putExtras(bundle);
                                                        getActivity().startService(downloadIntent);


                                                        //调用浏览器下载
//                                                            Intent intent = new Intent();
//                                                            intent.setAction("android.intent.action.VIEW");
//                                                            Uri content_url = Uri.parse("http://" + url);
//                                                            intent.setData(content_url);
//                                                            startActivity(intent);
                                                        dialog.superDismiss();
                                                    });
                                        }
                                    } else
                                        ToastUtil.CustomToast(getContext(), "查询失败  " + jsonObject.optInt("code") + "\n"
                                                + jsonObject.optString("msg"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void error(Call call, Exception e, int id) {
                                ToastUtil.CustomToast(getContext(), "查询失败  " + e.getMessage());
                            }
                        });
                break;
            case R.id.rl_logout:

                final NormalDialog dialog = new NormalDialog(getActivity());
                dialog.isTitleShow(false)//
                        .bgColor(Color.parseColor("#383838"))//
                        .cornerRadius(5)//
                        .content("确定注销吗?")//
                        .contentGravity(Gravity.CENTER)//
                        .contentTextColor(Color.parseColor("#ffffff"))//
                        .dividerColor(Color.parseColor("#222222"))//
                        .btnTextSize(15.5f, 15.5f)//
                        .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                        .btnPressColor(Color.parseColor("#2B2B2B"))//
                        .widthScale(0.85f)//
                        .showAnim(new BounceTopEnter())//
                        .show();

                dialog.setOnBtnClickL(
                        () -> dialog.dismiss(),
                        () -> {
                            App.token = null;
                            startActivity(new Intent(mainActivity, LoginActivity.class));
                            mainActivity.finish();
                            dialog.dismiss();
                        });

                break;
        }
    }

    public String getVersion() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getActivity().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int packageCode = packageInfo.versionCode;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
