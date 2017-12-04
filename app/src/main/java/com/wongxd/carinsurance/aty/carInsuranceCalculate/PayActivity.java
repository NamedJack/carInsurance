package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 保单支付页面
 */
public class PayActivity extends BaseSwipeActivity {

    @BindView(R.id.car_cade_num)
    EditText car_cade_num;//保单支付收件人

    @BindView(R.id.address_Relat)
    RelativeLayout address_Relat;//点击 > 地址选择三级联动

    @BindView(R.id.shoujian_text)
    TextView shoujian_text;// show  收件人地址

    @BindView(R.id.xianxi_text)
    EditText xianxi_text;//收件人详细地址

    @BindView(R.id.car_iphone)
    EditText car_iphone;//收件人电话号码

    @BindView(R.id.pay_btn)//支付按钮
            Button pay_btn;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;

    private OptionsPickerView<String> mOpv;
    private AppCompatActivity thisActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
    }


    @OnClick({R.id.address_Relat, R.id.pay_btn, R.id.rl_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_Relat://地址选择的三级联动
                //  mOpv = new OptionsPickerView<String>(PayActivity.this);

                break;
            case R.id.pay_btn://确认支付按钮

                break;

            case R.id.rl_return:
                thisActivity.finish();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.rl_return)
    public void onClick() {
    }
}
