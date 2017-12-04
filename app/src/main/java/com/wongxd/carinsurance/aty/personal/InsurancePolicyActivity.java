package com.wongxd.carinsurance.aty.personal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.adpter.FragmentViewpagerAdapter;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.base.PopwindowMenu;
import com.wongxd.carinsurance.fgt.personal.InsurancePolicyFragment;
import com.wongxd.carinsurance.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsurancePolicyActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private AppCompatActivity thisActivity;
    private List<Map<String, String>> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_policy);
        ButterKnife.bind(this);
        thisActivity = this;

        List<Fragment> fgtList = new ArrayList<>();

        dateList = App.dateList;
        if (dateList == null) {
            ToastUtil.CustomToast(thisActivity.getApplicationContext(), "没有时间列表");
            return;
        }
        init(fgtList);

    }

    private void init(List<Fragment> fgtList) {

        tvDate.setText(dateList.get(0).get("name"));
        for (int i = 0; i < dateList.size(); i++) {
            Bundle b = new Bundle();
            b.putString("type", dateList.get(i).get("id"));
            if (i==0) b.putBoolean("first",true);
            fgtList.add(InsurancePolicyFragment.getInstance(b));
        }

        viewpager.setAdapter(new FragmentViewpagerAdapter(getSupportFragmentManager(), fgtList));
        viewpager.setOffscreenPageLimit(dateList.size());
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvDate.setText(dateList.get(position).get("name"));
                PopwindowMenu.TextviewAnimitor(tvDate);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_return, R.id.tv_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.tv_date:
                if (null == dateList) {
                    ToastUtil.CustomToast(getApplication().getApplicationContext(), "没有获取到时间列表");
                    return;
                }
                PopwindowMenu.datePopWindow(thisActivity, tvDate, dateList, (str, position, id) -> viewpager.setCurrentItem(position)
                );
                break;
        }
    }
}
