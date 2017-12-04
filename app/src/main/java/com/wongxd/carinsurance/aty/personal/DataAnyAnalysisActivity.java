package com.wongxd.carinsurance.aty.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataAnyAnalysisActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.rl_insurance_policy)
    RelativeLayout rlInsurancePolicy;
    @BindView(R.id.rl_policy_analysis)
    RelativeLayout rlPolicyAnalysis;
    @BindView(R.id.rl_performance_statistics)
    RelativeLayout rlPerformanceStatistics;
    private AppCompatActivity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_any_analysis);
        ButterKnife.bind(this);
        thisActivity = this;
    }


    @OnClick({R.id.iv_return, R.id.rl_insurance_policy, R.id.rl_policy_analysis, R.id.rl_performance_statistics})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.rl_insurance_policy://保单
                startActivity(new Intent(thisActivity, InsurancePolicyActivity.class));
                break;
            case R.id.rl_policy_analysis://出单分析
                startActivity(new Intent(thisActivity, PolicyAnalysisActivity.class));
                break;
            case R.id.rl_performance_statistics://业绩
                startActivity(new Intent(thisActivity, PerformanceStatisticsActivity.class));
                break;
        }
    }
}
