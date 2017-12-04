package com.wongxd.carinsurance.aty.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.adpter.FragmentViewpagerAdapter;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.fgt.personal.CustomItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomManagerActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_yet)
    TextView tvYet;
    @BindView(R.id.tv_not)
    TextView tvNot;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private AppCompatActivity thisActivity;
    private List<Fragment> fgtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_manager);
        ButterKnife.bind(this);
        thisActivity = this;

        fgtList = new ArrayList<>();
        Bundle b = new Bundle();
        b.putString("type", "yet");
        fgtList.add(CustomItemFragment.getInstance(b));

        Bundle bb = new Bundle();
        bb.putString("type", "not");
        fgtList.add(CustomItemFragment.getInstance(bb));


        FragmentManager manager = getSupportFragmentManager();
        viewpager.setAdapter(new FragmentViewpagerAdapter(manager, fgtList));
        viewpager.setOffscreenPageLimit(2);
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) changeCheckItem(false);
                else changeCheckItem(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_return, R.id.tv_yet, R.id.tv_not})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.tv_yet:
                changeCheckItem(false);
                viewpager.setCurrentItem(0);
                break;
            case R.id.tv_not:
                changeCheckItem(true);
                viewpager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 切换顶部选择栏目
     *
     * @param isTwo 是否选中了第二项
     */
    private void changeCheckItem(boolean isTwo) {
        if (isTwo) {
            tvNot.setTextColor(Color.WHITE);
            tvNot.setBackground(getResources().getDrawable(R.drawable.right_round_stoke_with_solid));

            tvYet.setTextColor(getResources().getColor(R.color.textSelected));
            tvYet.setBackground(getResources().getDrawable(R.drawable.left_round_stoke));
        } else {
            tvYet.setTextColor(Color.WHITE);
            tvYet.setBackground(getResources().getDrawable(R.drawable.left_round_stoke_with_solid));

            tvNot.setTextColor(getResources().getColor(R.color.textSelected));
            tvNot.setBackground(getResources().getDrawable(R.drawable.right_round_stoke));
        }
    }
}
