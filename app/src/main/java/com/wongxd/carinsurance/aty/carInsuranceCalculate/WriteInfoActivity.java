package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.China;
import com.wongxd.carinsurance.bean.PostInfoResultBean;
import com.wongxd.carinsurance.bean.ProvinceBean;
import com.wongxd.carinsurance.bean.carInsurance.CarBrandBean;
import com.wongxd.carinsurance.bean.personal.CustomDetailBean;
import com.wongxd.carinsurance.custom.TopListView;
import com.wongxd.carinsurance.net.InfomationPost;
import com.wongxd.carinsurance.net.MyBaseObserver;
import com.wongxd.carinsurance.net.NetRequest;
import com.wongxd.carinsurance.utils.Low2Upper;
import com.wongxd.carinsurance.utils.SystemUtils;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.ResponseBody;

/**
 * 填写信息
 */
public class WriteInfoActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.et_car_cade_num)
    EditText etCarCadeNum;
    @BindView(R.id.et_engine_num)
    EditText etEngineNum;
    @BindView(R.id.tv_brand_num)
    TextView tvBrandNum;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.et_people_name)
    EditText etPeopleName;
    @BindView(R.id.et_idcard_num)
    TextView etIdcardNum;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_regist_time)
    TextView tvRegistTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_car_info)
    TextView tvCarInfo;
    @BindView(R.id.tv_force_start_time)
    TextView tvForceStartTime;
    @BindView(R.id.tv_gouhu_time)
    TextView tvGouhuTime;
    @BindView(R.id.card_guohu_time)
    CardView cardGuohuTime;
    @BindView(R.id.v_guohu_time)
    View vGuohuTime;
    @BindView(R.id.et_adress)
    EditText etAdress;
    @BindView(R.id.et_detail_adress)
    EditText etDetailAdress;
    @BindView(R.id.lv_carbrand)
    TopListView lvCarbrand;
    @BindView(R.id.et_brand)
    EditText etBrand;
    @BindView(R.id.ll_carBrand_search)
    LinearLayout llCarBrandSearch;
    @BindView(R.id.v_carBrand_datail)
    View vCarBrandDatail;
    @BindView(R.id.t)
    TextView t;
    @BindView(R.id.tv_brand_detail)
    TextView tvBrandDetail;
    @BindView(R.id.card_carBrand_datail)
    CardView cardCarBrandDatail;
    @BindView(R.id.rl_idcard_num)
    RelativeLayout rlIdcardNum;
    @BindView(R.id.tv_scan_ride_card)
    TextView tvScanRideCard;  //扫描行驶证
    @BindView(R.id.tv_scan_id_card)
    TextView tvScanIdCard;//扫描身份证
    @BindView(R.id.cb_is_one_person)
    CheckBox cbIsOnePerson; //车主是否为被保人
    @BindView(R.id.et_insuranss_name)
    EditText etInsuranssName; //被保人姓名
    @BindView(R.id.card_bao_name)
    CardView cardBaoName;
    @BindView(R.id.et_bao_card)
    TextView etBaoCard;//被保人身份证
    @BindView(R.id.card_bao_card)
    CardView cardBaoCard;
    @BindView(R.id.v_bao_name)
    View vBaoName;
    @BindView(R.id.v_bao_card)
    View vBaoCard;
    private Context mContext;
    private AppCompatActivity thisActivity;
    private List<CarBrandBean.DataBean> lvList;  //LvCarBrand 的数据提供
    private LvCarBrandAdapter lvAdapter;
    private CarBrandBean.DataBean selectedBrandName; //选择的品牌信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_info);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = getApplicationContext();

        lvList = new ArrayList<>();
        lvAdapter = new LvCarBrandAdapter();
        lvCarbrand.setAdapter(lvAdapter);

        btnSubmit.setClickable(false);
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        String carNum = intent.getStringExtra("carNum");


        initPreInfo(intent, carNum);

        initListener();

        initRegionPicker();

        etAdress.setFocusableInTouchMode(false);
        etAdress.setFocusable(false);
        etAdress.setOnClickListener(v -> pvOptions.show());

        etCarCadeNum.setTransformationMethod(new Low2Upper());
        etEngineNum.setTransformationMethod(new Low2Upper());
        etBrand.setTransformationMethod(new Low2Upper());


        initKeyBoard();
    }

    /**
     * 身份证输入框
     */
    private void initKeyBoard() {

        cardBaoCard.setOnClickListener(v -> {
            View keyBoard = View.inflate(mContext, R.layout.keyboard, null);
            Button btn0 = (Button) keyBoard.findViewById(R.id.btn_0);
            Button btn1 = (Button) keyBoard.findViewById(R.id.btn_1);
            Button btn2 = (Button) keyBoard.findViewById(R.id.btn_2);
            Button btn3 = (Button) keyBoard.findViewById(R.id.btn_3);
            Button btn4 = (Button) keyBoard.findViewById(R.id.btn_4);
            Button btn5 = (Button) keyBoard.findViewById(R.id.btn_5);
            Button btn6 = (Button) keyBoard.findViewById(R.id.btn_6);
            Button btn7 = (Button) keyBoard.findViewById(R.id.btn_7);
            Button btn8 = (Button) keyBoard.findViewById(R.id.btn_8);
            Button btn9 = (Button) keyBoard.findViewById(R.id.btn_9);
            Button btnx = (Button) keyBoard.findViewById(R.id.btn_x);
            ImageButton btno = (ImageButton) keyBoard.findViewById(R.id.btn_o);
            Button btnd = (Button) keyBoard.findViewById(R.id.btn_d);
            TextView tv = (TextView) keyBoard.findViewById(R.id.tv_amount);

            btn0.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 0));
            btn1.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 1));
            btn2.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 2));
            btn3.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 3));
            btn4.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 4));
            btn5.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 5));
            btn6.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 6));
            btn7.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 7));
            btn8.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 8));
            btn9.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 9));
            btnx.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + "x"));
            btnd.setOnClickListener(v12 -> {
                String s = tv.getText().toString();
                int length = s.length();
                if (length > 1) {
                    StringBuilder temp = new StringBuilder();

                    for (int i = 0; i < length - 1; i++) {
                        temp.append(s.charAt(i));
                    }
                    tv.setText(temp);
                } else tv.setText("");

            });
            tv.setText("");
            AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                    .setView(keyBoard)
                    .setTitle("请输入18位身份证号")
                    .setCancelable(true);

            Dialog dlg = builder.show();
            btno.setOnClickListener(v1 -> {
                if (tv.getText().toString().length() != 18) {
                    ToastUtil.CustomToast(mContext, "身份证号码必须为18位");
                }
                etBaoCard.setText(tv.getText().toString());
                dlg.dismiss();
            });
        });

        rlIdcardNum.setOnClickListener(v -> {

            View keyBoard = View.inflate(mContext, R.layout.keyboard, null);
            Button btn0 = (Button) keyBoard.findViewById(R.id.btn_0);
            Button btn1 = (Button) keyBoard.findViewById(R.id.btn_1);
            Button btn2 = (Button) keyBoard.findViewById(R.id.btn_2);
            Button btn3 = (Button) keyBoard.findViewById(R.id.btn_3);
            Button btn4 = (Button) keyBoard.findViewById(R.id.btn_4);
            Button btn5 = (Button) keyBoard.findViewById(R.id.btn_5);
            Button btn6 = (Button) keyBoard.findViewById(R.id.btn_6);
            Button btn7 = (Button) keyBoard.findViewById(R.id.btn_7);
            Button btn8 = (Button) keyBoard.findViewById(R.id.btn_8);
            Button btn9 = (Button) keyBoard.findViewById(R.id.btn_9);
            Button btnx = (Button) keyBoard.findViewById(R.id.btn_x);
            ImageButton btno = (ImageButton) keyBoard.findViewById(R.id.btn_o);
            Button btnd = (Button) keyBoard.findViewById(R.id.btn_d);
            TextView tv = (TextView) keyBoard.findViewById(R.id.tv_amount);

            btn0.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 0));
            btn1.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 1));
            btn2.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 2));
            btn3.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 3));
            btn4.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 4));
            btn5.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 5));
            btn6.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 6));
            btn7.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 7));
            btn8.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 8));
            btn9.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + 9));
            btnx.setOnClickListener(v12 -> tv.setText(tv.getText().toString() + "x"));
            btnd.setOnClickListener(v12 -> {
                String s = tv.getText().toString();
                int length = s.length();
                if (length > 1) {
                    StringBuilder temp = new StringBuilder();

                    for (int i = 0; i < length - 1; i++) {
                        temp.append(s.charAt(i));
                    }
                    tv.setText(temp);
                } else tv.setText("");

            });
            tv.setText("");
            AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                    .setView(keyBoard)
                    .setTitle("请输入18位身份证号")
                    .setCancelable(true);

            Dialog dlg = builder.show();
            btno.setOnClickListener(v1 -> {
                if (tv.getText().toString().length() != 18) {
                    ToastUtil.CustomToast(mContext, "身份证号码必须为18位");
                }
                etIdcardNum.setText(tv.getText().toString());
                dlg.dismiss();
            });
        });
    }


    private class LvCarBrandAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lvList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_carbrand, parent, false);
            }
            TextView tvCarBrandName = (TextView) convertView.findViewById(R.id.tv_brandName);
            TextView tvInfo = (TextView) convertView.findViewById(R.id.tv_info);

            CarBrandBean.DataBean item = lvList.get(position);
            tvCarBrandName.setText(item.getBrandName());
            String remark = TextUtils.isEmpty(item.getRemark()) ? "" : item.getRemark();
            String modelsYear = TextUtils.isEmpty(item.getModelsYear()) ? "" : item.getModelsYear();
            String transType = TextUtils.isEmpty(item.getTranType()) ? "" : item.getTranType();
            String seatNumber = TextUtils.isEmpty(item.getSeatNumber()) ? "" : item.getSeatNumber();
            String carValue = TextUtils.isEmpty(item.getCarValue()) ? "" : item.getCarValue();
            tvInfo.setText(remark + " " + modelsYear + " " + transType + "   "
                    + seatNumber + "座  参考价：" + carValue + "元");

            return convertView;
        }
    }

    /**
     * 初始化控件的监听
     */
    private void initListener() {

        tvBrandNum.setOnClickListener(v -> {
            if (View.VISIBLE == llCarBrandSearch.getVisibility())
                llCarBrandSearch.setVisibility(View.GONE);
            else llCarBrandSearch.setVisibility(View.VISIBLE);
        });
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                btnSubmit.setBackgroundColor(Color.GRAY);
                btnSubmit.setClickable(false);
            } else {
                btnSubmit.setBackgroundColor(getResources().getColor(R.color.textSelected));
                btnSubmit.setClickable(true);
            }
        });


        btnSearch.setOnClickListener(v -> {
            String brandName = etBrand.getText().toString().trim().toUpperCase();
            try {
                brandName = URLEncoder.encode(brandName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(brandName)) {
                ToastUtil.CustomToast(mContext, "请输入品牌");
            } else {
                WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.GetBrandByVehicleNo_URL)
                                .addParams("token", App.token)
                                .addParams("brandName", brandName),
                        UrlConfig.GetBrandByVehicleNo_URL,
                        thisActivity, "获取品牌信息中",
                        new WNetUtil.WNetStringCallback() {
                            @Override
                            public void success(String response, int id) {
                                Logger.e(response);
                                try {
                                    Gson gson = new Gson();
                                    CarBrandBean carBrandBean = gson.fromJson(response, CarBrandBean.class);
                                    if (null != carBrandBean && carBrandBean.getCode() == 100) {
                                        lvList.clear();
                                        lvList.addAll(carBrandBean.getData());
                                        lvAdapter.notifyDataSetChanged();
                                        lvCarbrand.setOnItemClickListener((parent, view, position, id1) -> {
                                            tvBrandNum.setText(lvList.get(position).getBrandName());
                                            selectedBrandName = lvList.get(position);
                                            String remark = TextUtils.isEmpty(selectedBrandName.getRemark()) ? "" : selectedBrandName.getRemark() + " ";
                                            String modelsYear = TextUtils.isEmpty(selectedBrandName.getModelsYear()) ? "" : selectedBrandName.getModelsYear() + " ";
                                            String tranType = TextUtils.isEmpty(selectedBrandName.getTranType()) ? "" : selectedBrandName.getTranType() + " ";
                                            String seatNumber = TextUtils.isEmpty(selectedBrandName.getSeatNumber()) ? "" : selectedBrandName.getSeatNumber() + " 座  ";
                                            String carValue = TextUtils.isEmpty(selectedBrandName.getCarValue()) ? "" : "参考价：" + selectedBrandName.getCarValue() + "元";
                                            tvBrandDetail.setText(remark + modelsYear + tranType + seatNumber + carValue);
                                            cardCarBrandDatail.setVisibility(View.VISIBLE);
                                            vCarBrandDatail.setVisibility(View.VISIBLE);
                                            llCarBrandSearch.setVisibility(View.GONE);
                                        });
                                    } else
                                        ToastUtil.CustomToast(mContext, carBrandBean.getMsg());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void error(Call call, Exception e, int id) {
                                ToastUtil.CustomToast(mContext, "获取品牌请求出错");
                            }
                        }
                );
            }

        });


        rb1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            cardGuohuTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            vGuohuTime.setVisibility(isChecked ? View.VISIBLE : View.GONE);


        });


        //车主是否为被保人
        cbIsOnePerson.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cardBaoCard.setVisibility(View.GONE);
                cardBaoName.setVisibility(View.GONE);
                vBaoCard.setVisibility(View.GONE);
                vBaoName.setVisibility(View.GONE);
            } else {
                cardBaoCard.setVisibility(View.VISIBLE);
                cardBaoName.setVisibility(View.VISIBLE);
                vBaoCard.setVisibility(View.VISIBLE);
                vBaoName.setVisibility(View.VISIBLE);
            }

        });

        cbIsOnePerson.setChecked(true);

        tvScanRideCard.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 100);
            SystemUtils.backgroundAlpha(thisActivity, 0.5f);
        });


        tvScanIdCard.setOnClickListener(v -> {
            startActivityForResult(new Intent(thisActivity, TakeImgActivity.class), 101);
            SystemUtils.backgroundAlpha(thisActivity, 0.5f);
        });


    }


    /**
     * 查询行驶证
     */
    private void queryRideCard() {
        if (TextUtils.isEmpty(imgPath)) {
            ToastUtil.CustomToast(getApplicationContext(), "获取证件图片失败");
            return;
        }
        Bitmap bmp = BitmapFactory.decodeFile(imgPath);
        if (bmp == null) {
            ToastUtil.CustomToast(getApplicationContext(), "获取证件图片失败");
            return;
        }


        String dataValue = SystemUtils.BitmapToString(bmp);

        String bodys = "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"" + dataValue + "\"}}]}";
        Logger.e(bodys);
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveFile(bodys, "CarCard.txt");
            }
        }).start();
        OkHttpUtils.postString().url("http://dm-53.data.aliyun.com/rest/160601/ocr/ocr_vehicle.json")
                .addHeader("Authorization", "APPCODE " + "6ef3acedff1a479bb597b73adab531be")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .content(bodys)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                ToastUtil.CustomToast(getApplicationContext(), "证件图片识别失败");
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.optJSONArray("outputs");
                    JSONObject object = (JSONObject) array.get(0);
                    JSONObject value = object.optJSONObject("outputValue");
                    String dataValue = value.optString("dataValue");
                    Logger.e("行驶证信息  " + dataValue);
                    if (!TextUtils.isEmpty(dataValue)) {

                        //{"config_str":"","engine_num":"131591588","issue_date":"20130705","model":"别克牌SGM7164ATC","owner":"冯佳","plate_num":"川ANP911","register_date":"20130705","request_id":"20170901095020_2158348be1d17b0e22646a0d9b7b815e","success":true,"use_character":"非营运","vehicle_type":"小型轿车","vin":"LSGPB64U7DD299403"}
                        JSONObject info = new JSONObject(dataValue);

                        String model = info.optString("model");
                        String vin = info.optString("vin"); //车辆识别代号
                        String engine_num = info.optString("engine_num");
                        String owner = info.optString("owner");
                        String plate_num = info.optString("plate_num");
                        final String register_date = info.optString("register_date");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                etCarCadeNum.setText(vin);
                                etEngineNum.setText(engine_num);

                                String RegistTime = register_date;
                                RegistTime = RegistTime.substring(0, 4) + "年" + RegistTime.substring(4, 6) + "月"
                                        + RegistTime.substring(6, 8) + "日";
                                tvRegistTime.setText(RegistTime);
                                tvCarInfo.setText(plate_num);



                                String reg = "[\u4e00-\u9fa5]";

                                Pattern pat = Pattern.compile(reg);

                                Matcher mat=pat.matcher(model);

                                String repickStr = mat.replaceAll("");
                                etBrand.setText(repickStr);
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dialog != null && dialog.isShowing()) dialog.dismiss();
                }
            }
        });
    }


    /**
     * 查询身份证
     */
    private void queryIdCard() {
        if (TextUtils.isEmpty(imgPath)) {
            ToastUtil.CustomToast(getApplicationContext(), "获取证件图片失败");
            return;
        }
        Bitmap bmp = BitmapFactory.decodeFile(imgPath);
        if (bmp == null) {
            ToastUtil.CustomToast(getApplicationContext(), "获取证件图片失败");
            return;
        }

        String dataValue = SystemUtils.BitmapToString(bmp);

        String bodys = "{\"inputs\": [" +
                " {\"image\": {\"dataType\": 50,\"dataValue\": \"" + dataValue + "\"}," +
                "\"configure\": {\"dataType\": 50,\"dataValue\":\"{\\\"side\\\":\\\"face\\\"}\"}}]}";

        OkHttpUtils.postString().url("http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json")
                .addHeader("Authorization", "APPCODE " + "6ef3acedff1a479bb597b73adab531be")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .content(bodys)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                ToastUtil.CustomToast(getApplicationContext(), "证件图片识别失败");
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);
                //{"outputs": [{"outputLabel": "ocr_id","outputMulti": {},"outputValue": { "dataType": 50,"dataValue": "{\"address\":\"四川省巴中市巴州区玉山\",\"birth\":\"19931008\",\"config_str\":\"{\\\"side\\\":\\\"face\\\"}\",\"face_rect\":{\"angle\":-86.842056274414062,\"center\":{\"x\":570.8575439453125,\"y\":180.01969909667969},\"size\":{\"height\":145.22050476074219,\"width\":132.240234375}},\"name\":\"王晓东\",\"nationality\":\"汉\",\"num\":\"513701199310083111\",\"request_id\":\"20170831175841_d60527ca072df989bbefe42a7426f89a\",\"sex\":\"男\",\"success\":true}"}}]}
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.optJSONArray("outputs");
                    JSONObject object = (JSONObject) array.get(0);
                    JSONObject value = object.optJSONObject("outputValue");
                    String dataValue = value.optString("dataValue");
                    Logger.e("身份信息 " + dataValue);
                    if (!TextUtils.isEmpty(dataValue)) {
                        JSONObject info = new JSONObject(dataValue);
                        String name = info.optString("name");
                        String nationality = info.optString("nationality");
                        String num = info.optString("num");
                        String sex = info.optString("sex");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etPeopleName.setText(name);
                                etIdcardNum.setText(num);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dialog != null && dialog.isShowing()) dialog.dismiss();
                }

            }
        });

        //        Logger.e(bodys);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                saveFile(bodys,"IdCard.txt");
//            }
//        }).start();
    }

    public static void saveFile(String str, String fileName) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;
        } else  // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + fileName;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            } else {
                file.delete();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String imgPath;
    private ProgressDialog dialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SystemUtils.backgroundAlpha(this, 1f);
        if (resultCode == RESULT_OK && data != null) {
            dialog = new ProgressDialog(WriteInfoActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("识别证件中");
            dialog.show();
            if (requestCode == 101) {//拍身份证
                imgPath = data.getStringExtra("path");
                new Thread(this::queryIdCard).start();

            } else if (requestCode == 100) {//行驶证
                imgPath = data.getStringExtra("path");
                new Thread(this::queryRideCard).start();

            } else {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        }
    }

    /**
     * 根据前一个页面的数据 初始化当前页
     *
     * @param intent
     * @param carNum
     */
    private void initPreInfo(Intent intent, String carNum) {
        boolean isNewCar = intent.getBooleanExtra("isNewCar", false);
        if (isNewCar) tvCarInfo.setText("新车未上牌");
        else {
            tvCarInfo.setText(carNum);
            //通过车牌获取信息
            WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.GetPolicyInfoByCarNo_URL)
                            .addParams("carNo", carNum)
                            .addParams("token", App.token),
                    UrlConfig.GetPolicyInfoByCarNo_URL,
                    thisActivity, "获取保单信息"
                    , new WNetUtil.WNetStringCallback() {
                        @Override
                        public void success(String response, int id) {
                            Gson gson = new Gson();
                            Logger.e(response);
                            CustomDetailBean detailBean = gson.fromJson(response, CustomDetailBean.class);
                            if (detailBean.getCode() == 100) {
                                CustomDetailBean.DataBean.CarBean car = detailBean.getData().getCar();
                                final CustomDetailBean.DataBean.PeopleBean people = detailBean.getData().getPeople();
                                etCarCadeNum.setText(car.getVehicleFrameNo());
                                etEngineNum.setText(car.getEngineNo());
//                                20170614000000
                                String forceStartTime = car.getForceBeginDate();
                                tvForceStartTime.setText(forceStartTime.substring(0, 4) + "年" + forceStartTime.substring(4, 6) + "月"
                                        + forceStartTime.substring(6, 8) + "日");

                                String RegistTime = car.getRegisterTime();
                                tvRegistTime.setText(RegistTime.substring(0, 4) + "年" + RegistTime.substring(4, 6) + "月"
                                        + RegistTime.substring(6, 8) + "日");

                                String startTime = car.getBizBeginDate();
                                tvStartTime.setText(startTime.substring(0, 4) + "年" + startTime.substring(4, 6) + "月"
                                        + startTime.substring(6, 8) + "日");

                                if (car.getSpecialCarFlag() == 1) {
                                    //是否过户 1 是
                                    rb1.setChecked(true);
                                    String guohuTime = car.getSpecialCarDate();
                                    tvGouhuTime.setText(guohuTime.substring(0, 4) + "年" + guohuTime.substring(4, 6) + "月"
                                            + guohuTime.substring(6, 8) + "日");
                                    vGuohuTime.setVisibility(View.VISIBLE);
                                    cardGuohuTime.setVisibility(View.VISIBLE);

                                } else rb2.setChecked(true);

                                if (car.getLoanCarFlag() == 1) {
                                    //是否贷款 1 是
                                    rb3.setChecked(true);
                                } else rb4.setChecked(true);


                                etPeopleName.setText(people.getOwnerName());
                                etPhone.setText(people.getOwnerPhoneNo());
                                etIdcardNum.setText(people.getOwnerIdNo());

                                etAdress.setText(people.getAddress());
                                etDetailAdress.setText(people.getDetailedAddress());

                                province = people.getOwnerProvince();
                                city = people.getOwnerCity();
                                area = people.getOwnerArea();

                            } else ToastUtil.CustomToast(mContext, detailBean.getMsg());
                        }

                        @Override
                        public void error(Call call, Exception e, int id) {
                            ToastUtil.CustomToast(mContext, "获取失败");
                        }
                    }
            );
        }
    }

    @OnClick({R.id.iv_return, R.id.btn_submit, R.id.tv_writeGuide, R.id.tv_regist_time, R.id.tv_start_time
            , R.id.rl_force_start_time, R.id.rl_guohu_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.btn_submit:
                postInfo();
                break;
            case R.id.tv_writeGuide:
                startActivity(new Intent(thisActivity, ShowWriteGuideActivity.class));
                break;
            case R.id.tv_regist_time:
                showDatePickDlg(tvRegistTime);
                break;
            case R.id.tv_start_time:
                showDatePickDlg(tvStartTime);
                break;
            case R.id.rl_force_start_time:
                showDatePickDlg(tvForceStartTime);
                break;
            case R.id.rl_guohu_time:
                showDatePickDlg(tvGouhuTime);
                break;
        }
    }

    private void postInfo(){
        Map<String, Object> infoMap = new HashMap<>();
        if (null == selectedBrandName) {
            ToastUtil.CustomToast(mContext, "请选择车型信息");
            return;
        }
        String car_code_num = etCarCadeNum.getText().toString().trim().toUpperCase();
        String engine_num = etEngineNum.getText().toString().trim().toUpperCase();
        String brand_num = tvBrandNum.getText().toString().trim();
        String register_time = tvRegistTime.getText().toString().trim();
        String start_time = tvStartTime.getText().toString().trim();
        String force_start_time = tvForceStartTime.getText().toString().trim();

        register_time = register_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
        start_time = start_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
        force_start_time = force_start_time.replace("年", "").replace("月", "").replace("日", "") + "000000";

        boolean isGuoHu = rb1.isChecked();
        boolean isDaiKuan = rb3.isChecked();
        boolean isNewPower = rb5.isChecked();
        String name = etPeopleName.getText().toString().trim();
        String idNum = etIdcardNum.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String adress = etAdress.getText().toString().trim();
        String detail_adress = etDetailAdress.getText().toString().trim();



        infoMap.put("registerTime", register_time);
        infoMap.put("engineNo", engine_num);
        infoMap.put("licenseNo", tvCarInfo.getText().toString().trim().toUpperCase().equals("新车未上牌") ?
                "" : tvCarInfo.getText().toString().trim().toUpperCase());
        infoMap.put("vehicleFrameNo", car_code_num);
        infoMap.put("bizBeginDate", start_time);
        infoMap.put("forceBeginDate", force_start_time);
        infoMap.put("specialCarFlag", isGuoHu ? 1 + "" : 0 + "");
        infoMap.put("loanCarFlag", isDaiKuan ? 1 + "" : 0 + "");
        infoMap.put("isNew_energy",isNewPower ? 1 + "" : 0 + "");
        infoMap.put("brandName", selectedBrandName.getBrandName());
        infoMap.put("seatNumber", selectedBrandName.getSeatNumber());
        infoMap.put("carValue", selectedBrandName.getCarValue());
        infoMap.put("modelsYear", selectedBrandName.getModelsYear());
        infoMap.put("remark", selectedBrandName.getRemark());
        infoMap.put("paCode", selectedBrandName.getPaCode());
        infoMap.put("powerType", selectedBrandName.getPowerType());
        infoMap.put("ownerName", name);
        infoMap.put("ownerIdNo", idNum);
        infoMap.put("ownerPhoneNo", phone);
        infoMap.put("address", adress);
        infoMap.put("detailedAddress", detail_adress);
        infoMap.put("ownerProvince", province);
        infoMap.put("ownerCity", city);
        infoMap.put("ownerArea", area);

        if (!cbIsOnePerson.isChecked()) {
            String num = etBaoCard.getText().toString().trim();
            String pName = etInsuranssName.getText().toString().trim();
            if (TextUtils.isEmpty(num) || TextUtils.isEmpty(pName)) {
                ToastUtil.CustomToast(getApplicationContext(), "请输入被保人信息！");
                return;
            }
            infoMap.put("insrntName", pName);
            infoMap.put("insrntIdNo", num);
        }
        if (rb1.isChecked()) {
            String guohu_time = tvGouhuTime.getText().toString().trim();
            guohu_time = guohu_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
            if (guohu_time.equals("请选择")) {
                ToastUtil.CustomToast(mContext, "请选择过户日期");
                return;
            } else infoMap.put("specialCarDate", guohu_time);
        }

        NetRequest.getInstance(UrlConfig.Host_URL).create(InfomationPost.class)
                .postInfo(infoMap, App.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<PostInfoResultBean>(this,true,"信息提交中...") {
                    @Override
                    public void _doNext(PostInfoResultBean resultBean) {
                        if(resultBean.getCode() == 100){
                            Intent i = new Intent(thisActivity, InsuranceSelectActivity.class);
                            i.putExtra("peopleId", resultBean.getData().getPeopleId() + "");
                            i.putExtra("carId", resultBean.getData().getPeopleId() + "");
                            startActivity(i);
                        }else {
                            ToastUtil.CustomToast(mContext, resultBean.getMsg());
                        }
                    }
                });
    }


    /**
     * 提交信息
     */
    private void submitInfo() {
        if (null == selectedBrandName) {
            ToastUtil.CustomToast(mContext, "请选择车型信息");
            return;
        }
        String car_code_num = etCarCadeNum.getText().toString().trim().toUpperCase();
        String engine_num = etEngineNum.getText().toString().trim().toUpperCase();
        String brand_num = tvBrandNum.getText().toString().trim();
        String register_time = tvRegistTime.getText().toString().trim();
        String start_time = tvStartTime.getText().toString().trim();
        String force_start_time = tvForceStartTime.getText().toString().trim();

        register_time = register_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
        start_time = start_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
        force_start_time = force_start_time.replace("年", "").replace("月", "").replace("日", "") + "000000";

        boolean isGuoHu = rb1.isChecked();
        boolean isDaiKuan = rb3.isChecked();
        boolean isNewPower = rb5.isChecked();
        String name = etPeopleName.getText().toString().trim();
        String idNum = etIdcardNum.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String adress = etAdress.getText().toString().trim();
        String detail_adress = etDetailAdress.getText().toString().trim();

        //private String insrntName;//被保人姓名
        //private String insrntIdNo;//被保人身份证号码

        PostFormBuilder builder = OkHttpUtils.post().url(UrlConfig.SaveCarAndPeople_URL)
                .addParams("token", App.token)
                .addParams("engineNo", engine_num)
                .addParams("registerTime", register_time)
                .addParams("licenseNo", tvCarInfo.getText().toString().trim().toUpperCase().equals("新车未上牌") ?
                        "" : tvCarInfo.getText().toString().trim().toUpperCase())
                .addParams("vehicleFrameNo", car_code_num)
                .addParams("bizBeginDate", start_time)
                .addParams("forceBeginDate", force_start_time)
                .addParams("specialCarFlag", isGuoHu ? 1 + "" : 0 + "")
                .addParams("loanCarFlag", isDaiKuan ? 1 + "" : 0 + "")
                .addParams("isNew_energy",isNewPower ? 1 + "" : 0 + "")
                .addParams("brandName", selectedBrandName.getBrandName())
                .addParams("seatNumber", selectedBrandName.getSeatNumber())
                .addParams("carValue", selectedBrandName.getCarValue())
                .addParams("modelsYear", selectedBrandName.getModelsYear())
                .addParams("remark", selectedBrandName.getRemark())
                .addParams("paCode", selectedBrandName.getPaCode())
                .addParams("powerType", selectedBrandName.getPowerType())
                .addParams("ownerName", name)
                .addParams("ownerIdNo", idNum)
                .addParams("ownerPhoneNo", phone)
                .addParams("address", adress)
                .addParams("detailedAddress", detail_adress)
                .addParams("ownerProvince", province)
                .addParams("ownerCity", city)
                .addParams("ownerArea", area);
        Logger.e("地区" + area);

        if (!cbIsOnePerson.isChecked()) {
            String num = etBaoCard.getText().toString().trim();
            String pName = etInsuranssName.getText().toString().trim();
            if (TextUtils.isEmpty(num) || TextUtils.isEmpty(pName)) {
                ToastUtil.CustomToast(getApplicationContext(), "请输入被保人信息！");
                return;
            }
            builder.addParams("insrntName", pName);
            builder.addParams("insrntIdNo", num);
        }

        if (rb1.isChecked()) {
            String guohu_time = tvGouhuTime.getText().toString().trim();
            guohu_time = guohu_time.replace("年", "").replace("月", "").replace("日", "") + "000000";
            if (guohu_time.equals("请选择")) {
                ToastUtil.CustomToast(mContext, "请选择过户日期");
                return;
            } else builder.addParams("specialCarDate", guohu_time);
        }


        WNetUtil.StringCallBack(builder, UrlConfig.SaveCarAndPeople_URL, thisActivity, "上传信息中",
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            Log.e("msg", jsonObject.optInt("code") + "提交数据");
                            if (jsonObject.optInt("code") == 100) {
                                JSONObject j = jsonObject.optJSONObject("data");
                                ToastUtil.CustomToast(mContext, "保存成功");
                                Intent i = new Intent(thisActivity, InsuranceSelectActivity.class);
                                i.putExtra("peopleId", j.optInt("peopleId") + "");
                                i.putExtra("carId", j.optInt("carId") + "");
                                startActivity(i);
                            } else {
                                ToastUtil.CustomToast(mContext, jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(mContext, "保存请求出错");
                    }
                });
    }


    /**
     * 展示时间选择dialog
     *
     * @param tv
     */
    protected void showDatePickDlg(final TextView tv) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(thisActivity, (view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear++;
            String month = monthOfYear > 9 ? monthOfYear + "" : "0" + monthOfYear;
            String day = dayOfMonth > 9 ? dayOfMonth + "" : "0" + dayOfMonth;
            tv.setText(year + "年" + month + "月" + day + "日");
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        App.requestCount = 1;
    }


    //################################################### 地址选择 #############################################################
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

        pvOptions = new OptionsPickerView.Builder(thisActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {

                province = options1Items.get(options1).getPickerViewText();
                city = options2Items.get(options1).get(option2);
                area = options3Items.get(options1).get(option2).get(options3);

                if (city.equals("") || area.equals("")) {
                    area = city;
                    city = province;
                }


                String tx = options1Items.get(options1).getPickerViewText() + " "
                        + options2Items.get(options1).get(option2) + " "
                        + options3Items.get(options1).get(option2).get(options3);

                etAdress.setText(tx);


            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("收货地址选择")//标题
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


