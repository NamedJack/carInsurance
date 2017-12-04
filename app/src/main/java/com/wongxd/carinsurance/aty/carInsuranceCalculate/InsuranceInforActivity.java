package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.carInsurance.InsuranceDetailBean;
import com.wongxd.carinsurance.utils.SystemUtils;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideLoader;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 保单信息页面
 */
public class InsuranceInforActivity extends BaseSwipeActivity {

    @BindView(R.id.infor_img)
    ImageView infor_img;//保险公司的logo

    @BindView(R.id.money)
    TextView money;//合计总价

    @BindView(R.id.pay_btn)
    Button pay_btn; //购买按钮

    @BindView(R.id.regist_date_text)
    TextView regist_date_text; //商业险生效日期

    @BindView(R.id.qiangxian_riqi)
    TextView qiangxian_riqi;//交强险生效日期


    @BindView(R.id.heji_money)
    TextView heji_money;//合计金额

    @BindView(R.id.shangye_money)
    TextView shangye_money;//商业险金额

    @BindView(R.id.qiangxian_money)
    TextView qiangxian_money;//交强险金额

    @BindView(R.id.queren_pay_btn)
    Button queren_pay_btn;
    @BindView(R.id.ll_return)
    RelativeLayout llReturn;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.login_return_img)
    ImageView loginReturnImg;
    @BindView(R.id.login_lin)
    RelativeLayout loginLin;
    @BindView(R.id.infor_view)
    View inforView;
    @BindView(R.id.infor_text)
    TextView inforText;
    @BindView(R.id.infor_img_riqi)
    ImageView inforImgRiqi;
    @BindView(R.id.qiangxian_text)
    ImageView qiangxianText;
    @BindView(R.id.rv_force)
    RecyclerView rvForce;
    private AppCompatActivity thisActivity;
    private Context mContext;
    private List<InsuranceDetailBean.DataBean.InsuranceKindBean> list;
    private List<InsuranceDetailBean.DataBean.InsuranceKindBean> forceList;
    private RvAdapter adapter;
    private RvFoeceAdapter forceAdapter;

    private String truePolicyNo = "";// 返回的保单号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_infor);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
        String policyNo = getIntent().getStringExtra("policyNo");

        list = new ArrayList<>();
        forceList = new ArrayList<>();


        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.PolicyDetail_URL)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addParams("token", App.token)
                        .addParams("policyNo", policyNo),
                UrlConfig.PolicyDetail_URL, thisActivity, "获取保单详情中",
                new WNetUtil.WNetStringCallback() {


                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        InsuranceDetailBean detailBean = gson.fromJson(response, InsuranceDetailBean.class);
                        if (null != detailBean) {
                            if (detailBean.getCode() == 100) {
                                InsuranceDetailBean.DataBean dataBean = detailBean.getData();
                                truePolicyNo = dataBean.getPolicyNo();
                                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + dataBean.getImgUrl(), infor_img);
                                money.setText(dataBean.getTotalMoney() + "");
                                regist_date_text.setText(dataBean.getBizBeginDate() + "");
                                qiangxian_riqi.setText(dataBean.getForceBeginDate() + "");
                                qiangxian_money.setText("￥" + dataBean.getForcePolicyMoney() + "");
                                shangye_money.setText("￥" + dataBean.getBizPolicyMoney() + "");
                                heji_money.setText("￥" + dataBean.getTotalMoney() + "");
                                if (null != dataBean.getInsuranceKind()) {
                                    list.clear();

                                    for (int i = 0; i < dataBean.getInsuranceKind().size(); i++) {
                                        if (dataBean.getInsuranceKind().get(i).getPolicyType() == 3) {
                                            forceList.add(dataBean.getInsuranceKind().get(i));
                                        } else {
                                            list.add(dataBean.getInsuranceKind().get(i));
                                        }
                                    }

                                    adapter = new RvAdapter();
                                    rv.setAdapter(adapter);
                                    rv.setLayoutManager(new LinearLayoutManager(mContext));

                                    forceAdapter = new RvFoeceAdapter();
                                    rvForce.setAdapter(forceAdapter);
                                    rvForce.setLayoutManager(new LinearLayoutManager(mContext));

                                }

                            } else
                                ToastUtil.CustomToast(mContext, detailBean.getCode() + "  " + detailBean.getMsg());

                        } else ToastUtil.CustomToast(mContext, "返回结果有误");
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(mContext, "请求出错了 " + e.getMessage());
                    }
                }

        );

    }


    @OnClick({R.id.queren_pay_btn, R.id.pay_btn, R.id.ll_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_btn: //顶部购买按钮
            case R.id.queren_pay_btn://确认购买
                if (!truePolicyNo.equals("")) {

                    WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.ConfirmBuy_URL)
                                    .addParams("token", App.token)
                                    .addParams("policyNo", truePolicyNo),
                            UrlConfig.ConfirmBuy_URL, thisActivity, "提交订单中",
                            new WNetUtil.WNetStringCallback() {
                                @Override
                                public void success(String response, int id) {
                                    Logger.d(response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optInt("code") == 200) {
                                            App.requestCount = 1;
                                            ToastUtil.CustomToast(mContext, jsonObject.optString("msg"));
                                            SystemUtils.openUrlByBrowser(mContext, jsonObject.optString("payUrl"));
                                            App.buyStatu = 1;
                                            App.orderStatusChanged = true;
                                            finish();
                                        } else
                                            ToastUtil.CustomToast(mContext, "返回信息有误  " + jsonObject.optString("msg"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void error(Call call, Exception e, int id) {
                                    ToastUtil.CustomToast(mContext, "请求出错" + e.getMessage());
                                }
                            }
                    );


                }

                break;
            case R.id.ll_return:
                thisActivity.finish();
                break;
            default:
                break;
        }
    }


    class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_insurance_detail_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            InsuranceDetailBean.DataBean.InsuranceKindBean item = list.get(position);
            //险种类型（1.主险 2.附加险 3.交强险）
            switch (item.getPolicyType()) {
                case 1:
                    holder.iv.setBackground(getResources().getDrawable(R.drawable.zhuxian_fang));
                    break;
                case 2:
                    holder.iv.setBackground(getResources().getDrawable(R.drawable.fujia_fang));
                    break;
            }

            holder.tv1.setText(item.getInsureName());
            if (item.getRemarkMsg().equals("")) {
                holder.tvMsg.setVisibility(View.GONE);
            } else {
                holder.tvMsg.setVisibility(View.VISIBLE);
                holder.tvMsg.setText(item.getRemarkMsg());
            }
            holder.tv2.setText(item.getPolicyFei() + "");
            if (null != item.getShowPolicyMoney()) {
                holder.tv3.setText(item.getShowPolicyMoney());
            } else {
                holder.tv3.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv1;
            private TextView tv2;
            private TextView tv3;
            private TextView tvMsg;
            private ImageView iv;


            public ViewHolder(View itemView) {
                super(itemView);
                tv1 = (TextView) itemView.findViewById(R.id.tv1);
                tv2 = (TextView) itemView.findViewById(R.id.tv2);
                tv3 = (TextView) itemView.findViewById(R.id.tv3);
                tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }


        }
    }

    class RvFoeceAdapter extends RecyclerView.Adapter<RvFoeceAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_insurance_detail_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            InsuranceDetailBean.DataBean.InsuranceKindBean item = forceList.get(position);
            holder.iv.setBackground(getResources().getDrawable(R.drawable.qita_fang));
            holder.tv1.setText(item.getInsureName());
            if (item.getRemarkMsg().equals("")) {
                holder.tvMsg.setVisibility(View.GONE);
            } else {
                holder.tvMsg.setVisibility(View.VISIBLE);
                holder.tvMsg.setText(item.getRemarkMsg());
            }
            holder.tv2.setText(item.getPolicyFei() == 0 ? "--" : item.getPolicyFei() + "");
            if (null != item.getShowPolicyMoney()) {
                holder.tv3.setText(item.getShowPolicyMoney());
            } else {
                holder.tv3.setText("");
            }

        }

        @Override
        public int getItemCount() {
            return forceList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv1;
            private TextView tv2;
            private TextView tv3;
            private TextView tvMsg;
            private ImageView iv;


            public ViewHolder(View itemView) {
                super(itemView);
                tv1 = (TextView) itemView.findViewById(R.id.tv1);
                tv2 = (TextView) itemView.findViewById(R.id.tv2);
                tv3 = (TextView) itemView.findViewById(R.id.tv3);
                tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }


        }
    }
}
