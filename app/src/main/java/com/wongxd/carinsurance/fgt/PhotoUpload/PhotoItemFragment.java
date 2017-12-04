package com.wongxd.carinsurance.fgt.PhotoUpload;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.StateLayout;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.photoUpload.AuditPhotoActivity;
import com.wongxd.carinsurance.aty.photoUpload.SeletePhotoUploadActivity;
import com.wongxd.carinsurance.bean.photoUpload.PhotoListBean;
import com.wongxd.carinsurance.fgt.LazyLoadFragment;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class PhotoItemFragment extends LazyLoadFragment {
    @BindView(R.id.rv)
    XRecyclerView rv;
    private Context mContext;
    private int type = 0;
    private StateLayout stateLayout;
    private RvPhotoAdapter adapter;
    private List<PhotoListBean.DataBean> photoList;

    public PhotoItemFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String typeString = getArguments().getString("type");
        assert typeString != null;
        switch (typeString) {
            case "waitUpload":
                type = 0;
                break;
            case "audit":
                type = 1;
                break;
            case "notPass":
                type = 3;
                break;
            case "uploadYet":
                type = 2;
                break;
            default:
                type = 0;
                break;
        }
    }

    /**
     * 获取包含参数fgt实例
     *
     * @param args 要放入的参数
     * @return 包含参数的fgt实例
     */
    public static PhotoItemFragment getInstance(@Nullable Bundle args) {
        PhotoItemFragment photoItemFragment = new PhotoItemFragment();
        if (null != args) {
            photoItemFragment.setArguments(args);
        }

        return photoItemFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getContext().getApplicationContext();
    }

    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_photo_item;
    }

    @Override
    protected void lazyLoad() {
        getList(false);
    }

    private int pageNo = 1;    //当前页
    private int totalPage = 0; //总页数

    private void getList(final boolean isLoadMore) {

        PostFormBuilder builder = OkHttpUtils.post().url(UrlConfig.QueryPolicyByUpImgFlag_URL)
                .addParams("token", App.token)
                .addParams("upImgFlag", type + "");
        if (isLoadMore) {
            if (totalPage < pageNo && totalPage > 0) {
                rv.setNoMore(true);
                return;
            } else rv.setNoMore(false);
            builder.addParams("pageNo", pageNo + "");
        } else {
            rv.setNoMore(false);
            stateLayout.showProgressView();
        }
        WNetUtil.StringCallBack(builder, UrlConfig.QueryPolicyByUpImgFlag_URL + type, (AppCompatActivity) getActivity(), "获取图片列表中",
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        PhotoListBean photoListBean = gson.fromJson(response, PhotoListBean.class);
                        if (null != photoListBean && photoListBean.getCode() == 100) {

                            if (null != photoListBean.getData() && photoListBean.getData().size() != 0) {
                                rv.setNoMore(false);
                                if (!isLoadMore) {
                                    pageNo = 1;
                                    photoList.clear();
                                }
                                photoList.addAll(photoListBean.getData());
                                adapter.notifyDataSetChanged();
                                pageNo++;//取到数据后  页面 +1
                                totalPage = photoListBean.getTotlePageNo();
                                stateLayout.showContentView();
                            } else {
                                rv.setNoMore(true);
                                stateLayout.showEmptyView("这里空空如也");
                                stateLayout.setEmptyAction(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getList(isLoadMore);
                                    }
                                });
                            }
                        } else {
                            stateLayout.showErrorView("请求出错");
                            stateLayout.setErrorAction(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getList(isLoadMore);
                                }
                            });

                        }
                        rv.loadMoreComplete();
                        rv.refreshComplete();

                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        stateLayout.showErrorView("请求出错: " + e.getMessage());
                        stateLayout.setErrorAction(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getList(isLoadMore);
                            }
                        });
                        rv.loadMoreComplete();
                        rv.refreshComplete();
                    }

                });
    }


    @Override
    protected void initData(View view, Bundle savedInstanceState) {

    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(PhotoItemFragment.this, view);
        stateLayout = (StateLayout) view.findViewById(R.id.stateLayout);

        photoList = new ArrayList<>();
        adapter = new RvPhotoAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setLoadingMoreProgressStyle(App.RECYCLEVIEW_LOADMORE_STYLE);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getList(false);
            }

            @Override
            public void onLoadMore() {
                getList(true);
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (type == 0) getList(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.Should_PhotoUpload_Refresh) {
            getList(false);
        }
    }

    class RvPhotoAdapter extends RecyclerView.Adapter<RvPhotoAdapter.PhotoViewHolder> {

        @Override
        public RvPhotoAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_photo_item, parent, false);
            return new RvPhotoAdapter.PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RvPhotoAdapter.PhotoViewHolder holder, final int position) {


            ViewHelper.setScaleX(holder.itemView, 0.8f);
            ViewHelper.setScaleY(holder.itemView, 0.8f);
            ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            if (type == 1 || type == 2) {
                holder.tv_btn.setText("查看");
                holder.tv_btn.setTextColor(Color.GRAY);
            } else if (type == 3) {
                holder.tv_btn.setText("重新上传");
                holder.tv_btn.setTextColor(getResources().getColor(R.color.textSelected));
            }

            holder.tv_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case 0:
                            Intent i0 = new Intent(getActivity(), SeletePhotoUploadActivity.class);
                            i0.putExtra("policyId", photoList.get(position).getId() + "");
                            startActivity(i0);
                            break;
                        case 1://审核中
                            Intent i = new Intent(getActivity(), AuditPhotoActivity.class);
                            i.putExtra("policyId", photoList.get(position).getId() + "");
                            startActivity(i);
                            break;
                        case 3://未通过
                            Intent i3 = new Intent(getActivity(), SeletePhotoUploadActivity.class);
                            i3.putExtra("isUploadAgin", true);
                            i3.putExtra("policyId", photoList.get(position).getId() + "");
                            startActivity(i3);
                            break;
                        case 2:
                            Intent i4 = new Intent(getActivity(), AuditPhotoActivity.class);
                            i4.putExtra("policyId", photoList.get(position).getId() + "");
                            i4.putExtra("isUploadYet", true);
                            startActivity(i4);
                            break;
                    }
                }
            });

            holder.tv_car_num.setText(photoList.get(position).getPolicyCarNo());
            holder.tv_people_name.setText(photoList.get(position).getPolicyPeople());
        }

        @Override
        public int getItemCount() {
            return photoList.size();
        }

        class PhotoViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_car_num;
            private TextView tv_people_name;
            private TextView tv_btn;

            public PhotoViewHolder(View itemView) {
                super(itemView);
                tv_car_num = (TextView) itemView.findViewById(R.id.tv_activity);
                tv_people_name = (TextView) itemView.findViewById(R.id.tv_people_name);
                tv_btn = (TextView) itemView.findViewById(R.id.tv_btn);
            }
        }
    }
}
