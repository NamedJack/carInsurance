package com.wongxd.carinsurance.fgt.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.StateLayout;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.adpter.personal.DataAnalysis.RvDataAnalysisAdapter;
import com.wongxd.carinsurance.bean.personal.DataAnalysisBean;
import com.wongxd.carinsurance.fgt.LazyLoadFragment;
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

/**
 * Created by wxd1 on 2017/3/17.
 * <p>
 * <p>
 * 保单数据
 */
public class InsurancePolicyFragment extends LazyLoadFragment {
    @BindView(R.id.rv)
    XRecyclerView rv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.tv_item4)
    TextView tvItem4;
    @BindView(R.id.ll_item4)
    LinearLayout llItem4;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;
    private String type;
    private List<DataAnalysisBean.DataBean> list;
    private boolean isFirst = false;

    @Override
    protected void initData(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_data_anyanalysis_item;
    }

    @Override
    protected void lazyLoad() {
        list = new ArrayList<>();
        rv.setAdapter(new RvDataAnalysisAdapter(getActivity(), list, RvDataAnalysisAdapter.ITEM_TYPE.ITEM_FIVE));
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setLoadingMoreProgressStyle(App.RECYCLEVIEW_LOADMORE_STYLE);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(false);
            }

            @Override
            public void onLoadMore() {
                getData(true);
            }
        });

        getData(false);

    }


    private void getData(final boolean isLoadMore) {
        String url = UrlConfig.GetData_URL;
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("timeId", type)
                .addParams("token", App.token);
        if (isLoadMore) {
            rv.setNoMore(false);
            // TODO: 2017/3/29 加载更多
            rv.loadMoreComplete();
            return;
        }

        Logger.e("保单数据--timeId--" + type + "--token--" + App.token);

        WNetUtil.StringCallBack(builder, url + type, (AppCompatActivity) getActivity(), "", false, () -> {
            stateLayout.showProgressView();
            stateLayout.setErrorAndEmptyAction(v -> getData(isLoadMore));
        }, new WNetUtil.WNetStringCallback() {
            @Override
            public void success(String response, int id) {

                Logger.e(response);
                Gson gson = new Gson();
                DataAnalysisBean dataAnalysisBean = gson.fromJson(response, DataAnalysisBean.class);

                if (null == dataAnalysisBean) {
                    stateLayout.showErrorView("返回数据为空");
                    return;
                }

                if (dataAnalysisBean.getCode() == 100) {
                    if (dataAnalysisBean.getData().size() == 0) {
                        if (isLoadMore) {
                            stateLayout.showContentView();
                            ToastUtil.CustomToast(getContext(), "没有更多了");
                            rv.setNoMore(true);
                        } else {
                            stateLayout.showEmptyView(getString(R.string.state_empty));
                        }
                    } else {

                        if (!isLoadMore) {
                            list.clear();
                        }
                        list.addAll(dataAnalysisBean.getData());
                        rv.getAdapter().notifyDataSetChanged();
                        stateLayout.showContentView();
                    }

                } else stateLayout.showErrorView(dataAnalysisBean.getMsg());

                rv.refreshComplete();
                rv.loadMoreComplete();
            }

            @Override
            public void error(Call call, Exception e, int id) {
                stateLayout.showErrorView(e.getMessage());
                rv.refreshComplete();
                rv.loadMoreComplete();
            }
        });


    }


    /**
     * 获取包含参数fgt实例
     *
     * @param args 要放入的参数
     * @return 包含参数的fgt实例
     */
    public static InsurancePolicyFragment getInstance(@Nullable Bundle args) {
        InsurancePolicyFragment fragment = new InsurancePolicyFragment();
        if (null != args) {
            fragment.setArguments(args);
        }
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
        isFirst = getArguments().getBoolean("first", false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isFirst) lazyLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        llItem4.setVisibility(View.VISIBLE);
        tvName.setText("保险公司");
        tvItem1.setText("交强险(单)");
        tvItem2.setText("交强险(元)");
        tvItem3.setText("商业险(单)");
        tvItem4.setText("商业险(元)");


        return rootView;
    }
}
