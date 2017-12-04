package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.StateLayout;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.carInsurance.InsuranceCompareBean;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 保险比价
 */
public class InsuranceParityActivity extends BaseSwipeActivity {

    @BindView(R.id.car_pnum)
    TextView tv_car_num; // 汽车LOGO

    @BindView(R.id.tv_car_user)
    TextView tv_car_user;//车主name

    @BindView(R.id.car_infortwo)
    TextView tv_car_infortwo;//车型号


    @BindView(R.id.rv)
    XRecyclerView rv;
    @BindView(R.id.login_return_img)
    ImageView loginReturnImg;
    @BindView(R.id.return_linearlayout)
    RelativeLayout returnLinearlayout;
    @BindView(R.id.login_lin)
    RelativeLayout loginLin;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    @BindView(R.id.suiwu)
    ImageView suiwu;
    @BindView(R.id.rl_selete)
    RelativeLayout rlSelete;
    @BindView(R.id.tv_dateone)
    TextView tvDateone;
    @BindView(R.id.tv_datetwo)
    TextView tvDatetwo;
    @BindView(R.id.xiugai)
    ImageView xiugai;
    @BindView(R.id.rl_date)
    RelativeLayout rlDate;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;

    private Context mContext;
    private AppCompatActivity thisActivity;
    private InsuranceCompareBean insuranceCompareBean;
    private List<InsuranceCompareBean.DataBean> list = new ArrayList<>();
    private RvAdapter adapter = new RvAdapter();
    private String dateShangYe = "";
    private String dateJiaoQiang = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_parity);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
        insuranceCompareBean = (InsuranceCompareBean) getIntent().getSerializableExtra("info");

        if (null == insuranceCompareBean || insuranceCompareBean.getData().size() == 0) {
            stateLayout.showEmptyView("没有保单信息");
        } else {
            InsuranceCompareBean.DataBean item = insuranceCompareBean.getData().get(0);
            tv_car_num.setText(item.getLicenseNo());
            tv_car_user.setText(item.getOwnerName());
            tv_car_infortwo.setText(item.getBrandName() + " \n" + item.getSeatNumber() + "座   参考价(" + item.getCarValue() + ")");

            tvDateone.setText("商业险 " + item.getBizBeginDate());
            tvDatetwo.setText("交强险 " + item.getForceBeginDate());
            dateShangYe = item.getBizBeginDate().substring(0, 8);
            dateJiaoQiang = item.getForceBeginDate().substring(0, 8);
            init();
            list.addAll(insuranceCompareBean.getData());
            adapter.notifyDataSetChanged();
            stateLayout.showContentView();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (App.buyStatu == 1) {
            finish();
        }
    }

    private void init() {
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setLoadingMoreEnabled(false);
        rv.setPullRefreshEnabled(false);

    }


    @OnClick({R.id.rl_selete, R.id.return_linearlayout, R.id.rl_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_selete:
            case R.id.return_linearlayout:
                thisActivity.finish();
                break;

            case R.id.rl_date:
                Intent intent = new Intent(thisActivity, Change_time_activity.class);
                intent.putExtra("dateShangYe", dateShangYe);
                intent.putExtra("dateJiaoQiang", dateJiaoQiang);

                startActivityForResult(intent, 10086);

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || null == data) {
            return;
        }
        if (requestCode == 10086) {
            String shangyeDate = data.getStringExtra("dateShangYe");
            String jiaoqingDate = data.getStringExtra("dateJiaoQiang");

            ToastUtil.CustomToast(mContext, "请求修改时间的接口 " + shangyeDate + "  " + jiaoqingDate);

        }

    }

    protected void showDatePickDlg(final TextView tv) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(thisActivity, (view, year, monthOfYear, dayOfMonth) -> {
            monthOfYear++;
            String month = monthOfYear > 9 ? monthOfYear + "" : "0" + monthOfYear;
            String day = dayOfMonth > 9 ? dayOfMonth + "" : "0" + dayOfMonth;
            tv.setText(year + "" + month + "" + day + "000000");
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_insurance_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            try {
                holder.tv_money.setText("￥" + list.get(position).getTotalValue() + "");
                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + list.get(position).getImgUrl(), holder.iv_icon);
                holder.btn.setOnClickListener(v -> {
                    Intent i = new Intent(thisActivity, InsuranceInforActivity.class);
                    i.putExtra("policyNo", list.get(position).getPolicyNo());
                    startActivity(i);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_icon;
            private TextView tv_money;
            private Button btn;

            public ViewHolder(View itemView) {
                super(itemView);
                iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tv_money = (TextView) itemView.findViewById(R.id.tv_money);
                btn = (Button) itemView.findViewById(R.id.btn);
            }


        }
    }

}
