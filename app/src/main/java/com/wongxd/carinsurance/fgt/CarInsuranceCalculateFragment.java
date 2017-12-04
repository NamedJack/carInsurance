package com.wongxd.carinsurance.fgt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.MainActivity;
import com.wongxd.carinsurance.aty.carInsuranceCalculate.WriteInfoActivity;
import com.wongxd.carinsurance.bean.China;
import com.wongxd.carinsurance.bean.ProvinceBean;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.Low2Upper;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class CarInsuranceCalculateFragment extends BaseFragment {
    @BindView(R.id.custom_toolbar)
    RelativeLayout customToolbar;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.car_number)
    TextView carNumber;
    @BindView(R.id.et_car_number)
    EditText etCarNumber;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    private String oldCity = "";
    private MainActivity mainActivity;
    private String carFront = "";

    public CarInsuranceCalculateFragment() {
    }

    @Override
    public void initData(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    private double latitude = 0;// 经度
    private double longitude = 0;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 2: //获取车牌前缀
                    WNetUtil.StringCallBack(new PostFormBuilder().url(UrlConfig.GetBeforeCar_URL)
                                    .addParams("token", App.token)
                                    .addParams("cityName", city), UrlConfig.GetBeforeCar_URL, (AppCompatActivity) getActivity(), ""
                            , new WNetUtil.WNetStringCallback() {
                                @Override
                                public void success(String response, int id) {
                                    try {
                                        JSONObject jo = new JSONObject(response);
                                        if (jo.optInt("code") == 100) {
                                            JSONObject j = jo.optJSONObject("data");
                                            carFront = j.optString("carName");
                                            etCarNumber.setText(carFront);
                                        } else {
                                            ToastUtil.CustomToast(getActivity().getApplicationContext(), "请手动输入车牌前缀");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void error(Call call, Exception e, int id) {
                                    ToastUtil.CustomToast(getActivity().getApplicationContext(), "请手动输入车牌前缀");
                                }
                            }
                    );
                    break;
                case 1://处理定位

                    List<Address> addList = null;
                    Geocoder ge = new Geocoder(getActivity().getApplicationContext());
                    try {
                        addList = ge.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String locationString = "";
                    if (addList != null && addList.size() > 0) {
                        for (int i = 0; i < addList.size(); i++) {
                            Address ad = addList.get(i);
                            locationString = ad.getLocality();
                        }
                    }
                    if (!locationString.equals("")) {
                        city = locationString;
                        tvCity.setText(locationString);
                        handler.sendEmptyMessage(2);
                    }

                    break;
            }
            return true;
        }
    });

    LocationManager locationManager;
    private String locationProvider;

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etCarNumber.setFocusable(false);
                etCarNumber.setFocusableInTouchMode(false);
                etCarNumber.setText("");
            } else {
                etCarNumber.setFocusable(true);
                etCarNumber.setFocusableInTouchMode(true);
            }
        });


        etCarNumber.setTransformationMethod(new Low2Upper());

        initRegionPicker();

    }





    @Override
    public BaseFragment newFragment() {
        return new CarInsuranceCalculateFragment();
    }


    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_car_insrance_calculate;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (Build.VERSION.SDK_INT >= 23) {
            mainActivity.checkPermission(
                    () -> getLocation(), "需要定位权限，获取城市", Manifest.permission.ACCESS_FINE_LOCATION);

        } else getLocation();
    }

    private void getLocation() {
        //获取地理位置管理器
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            getActivity().runOnUiThread(() -> ToastUtil.CustomToast(getActivity().getApplicationContext(), "没有可用的位置提供器"));
            return;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            if (latitude == 0) {
                //不为空,显示地理位置经纬度
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
                handler.sendEmptyMessage(1);
            }
        }
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    if (latitude == 0) {
                        //不为空,显示地理位置经纬度
                        latitude = location.getLatitude(); // 经度
                        longitude = location.getLongitude(); // 纬度
                        handler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(locationProvider, 1000 * 10, 0,
                listener);

    }


    @OnClick({R.id.rl_city, R.id.btn_submit, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_city:
                pvOptions.show();
                break;
            case R.id.btn_submit:
                String c = tvCity.getText().toString().trim();
                String carN = etCarNumber.getText().toString().trim().toUpperCase();

                if (TextUtils.isEmpty(c)) {
                    ToastUtil.CustomToast(getActivity().getApplicationContext(), "请选择城市");
                } else if (TextUtils.isEmpty(carN) && !checkbox.isChecked()) {
                    ToastUtil.CustomToast(getActivity().getApplicationContext(), "非未上牌车的车牌信息为必填");
                } else {
                    Intent in = new Intent(getActivity(), WriteInfoActivity.class);
                    in.putExtra("city", city);
                    in.putExtra("carNum", carN);
                    in.putExtra("isNewCar", checkbox.isChecked());
                    startActivity(in);
                }
                break;
            case R.id.tv_agreement:
                break;
        }
    }


    public String province = "", city = " ", area = " ";
    //省
    private List<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    //市
    private List<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    //区 县
    private List<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    private void initData() {
        try {
            //解析json数据
            InputStream is = getResources().getAssets().open("city.json");

            int available = is.available();

            byte[] b = new byte[available];
            int read = is.read(b);
            //注意格式，utf-8 或者gbk  否则解析出来可能会出现乱码
            String json = new String(b, "GBK");

            // System.out.println(json);

            Gson gson = new Gson();
            China china = gson.fromJson(json, China.class);
            ArrayList<China.Province> citylist = china.citylist;
            //======省级
            for (China.Province province : citylist
                    ) {
                String provinceName = province.p;

//                 System.out.println("provinceName==="+provinceName);
                ArrayList<China.Province.Area> c = province.c;
                //选项1
                options1Items.add(new ProvinceBean(0, provinceName, "", ""));
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                //区级
                //选项2

                ArrayList<String> options2Items_01 = new ArrayList<String>();
                if (c != null) {
                    for (China.Province.Area area : c
                            ) {
                        //System.out.println("area------" + area.n + "------");

                        options2Items_01.add(area.n);
                        ArrayList<China.Province.Area.Street> a = area.a;
                        ArrayList<String> options3Items_01_01 = new ArrayList<String>();

                        //县级
                        if (a != null) {
                            for (China.Province.Area.Street street : a
                                    ) {
                                // System.out.println("street/////" + street.s);
                                options3Items_01_01.add(street.s);
                            }
                            options3Items_01.add(options3Items_01_01);
                        } else {
                            //县级为空的时候
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                    options2Items.add(options2Items_01);
                } else {
                    //区级为空的时候  国外
                    options2Items_01.add("");
                    options2Items.add(options2Items_01);
                }
                options3Items.add(options3Items_01);
                ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                options3Items_01_01.add("");
                options3Items_01.add(options3Items_01_01);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    OptionsPickerView pvOptions;

    //修改区域
    private void initRegionPicker() {


        initData();

        pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(option2);
                area = options3Items.get(options1).get(option2).get(options3);

                if (city.equals("") || area.equals("")) city = province;


                String tx = options1Items.get(options1).getPickerViewText() + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);
                city += "市";
                tvCity.setText(city);
                if (tvCity.getText().toString().trim().equals(oldCity)) {
                    return;
                } else {
                    oldCity = city;
                    handler.sendEmptyMessage(2);
                }
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
                .setTitleSize(22)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(20)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);


    }
}

