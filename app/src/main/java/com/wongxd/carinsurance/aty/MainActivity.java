package com.wongxd.carinsurance.aty;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.LoginActivity;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.adpter.FragmentViewpagerAdapter;
import com.wongxd.carinsurance.fgt.CarInsuranceCalculateFragment;
import com.wongxd.carinsurance.fgt.OrderManagerFragment;
import com.wongxd.carinsurance.fgt.PersonalFragment;
import com.wongxd.carinsurance.fgt.PhotoUploadFragment;
import com.wongxd.carinsurance.swipbackhelper.SwipeBackHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ib_bot_sheet_1)
    ImageButton ibBotSheet1;
    @BindView(R.id.tv_bot_sheet_1)
    TextView tvBotSheet1;
    @BindView(R.id.ll_bot_sheet_1)
    LinearLayout llBotSheet1;
    @BindView(R.id.ib_bot_sheet_2)
    ImageButton ibBotSheet2;
    @BindView(R.id.tv_bot_sheet_2)
    TextView tvBotSheet2;
    @BindView(R.id.ll_bot_sheet_2)
    LinearLayout llBotSheet2;
    @BindView(R.id.ib_bot_sheet_3)
    ImageButton ibBotSheet3;
    @BindView(R.id.tv_bot_sheet_3)
    TextView tvBotSheet3;
    @BindView(R.id.ll_bot_sheet_3)
    LinearLayout llBotSheet3;
    @BindView(R.id.ib_bot_sheet_4)
    ImageButton ibBotSheet4;
    @BindView(R.id.tv_bot_sheet_4)
    TextView tvBotSheet4;
    @BindView(R.id.ll_bot_sheet_4)
    LinearLayout llBotSheet4;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private AppCompatActivity thisActivity; //当前activity实例
    private ImageButton[] ibs;//底部导航 imagebutton数组
    private TextView[] tvs;//底部导航 文字数组
    private int selectedSheet = 0;//viewpager选中的条目索引
    private int lastSelectedPosition = -1;//上一次选中条目


    public int getSelectedSheet() {
        return selectedSheet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        thisActivity = this;
        initBottomSheet();
        initViewPager();
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false).setSwipeRelateEnable(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.IS_NEED_REBOOT) {
            App.IS_NEED_REBOOT = false;
            startActivity(new Intent(thisActivity, LoginActivity.class));
            finish();
        }
    }

    private void initViewPager() {
        List<Fragment> fgList = new ArrayList<>();
        fgList.add(new CarInsuranceCalculateFragment());
        fgList.add(new OrderManagerFragment());
        fgList.add(new PhotoUploadFragment());
        fgList.add(new PersonalFragment());


        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(new FragmentViewpagerAdapter(getSupportFragmentManager(), fgList));

        viewpager.setCurrentItem(0, true);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedSheet = position;
                SelectSheet(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomSheet() {
        ibs = new ImageButton[]{ibBotSheet1, ibBotSheet2, ibBotSheet3, ibBotSheet4};
        tvs = new TextView[]{tvBotSheet1, tvBotSheet2, tvBotSheet3, tvBotSheet4};
        SelectSheet(0);
    }


    /**
     * 底部按钮选择逻辑
     *
     * @param num 选中项目的索引
     */
    private void SelectSheet(int num) {
        if (lastSelectedPosition == selectedSheet) return;//避免点击同一条目执行逻辑，消耗内存
        for (int i = 0; i < 4; i++) {
            if (i == num) {
                ibs[i].setActivated(true);
                tvs[i].setTextColor(getResources().getColor(R.color.textSelected));
                viewAnimitor(i);
                viewpager.setCurrentItem(i);
                lastSelectedPosition = num;
            } else {
                ibs[i].setActivated(false);
                tvs[i].setTextColor(getResources().getColor(R.color.textUnSelected));
            }
        }
    }

    /**
     * 底部按钮选中动画
     *
     * @param i 按钮位于组中的索引
     */
    private void viewAnimitor(int i) {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.7f, 1.1f,
                1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.7f, 1.1f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.7f, 1.1f,
                1f);
        ObjectAnimator.ofPropertyValuesHolder(tvs[i], pvhAlpha, pvhY, pvhZ).setDuration(200).start();
        ObjectAnimator.ofPropertyValuesHolder(ibs[i], pvhAlpha, pvhY, pvhZ).setDuration(200).start();
    }

    @OnClick({R.id.ll_bot_sheet_1, R.id.ll_bot_sheet_2, R.id.ll_bot_sheet_3, R.id.ll_bot_sheet_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bot_sheet_1:
                selectedSheet = 0;
                break;
            case R.id.ll_bot_sheet_2:
                selectedSheet = 1;
                break;
            case R.id.ll_bot_sheet_3:
                selectedSheet = 2;
                break;
            case R.id.ll_bot_sheet_4:
                selectedSheet = 3;
                break;
        }
        SelectSheet(selectedSheet);
    }


    @Override
    public void onBackPressed() {

//        final NormalDialog dialog = new NormalDialog(thisActivity);
//        dialog.isTitleShow(false)//
//                .bgColor(Color.parseColor("#383838"))//
//                .cornerRadius(5)//
//                .content("确定退出程序吗?")//
//                .contentGravity(Gravity.CENTER)//
//                .contentTextColor(Color.parseColor("#ffffff"))//
//                .dividerColor(Color.parseColor("#222222"))//
//                .btnTextSize(15.5f, 15.5f)//
//                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
//                .btnPressColor(Color.parseColor("#2B2B2B"))//
//                .widthScale(0.85f)//
//                .showAnim(new BounceTopEnter())//
//                .show();
//
//        dialog.setOnBtnClickL(
//                () -> dialog.dismiss(),
//                () -> {
//                    finish();
//                    dialog.dismiss();
//
//                });


        //应用保活
        moveTaskToBack(false);

    }
}
