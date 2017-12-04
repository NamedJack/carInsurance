package com.wongxd.carinsurance.fgt.orderManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.StateLayout;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.carInsuranceCalculate.InsuranceInforActivity;
import com.wongxd.carinsurance.bean.orderManager.OrderListBean;
import com.wongxd.carinsurance.fgt.LazyLoadFragment;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideLoader;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class OrderItemFragment extends LazyLoadFragment {

    private Context mContext;
    private int type = 1;  //1 待支付
    private StateLayout stateLayout;
    private int totalPage = 0; //总页数
    private int pageNo = 1; //分页数
    private static boolean isDatePicker = false;

    public OrderItemFragment() {
    }

    /**
     * 获取包含参数fgt实例
     *
     * @param args 要放入的参数
     * @return 包含参数的fgt实例
     */
    public static OrderItemFragment getInstance(@Nullable Bundle args) {
        OrderItemFragment fragment = new OrderItemFragment();
        if (null != args) {
            fragment.setArguments(args);
        }

        return fragment;
    }

    @BindView(R.id.rv)
    XRecyclerView rv;

    public String Action = "Wongxd/CarInsurance/OrderManager";
    OrderBroadcastReceiver receiver = new OrderBroadcastReceiver();
    IntentFilter filter = new IntentFilter(Action);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext().getApplicationContext();
        String typeString = getArguments().getString("type");

        //   支付状态1.待支付 2.支付完成
        if (typeString.equals("complite")) {
            type = 2;
        }


        mContext.registerReceiver(receiver, filter);
    }


    List<OrderListBean.DataBean> list = new ArrayList<OrderListBean.DataBean>();
    RvOrderAdapter adapter = new RvOrderAdapter();

    /**
     * 获取orderlist
     *
     * @param isLoadMore
     */
    public void getList(final boolean isLoadMore, final boolean isDatePicker) {
        if (!isLoadMore) {  //仅刷新才显示，避免不友好
            stateLayout.showProgressView();
            rv.setNoMore(false);
        }
        String url = UrlConfig.QueryPolicyInfoByPayFlag_URL;
        if (isDatePicker) url = UrlConfig.QueryPolicyInfoByPayFlagTpye_URL;
        PostFormBuilder builder = OkHttpUtils.post().url(url)
                .addParams("token", App.token)
                .addParams("payFlag", type + "");

        if (isDatePicker) {
            builder.addParams("dateTimeTypeId", datePickerId);
        }

        if (isLoadMore) {
            if (pageNo > totalPage && totalPage > 0) { //没有更多
                stateLayout.showContentView();
                rv.setNoMore(true);
                return;
            } else rv.setNoMore(false);

            builder.addParams("pageNo", pageNo + "");
        }

        Logger.e("token--" + App.token + "--payFlag--" + type +
                "-isdatapiker-" + isDatePicker
                + "-pageNo-" + pageNo + "-dateTimeTypeId-" + datePickerId);
        WNetUtil.StringCallBack(builder, url + type, (AppCompatActivity) getActivity(), "获取订单中",
                new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);
                        Gson gson = new Gson();
                        OrderListBean listBean = gson.fromJson(response, OrderListBean.class);
                        stateLayout.showContentView();
                        if (null != listBean.getData() && listBean.getCode() == 100) {
                            totalPage = listBean.getTotlePageNo(); //总的页数
                            if (listBean.getData().size() != 0) {
                                if (!isLoadMore) {
                                    list.clear();
                                    pageNo = 1;
                                }
                                list.addAll(listBean.getData());
                                adapter.notifyDataSetChanged();
                                pageNo++; //获取成功，pageNo + 1
                            } else if (!isLoadMore) {
                                stateLayout.showEmptyView("这里空空如也");
                                stateLayout.setEmptyAction(v -> getList(isLoadMore, isDatePicker));
                            }

                        } else if (!isLoadMore) {
                            stateLayout.showErrorView("返回信息有问题 " + listBean.getMsg());
                            stateLayout.setErrorAction(v -> getList(isLoadMore, isDatePicker));

                        } else ToastUtil.CustomToast(mContext, "返回信息有问题 " + listBean.getMsg());


                        rv.loadMoreComplete();
                        rv.refreshComplete();
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        Logger.e(e.getMessage());
                        stateLayout.showErrorView("获取失败 ");
                        stateLayout.setErrorAction(v -> getList(isLoadMore, isDatePicker));
                        rv.loadMoreComplete();
                        rv.refreshComplete();
                    }
                });


    }


    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_order_item;
    }

    @Override
    protected void lazyLoad() {
        getList(false, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.orderStatusChanged) { //当跳转到支付页面时，订单可能从待支付 变为 已完成 ，需要刷新
            App.orderStatusChanged = false;
            getList(false, isDatePicker);
        }
    }

    @Override
    public void initData(View view, Bundle saveInstance) {

    }

    @Override
    public void initView(View view, Bundle saveInstance) {
        ButterKnife.bind(this, view);
        stateLayout = (StateLayout) view.findViewById(R.id.stateLayout);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setLoadingMoreProgressStyle(App.RECYCLEVIEW_LOADMORE_STYLE);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getList(false, isDatePicker);
            }

            @Override
            public void onLoadMore() {
                getList(true, isDatePicker);
            }
        });


        //懒加载，第一个fgt 无法实现
        if (type == 1) getList(false, false);
    }


    class RvOrderAdapter extends RecyclerView.Adapter<RvOrderAdapter.OrderViewHolder> {

        @Override
        public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_order_item, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(OrderViewHolder holder, final int position) {


            ViewHelper.setScaleX(holder.itemView, 0.8f);
            ViewHelper.setScaleY(holder.itemView, 0.8f);
            ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();


            final OrderListBean.DataBean item = list.get(position);
            holder.tvMoneny.setText(item.getTolPolicyMoney() + "");
            holder.tvPeopleName.setText(item.getPolicyPeople());
            holder.tvCarNum.setText(item.getPolicyCarNo());
            GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + item.getIcon(), holder.iv);

            holder.btn_delete.setOnClickListener(v -> {


                final NormalDialog dialog = new NormalDialog(getActivity());

                dialog.isTitleShow(false)//
                        .bgColor(Color.parseColor("#383838"))//
                        .cornerRadius(5)//
                        .content("确定删除吗?")//
                        .contentGravity(Gravity.CENTER)//
                        .contentTextColor(Color.parseColor("#ffffff"))//
                        .dividerColor(Color.parseColor("#222222"))//
                        .btnTextSize(15.5f, 15.5f)//
                        .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                        .btnPressColor(Color.parseColor("#2B2B2B"))//
                        .widthScale(0.85f)//
                        .showAnim(new BounceTopEnter())//
                        .show();

                dialog.setOnBtnClickL(
                        () -> dialog.dismiss(),
                        () -> {
                            dialog.superDismiss();

                            WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.DelPolicyByPolicyNo_URL)
                                            .addParams("token", App.token)
                                            .addParams("policyNo", item.getId() + ""), UrlConfig.DelPolicyByPolicyNo_URL, (AppCompatActivity) getActivity(), "删除保单中",
                                    new WNetUtil.WNetStringCallback() {
                                        @Override
                                        public void success(String response, int id) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.optInt("code") == 100) {
                                                    list.remove(position);
                                                    notifyDataSetChanged();
                                                    ToastUtil.CustomToast(mContext, "删除成功");
                                                } else ToastUtil.CustomToast(mContext, "删除失败");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void error(Call call, Exception e, int id) {
                                            ToastUtil.CustomToast(mContext, "删除失败  " + e.getMessage());
                                        }
                                    });
                        }
                );


            });


            holder.itemView.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), InsuranceInforActivity.class);
                i.putExtra("policyNo", item.getPolicyNo());
                startActivity(i);

            });


            holder.btn.setOnClickListener(v -> {
                Intent i = new Intent(getActivity(), InsuranceInforActivity.class);
                i.putExtra("policyNo", item.getPolicyNo());
                startActivity(i);
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class OrderViewHolder extends RecyclerView.ViewHolder {
            private TextView tvMoneny;
            private TextView tvCarNum;
            private TextView tvPeopleName;
            private Button btn;
            private Button btn_delete;
            private ImageView iv;

            public OrderViewHolder(View itemView) {
                super(itemView);
                tvMoneny = (TextView) itemView.findViewById(R.id.tv_money);
                tvPeopleName = (TextView) itemView.findViewById(R.id.tv_people_name);
                tvCarNum = (TextView) itemView.findViewById(R.id.tv_activity);
                btn = (Button) itemView.findViewById(R.id.btn);
                btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }


    public static String datePickerId = 1 + "";

    //广播 依据时间刷新
    public class OrderBroadcastReceiver extends BroadcastReceiver {
        public final String TAG = "OrderBroadcastReceiver";
        public String Action = "Wongxd/CarInsurance/OrderManager";


        @Override
        public void onReceive(Context context, Intent intent) {
            isDatePicker = true;
            datePickerId = intent.getStringExtra("id");
            int visblePositon = intent.getIntExtra("visblePositon", 0);

            if (type == visblePositon + 1) {
                getList(false, isDatePicker);
            }

            Logger.e("收到广播  " + datePickerId);
        }
    }

    @Override
    public void onDestroyView() {
        mContext.unregisterReceiver(receiver);
        super.onDestroyView();
    }
}
