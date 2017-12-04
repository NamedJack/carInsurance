package com.wongxd.carinsurance.fgt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.fgt.PhotoUpload.PhotoItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class PhotoUploadFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<FragmentWithTitle> fgtList;

    public PhotoUploadFragment() {
    }

    @Override
    public BaseFragment newFragment() {
        return new PhotoUploadFragment();
    }


    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_photo_upload;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void initData(View view, Bundle saveInstance) {
        fgtList = new ArrayList<>();
        Bundle b = new Bundle();
        b.putString("type", "waitUpload");
        fgtList.add(new FragmentWithTitle("待上传", PhotoItemFragment.getInstance(b)));
        Bundle b1 = new Bundle();
        b1.putString("type", "audit");
        fgtList.add(new FragmentWithTitle("审核中", PhotoItemFragment.getInstance(b1)));
        Bundle b2 = new Bundle();
        b2.putString("type", "notPass");
        fgtList.add(new FragmentWithTitle("未通过", PhotoItemFragment.getInstance(b2)));
        Bundle b3 = new Bundle();
        b3.putString("type", "uploadYet");
        fgtList.add(new FragmentWithTitle("已上传", PhotoItemFragment.getInstance(b3)));
    }

    @Override
    public void initView(View view, Bundle saveInstance) {
        ButterKnife.bind(this, view);

        viewpager.setAdapter(new FragmentViewpagerWithTitleAdapter(getChildFragmentManager(), fgtList));
        viewpager.setOffscreenPageLimit(4);
        tablayout.setupWithViewPager(viewpager);
    }


    public class FragmentWithTitle {
        private String title;
        private Fragment fragment;

        public FragmentWithTitle(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }

    //viewpager的适配器
    public class FragmentViewpagerWithTitleAdapter extends FragmentPagerAdapter {
        private List<FragmentWithTitle> list;

        public FragmentViewpagerWithTitleAdapter(FragmentManager fm, List<FragmentWithTitle> list) {
            super(fm);
            this.list = list;
        }


        public Fragment getItem(int position) {
            return list.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return null == list ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

}
