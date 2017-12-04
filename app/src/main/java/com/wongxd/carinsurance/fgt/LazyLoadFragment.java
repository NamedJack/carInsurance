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

public abstract class LazyLoadFragment extends Fragment {

    private View thisFragmentView;//当前fgt实例
    private boolean isPrepare = false;
    private boolean isFirst = true;
    private boolean isVisible = false;


//    /**
//     * 获取包含参数fgt实例
//     *
//     * @param args 要放入的参数
//     * @return 包含参数的fgt实例
//     */
//    public static LazyLoadFragment getInstance(@Nullable Bundle args) {
//        LazyLoadFragment lazyFragment = new ;
//        if (null != args) {
//            lazyFragment.setArguments(args);
//        }
//
//        return lazyFragment;
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisFragmentView = inflater.inflate(getChildLayoutRes(), null);
        //因为是公用一个fragment 先清除view
        ViewGroup viewGroup = (ViewGroup) thisFragmentView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(thisFragmentView);
        }
        initData(thisFragmentView, savedInstanceState);
        initView(thisFragmentView, savedInstanceState);
        return thisFragmentView;
    }

    /**
     * 初始化data
     */
    protected abstract void initData(View view, Bundle savedInstanceState);


    /**
     * 初始化view  如果使用ButterKnife  此方法不调用为好
     */
    protected abstract void initView(View view, Bundle savedInstanceState);


    /**
     * 获取真正显示的布局文件
     *
     * @return 布局res
     */
    public abstract int getChildLayoutRes();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) isVisible = true;
        if (isVisible && isFirst && isPrepare) {
            isFirst = false;
            lazyLoad();
        }
    }


    /**
     * 只有在可见时才加载
     */
    protected abstract void lazyLoad();

}
