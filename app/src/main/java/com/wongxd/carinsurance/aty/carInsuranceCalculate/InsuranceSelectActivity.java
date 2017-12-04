package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lufficc.stateLayout.StateLayout;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.carInsurance.InsuranceCompareBean;
import com.wongxd.carinsurance.bean.carInsurance.InsuranceSelectBean;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.WeiboDialogUtils;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by wxd1 on 2017/3/22.
 */
public class InsuranceSelectActivity extends BaseSwipeActivity {

    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tv_car_no)
    TextView tvCarNo;
    @BindView(R.id.tv_car_user)
    TextView tvCarUser;
    @BindView(R.id.tv_car_infortwo)
    TextView tvCarInfortwo;
    @BindView(R.id.rv)
    XRecyclerView rv;
    @BindView(R.id.buju)
    LinearLayout buju;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private Context mContext;
    private AppCompatActivity thisActivity;
    private String peopleId;
    private String carId;
    private List<InsuranceSelectBean.DataBean.InsuresBean> list;
    private RvAdapter adapter;
    private Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_select_layout);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();

        peopleId = getIntent().getStringExtra("peopleId");
        carId = getIntent().getStringExtra("carId");
        list = new ArrayList<InsuranceSelectBean.DataBean.InsuresBean>();
        adapter = new RvAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setLoadingMoreEnabled(false);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getInfo();
            }

            @Override
            public void onLoadMore() {

            }
        });
        getInfo();
    }

    private void getInfo() {
        stateLayout.showProgressView();
        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.QueryInsuranceKind_URL)
                        .addParams("token", App.token)
                        .addParams("peopleId", peopleId)
                        .addParams("carId", carId)
                , UrlConfig.QueryInsuranceKind_URL,
                thisActivity, "获取险种信息中", new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Logger.e(response);

                        Gson gson = new Gson();
                        InsuranceSelectBean selectBean = gson.fromJson(response, InsuranceSelectBean.class);
                        if (selectBean.getCode() == 100) {
                            if (selectBean.getData().getInsures().size() == 0) {
                                btnSubmit.setVisibility(View.GONE);
                                stateLayout.showEmptyView("这里空空如也");
                                stateLayout.setEmptyAction(v -> getInfo());
                            } else {
                                btnSubmit.setVisibility(View.VISIBLE);
                                tvCarNo.setText(selectBean.getData().getLicenseNo());
                                tvCarUser.setText(selectBean.getData().getOwnerName());
                                tvCarInfortwo.setText(selectBean.getData().getBrandName());

                                List<InsuranceSelectBean.DataBean.InsuresBean> insuresBeanList = selectBean.getData().getInsures();

                                for (int i = 0; i < insuresBeanList.size(); i++) {
                                    if (insuresBeanList.get(i).getInsuranceKind().getInsuranceType() == 3) {
                                        insuresBeanList.get(i).setIsSelected(1);
                                    }
                                }

                                list.clear();
                                list.addAll(insuresBeanList);
                                adapter.notifyDataSetChanged();

                                stateLayout.showContentView();
                            }
                        } else {
                            btnSubmit.setVisibility(View.GONE);
                            stateLayout.showErrorView(selectBean.getMsg());
                            stateLayout.setErrorAction(v -> getInfo());
                        }
                        rv.refreshComplete();
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        btnSubmit.setVisibility(View.GONE);
                        stateLayout.showErrorView(e.getMessage());
                        stateLayout.setErrorAction(v -> getInfo());
                        rv.refreshComplete();
                    }
                });
    }

    @OnClick({R.id.rl_return, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_return:
                thisActivity.finish();
                break;
            case R.id.btn_submit:

                dialog = WeiboDialogUtils.createLoadingDialog(thisActivity, "请稍后");
/**
 * JSONObject jsonObject = new JSONObject();
 JSONArray jsonArray = new JSONArray();
 JSONObject obj = new JSONObject();
 obj.put("id", obj.getId());
 obj.put("url", obj.getUrl());
 jsonArray.put(obj);
 jsonObject.put("array", jsonArray);
 jsonObject.toString();
 */
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (int i = 0; i < list.size(); i++) {
                        InsuranceSelectBean.DataBean.InsuresBean bean = list.get(i);
                        JSONObject item = new JSONObject();


                        item.put("token", App.token);
                        item.put("peopleId", Integer.valueOf(peopleId));
                        item.put("carId", Integer.valueOf(carId));
                        item.put("isChoose", bean.getIsSelected());
                        item.put("insuranceKindId", bean.getInsuranceKind().getId());
                        item.put("insurenceKindMark", bean.getInsuranceKind().getInsurenceKindMark());
                        if (null != bean.getCurrentInsuranceInfoBean()) {
                            item.put("insuranceInfoId", bean.getCurrentInsuranceInfoBean().getId());
                        } else {
                            item.put("insuranceInfoId", 0);
                        }

                        // 不计免赔 abatementFlag

                        item.put("abatementFlag", bean.getInsuranceKind().getCurrentAbatementFlag());


                        jsonArray.put(item);

                    }

                    jsonObject.put("jsons", jsonArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                JSONObject rc = new JSONObject();
//                try {
//                    rc.put("requestCount", App.requestCount);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                String re = "{\"jsons\":" + jsonArray.toString() + "}";
//                String re = "{{\"requestCount\":" + App.requestCount  + "},{\"jsons\":" + jsonArray.toString() + "}}";
//                String re ="{"+ rc.toString() + ",{\"jsons\":" + jsonArray.toString() + "}}";
                JSONObject info = new JSONObject();
                try {
                    info.put("requestCount", App.requestCount);
                    info.put("jsons", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String re = info.toString();

                Logger.d(re);
                OkHttpUtils.postString().url(UrlConfig.SubmitInsuranceKind_URL)
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .content(re)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (null != dialog) WeiboDialogUtils.closeDialog(dialog);
                        ToastUtil.CustomToast(mContext, "请求失败  " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (null != dialog) WeiboDialogUtils.closeDialog(dialog);

                        Logger.d(response);
                        Gson gson = new Gson();
                        InsuranceCompareBean insuranceCompareBean = gson.fromJson(response, InsuranceCompareBean.class);

                        if (null != insuranceCompareBean) {
                            if (insuranceCompareBean.getCode() == 100) {
                                App.requestCount++;
                                Intent i = new Intent(thisActivity, InsuranceParityActivity.class);
                                i.putExtra("info", insuranceCompareBean);
                                startActivity(i);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity)
                                        .setCancelable(false)
                                        .setTitle(Html.fromHtml("<font color='#ff0000'><big>比价有误</big></font>"))
                                        .setMessage(insuranceCompareBean.getMsg())
                                        .setPositiveButton("我知道了", (dialog1, which) -> dialog1.dismiss());
                                builder.show();
                            }

                        } else ToastUtil.CustomToast(mContext, "比价结果有误");

                    }
                });

                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (App.buyStatu == 1) {
            App.buyStatu = 0;
        }
    }

    class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_insurance_select_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            InsuranceSelectBean.DataBean.InsuresBean.InsuranceKindBean insuranceKindBean = list.get(position).getInsuranceKind();
            holder.tvTitle.setText(insuranceKindBean.getInsuranceName());
            //险种类型（1.主险 2.附加险 3.交强险）
            final int type = insuranceKindBean.getInsuranceType();
            if (type == 1) {
                holder.iv_type.setBackground(getResources().getDrawable(R.drawable.zhuxian));
            } else if (type == 2) {
                holder.iv_type.setBackground(getResources().getDrawable(R.drawable.fujia));
            } else if (type == 3) {
                holder.iv_type.setBackground(getResources().getDrawable(R.drawable.jiaoqiang));
                list.get(position).setIsSelected(1);
            }

            if ((list.get(position).getInsuranceKind().getCurrentAbatementFlag() == -2)) {
                int flag = list.get(position).getInsuranceKind().getAbatementFlag();
                list.get(position).getInsuranceKind().setCurrentAbatementFlag(flag);
            }

            //是否不计免赔（1.是 0.否 -1 表示该险种不存在此选项）
            switch (list.get(position).getInsuranceKind().getCurrentAbatementFlag()) {
                case -1:
                    holder.ll_free.setVisibility(View.GONE);
                    break;
                case 0:
                    holder.ll_free.setVisibility(View.VISIBLE);
                    holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen));
                    break;
                case 1:
                    holder.ll_free.setVisibility(View.VISIBLE);
                    holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen_check));
                    break;
            }

            //不计免赔信息 传服务器用
            int tempFlag = list.get(position).getInsuranceKind().getCurrentAbatementFlag();
            list.get(position).setCurrentAbatementFlag(tempFlag == -2 ? -1 : tempFlag);


            if (list.get(position).getInsuranceKind().getCurrentAbatementFlag() != -1) {
                //不计免赔信息加到listbean中
                holder.iv_free.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (list.get(position).getInsuranceKind().getCurrentAbatementFlag() == 1) {
                            list.get(position).getInsuranceKind().setCurrentAbatementFlag(0);
                            holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen));
                        } else {
                            list.get(position).getInsuranceKind().setCurrentAbatementFlag(1);
                            holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen_check));
                        }

                    }
                });
            }


            //附加险选项
            final List<InsuranceSelectBean.DataBean.InsuresBean.InsuranceInfoBean> insuranceInfoBean = list.get(position).getInsuranceInfos();
            if (null == insuranceInfoBean || insuranceInfoBean.size() == 0) {
                holder.tv_list_option.setVisibility(View.GONE);
            } else {

                InsuranceSelectBean.DataBean.InsuresBean.InsuranceInfoBean bean = new InsuranceSelectBean.DataBean.InsuresBean.InsuranceInfoBean();
                bean.setId(insuranceInfoBean.get(0).getId());
                bean.setShowValue(insuranceInfoBean.get(0).getShowValue());
                //添加附加险信息到bean中
                list.get(position).setCurrentInsuranceInfoBean(bean);
                holder.tv_list_option.setText(insuranceInfoBean.get(0).getShowValue());
//                holder.tv_list_option.setText("附加信息");
                holder.tv_list_option.setVisibility(View.VISIBLE);
                holder.tv_list_option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listPop = new ArrayList<Map<String, String>>();
                        for (int i = 0; i < insuranceInfoBean.size(); i++) {
                            Map<String, String> item = new HashMap<String, String>();
                            item.put("id", insuranceInfoBean.get(i).getId() + "");
                            item.put("name", insuranceInfoBean.get(i).getShowValue());
                            listPop.add(item);
                        }
                        showPopWindow(thisActivity, holder.tv_list_option, position);

                    }
                });


            }

            if (null != list.get(position).getCurrentInsuranceInfoBean()) {
                holder.tv_list_option.setText(list.get(position).getCurrentInsuranceInfoBean().getShowValue());
            }


            final InsuranceSelectBean.DataBean.InsuresBean item = list.get(position);


            holder.iv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type != 3) {
                        if (item.getIsSelected() == 1) {
                            item.setIsSelected(0);
                            holder.iv_check.setBackground(getResources().getDrawable(R.drawable.gou));
                            item.getInsuranceKind().setCurrentAbatementFlag(0);
                            holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen));
                        } else {
                            item.setIsSelected(1);
                            holder.iv_check.setBackground(getResources().getDrawable(R.drawable.gou_check));
                            item.getInsuranceKind().setCurrentAbatementFlag(1);
                            holder.iv_free.setBackground(getResources().getDrawable(R.drawable.gou_zhen_check));
                        }
                    }
                }
            });


            if (item.getIsSelected() == 1) {
                holder.iv_check.setBackground(getResources().getDrawable(R.drawable.gou_check));
            } else {
                holder.iv_check.setBackground(getResources().getDrawable(R.drawable.gou));
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTitle;
            private TextView tv_list_option;

            private ImageView iv_check;
            private ImageView iv_free;
            private ImageView iv_type;
            private LinearLayout ll_free;

            public ViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tv_list_option = (TextView) itemView.findViewById(R.id.tv_list_option);

                iv_check = (ImageView) itemView.findViewById(R.id.iv_check);
                ll_free = (LinearLayout) itemView.findViewById(R.id.ll_free);
                iv_free = (ImageView) itemView.findViewById(R.id.iv_free);
                iv_type = (ImageView) itemView.findViewById(R.id.iv_type);

            }
        }
    }


    PopupWindow pop;
    View layout;
    ListView menulist;
    List<Map<String, String>> listPop;


    //listPostion 为 list中的position
    public void showPopWindow(AppCompatActivity activity, final TextView tvTarget, final int listPostion) {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        } else {
            layout = activity.getLayoutInflater().inflate(R.layout.pop_menulist, null);
            menulist = (ListView) layout
                    .findViewById(R.id.menulist);
            SimpleAdapter listAdapter = new SimpleAdapter(
                    activity, listPop,
                    R.layout.pop_menuitem, new String[]{"id", "name"},
                    new int[]{R.id.tv_id, R.id.tv_name});
            menulist.setAdapter(listAdapter);

            // 点击listview中item的处理
            menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int position, long arg3) {
                    // 改变顶部对应TextView值
                    Map<String, String> currentMap = listPop.get(position);
                    tvTarget.setText(listPop.get(position).get("name"));
                    InsuranceSelectBean.DataBean.InsuresBean.InsuranceInfoBean bean = new InsuranceSelectBean.DataBean.InsuresBean.InsuranceInfoBean();
                    bean.setId(Integer.valueOf(currentMap.get("id")));
                    bean.setShowValue(currentMap.get("name"));
                    //添加附加险信息到bean中
                    list.get(listPostion).setCurrentInsuranceInfoBean(bean);

                    // 隐藏弹出窗口
                    if (pop != null && pop.isShowing()) {
                        pop.dismiss();
                    }
                }
            });

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            pop = new PopupWindow(layout, tvTarget.getWidth(),
                    600);

            Drawable d = new ColorDrawable(activity.getResources().getColor(R.color.white));
            pop.setBackgroundDrawable(d);
            pop.setAnimationStyle(R.style.PopupAnimation);
            pop.update();
            pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            // 设置pop被键盘顶上去，而不是遮挡
            pop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            pop.setTouchable(true); // 设置popupwindow可点击
            pop.setOutsideTouchable(true); // 设置popupwindow外部可点击
            pop.setFocusable(true); // 获取焦点S

            pop.showAsDropDown(tvTarget, 0,
                    tvTarget.getTop());

            pop.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        pop.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

}
