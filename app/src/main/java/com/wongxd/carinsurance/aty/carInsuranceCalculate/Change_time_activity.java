package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.wongxd.carinsurance.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Change_time_activity extends Activity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_shangye_date)
    TextView tvShangyeDate;
    @BindView(R.id.tv_jiaoqiang_date)
    TextView tvJiaoqiangDate;
    @BindView(R.id.btn)
    Button btn;
    private Activity thisActivity;
    private Context mContext;
    private String dateShangYe = " ";
    private String dateJiaoQiang = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_time_activity);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();

        dateShangYe =getIntent().getStringExtra("dateShangYe");
        dateJiaoQiang = getIntent().getStringExtra("dateJiaoQiang");

        tvShangyeDate.setText(dateShangYe.substring(0,4)+"年"+dateShangYe.substring(4,6)+"月"+dateShangYe.substring(6)+"日");
        tvJiaoqiangDate.setText(dateJiaoQiang.substring(0,4)+"年"+dateJiaoQiang.substring(4,6)+"月"+dateJiaoQiang.substring(6)+"日");
    }

    @OnClick({R.id.tv_title, R.id.tv_shangye_date, R.id.tv_jiaoqiang_date, R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                thisActivity.finish();
                break;
            case R.id.tv_shangye_date:
                showDatePickDlg(tvShangyeDate, true);
                break;
            case R.id.tv_jiaoqiang_date:
                showDatePickDlg(tvJiaoqiangDate, false);
                break;
            case R.id.btn:
                Intent intent = new Intent();
                intent.putExtra("dateShangYe", dateShangYe);
                intent.putExtra("dateJiaoQiang", dateJiaoQiang);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    protected void showDatePickDlg(final TextView tv, final boolean isShangYe) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(thisActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String month = monthOfYear > 9 ? monthOfYear + "" : "0" + monthOfYear;
                String day = dayOfMonth > 9 ? dayOfMonth + "" : "0" + dayOfMonth;
                tv.setText(year + "年" + month + "月" + day + "日");
                if (isShangYe) {
                    dateShangYe = year + month + day;
                } else {
                    dateJiaoQiang = year + month + day;
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
}
