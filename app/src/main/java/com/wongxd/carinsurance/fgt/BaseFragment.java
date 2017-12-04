package com.wongxd.carinsurance.fgt;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wxd1 on 2017/3/15.
 */

public abstract class BaseFragment extends Fragment {

    public View thisFragmentView;//当前fgt实例


//    /**
//     * 获取包含参数fgt实例
//     *
//     * @param args 要放入的参数
//     * @return 包含参数的fgt实例
//     */
//    public static BaseFragment getInstance(@Nullable Bundle args) {
//        BaseFragment baseFragment = new ;
//        if (null != args) {
//            baseFragment.setArguments(args);
//        }
//
//        return baseFragment;
//    }

    /**
     * new出一个Fragment实例
     *
     * @return Fragment实例
     */
    public abstract BaseFragment newFragment();

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisFragmentView = inflater.inflate(getChildLayoutRes(), container,false);
        //因为是公用一个fragment 先清除view
        ViewGroup viewGroup = (ViewGroup) thisFragmentView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(thisFragmentView);
        }
        initData(thisFragmentView,savedInstanceState);
        initView(thisFragmentView,savedInstanceState);
        return thisFragmentView;
    }

    /**
     * 初始化data
     */
    public abstract void initData(View view,Bundle savedInstanceState);


    /**
     * 初始化view 如果使用ButterKnife  此方法不调用为好
     */
    public abstract void initView(View view,Bundle savedInstanceState);


    /**
     * 获取真正显示的布局文件
     *
     * @return 布局res
     */
    public abstract int getChildLayoutRes();

}
