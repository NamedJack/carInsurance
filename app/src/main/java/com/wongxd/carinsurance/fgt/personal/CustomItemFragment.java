package com.wongxd.carinsurance.fgt.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.FadeViewAnimProvider;
import com.lufficc.stateLayout.StateLayout;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.personal.CustomInfoActivity;
import com.wongxd.carinsurance.aty.personal.CustomManagerActivity;
import com.wongxd.carinsurance.bean.personal.CustomMangerBean;
import com.wongxd.carinsurance.fgt.BaseFragment;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.wongxd.carinsurance.App.token;

/**
 * 客户管理
 * Created by wxd1 on 2017/3/15.
 */

public class CustomItemFragment extends BaseFragment {

    private Context mContext;
    private List<CustomMangerBean.DataBean> list;
    private int type = 1;
    private CustomManagerActivity customManagerActivity;
    private RvCustomAdapter adapter;
    private StateLayout statefulLayout;

    public CustomItemFragment() {
    }

    /**
     * 获取包含参数fgt实例
     *
     * @param args 要放入的参数
     * @return 包含参数的fgt实例
     */
    public static CustomItemFragment getInstance(@Nullable Bundle args) {
        CustomItemFragment fragment = new CustomItemFragment();
        if (null != args) {
            fragment.setArguments(args);
        }

        return fragment;
    }

    @BindView(R.id.rv)
    XRecyclerView rv;

    @Override
    public BaseFragment newFragment() {
        return new CustomItemFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        customManagerActivity = (CustomManagerActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext().getApplicationContext();
        String typeString = getArguments().getString("type");
        if (typeString.equals("yet")) type = 2;

    }


    /**
     * 获取信息
     *
     * @param isLoadMore
     */
    private void getList(final boolean isLoadMore) {
        PostFormBuilder builder = OkHttpUtils.post().url(UrlConfig.CustomManager_URL)
                .addParams("token", token)
                .addParams("state", type + "");
        if (isLoadMore) {
            rv.setNoMore(false);
            // TODO: 2017/3/20 分页

            rv.loadMoreComplete();
            return;
        }
//        if (isLoadMore) statefulLayout.showProgressView("加载中");
//        else statefulLayout.showProgressView("刷新中");


        Logger.e("token--" + token + "--type--" + type);
        WNetUtil.StringCallBack(builder
                , UrlConfig.CustomManager_URL + type, customManagerActivity, "获取列表中", new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        CustomMangerBean customMangerBean = gson.fromJson(response, CustomMangerBean.class);
                        if (null != customMangerBean) {

                            if (customMangerBean.getCode() == 100) {
                                if (isLoadMore) {
                                    if (customMangerBean.getData().size() == 0) {
                                        rv.setNoMore(true);
                                    } else {
                                        statefulLayout.showContentView();
                                        rv.setNoMore(false);
                                        list.addAll(customMangerBean.getData());
                                        adapter.notifyDataSetChanged();
                                    }

                                } else {
                                    if (customMangerBean.getData().size() == 0) {
                                        rv.setNoMore(true);
                                        statefulLayout.showEmptyView("这里空空如也");
                                        statefulLayout.setEmptyAction(v -> getList(false));
                                    } else {
                                        statefulLayout.showContentView();
                                        rv.setNoMore(false);
                                        list.clear();
                                        list.addAll(customMangerBean.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                            } else {
                                statefulLayout.showErrorView("获取失败");
                                statefulLayout.setErrorAction(v -> {
                                    if (!isLoadMore) getList(false);
                                    else getList(true);
                                });
                            }
                        } else {
                            statefulLayout.showErrorView("获取失败");
                            statefulLayout.setErrorAction(v -> {
                                if (!isLoadMore) getList(false);
                                else getList(true);
                            });
                        }


                        rv.refreshComplete();
                        rv.loadMoreComplete();
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        rv.refreshComplete();
                        rv.loadMoreComplete();
                        ToastUtil.CustomToast(mContext, "获取失败");
                        statefulLayout.showErrorView("获取失败");
                        statefulLayout.setErrorAction(v -> {
                            if (!isLoadMore) getList(false);
                            else getList(true);
                        });
                    }
                }

        );
    }

    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_order_item;
    }


    @Override
    public void initData(View view, Bundle saveInstance) {

    }

    @Override
    public void initView(View view, Bundle saveInstance) {
        ButterKnife.bind(this, view);
        statefulLayout = (StateLayout) view.findViewById(R.id.stateLayout);
        statefulLayout.setViewSwitchAnimProvider(new FadeViewAnimProvider());

        list = new ArrayList<>();
        adapter = new RvCustomAdapter();
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
        getList(false);
    }


    class RvCustomAdapter extends RecyclerView.Adapter<RvCustomAdapter.CustomViewHolder> {


        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_custom_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final CustomViewHolder holder, final int position) {

            ViewHelper.setScaleX(holder.itemView, 0.8f);
            ViewHelper.setScaleY(holder.itemView, 0.8f);
            ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

            holder.tvCarNum.setText(list.get(position).getPolicyCarNo());
            holder.tvPeopleName.setText(list.get(position).getPolicyPeople());
            holder.tvRemindTimes.setText(list.get(position).getNumber() + "");
            holder.tvLeftTime.setText(list.get(position).getDueDate() + "");
            holder.itemView.setOnClickListener(v -> {
                Intent in = new Intent(getActivity(), CustomInfoActivity.class);
                in.putExtra("id", list.get(position).getPolicyNo() + "");
                startActivity(in);
            });
        }

        @Override
        public int getItemCount() {
            return null == list ? 0 : list.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView tvCarNum;
            private TextView tvPeopleName;
            private TextView tvRemindTimes;
            private TextView tvLeftTime;

            public CustomViewHolder(View itemView) {
                super(itemView);
                tvCarNum = (TextView) itemView.findViewById(R.id.tv_activity);
                tvPeopleName = (TextView) itemView.findViewById(R.id.tv_people_name);
                tvRemindTimes = (TextView) itemView.findViewById(R.id.tv_remind_times);
                tvLeftTime = (TextView) itemView.findViewById(R.id.tv_left_time);

            }
        }
    }


}
