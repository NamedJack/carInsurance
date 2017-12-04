package com.wongxd.carinsurance.fgt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.adpter.FragmentViewpagerAdapter;
import com.wongxd.carinsurance.base.PopwindowMenu;
import com.wongxd.carinsurance.fgt.orderManager.OrderItemFragment;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class OrderManagerFragment extends BaseFragment implements View.OnClickListener {
    private List<Fragment> fgtList;
    private ViewPager viewpager;
    private TextView tvDate;
    private TextView tvNotPay;
    private TextView tvComplite;

    @Override
    public BaseFragment newFragment() {
        return new OrderManagerFragment();
    }

    public OrderManagerFragment() {
    }

    @Override
    public void initData(View view, Bundle saveInstance) {
        fgtList = new ArrayList<>();
        Bundle b = new Bundle();
        b.putString("type", "notPay");
        fgtList.add(OrderItemFragment.getInstance(b));

        Bundle bb = new Bundle();
        bb.putString("type", "complite");
        fgtList.add(OrderItemFragment.getInstance(bb));
    }

    @Override
    public void initView(View view, Bundle saveInstance) {
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        tvComplite = (TextView) view.findViewById(R.id.tv_complite);
        tvNotPay = (TextView) view.findViewById(R.id.tv_yet);
        tvDate = (TextView) view.findViewById(R.id.tv_date);

        dateTitleMap.put(0, "全部");
        dateTitleMap.put(1, "全部");

        tvComplite.setOnClickListener(this);
        tvNotPay.setOnClickListener(this);
        tvDate.setOnClickListener(this);

        FragmentManager manager = getChildFragmentManager();
        viewpager.setAdapter(new FragmentViewpagerAdapter(manager, fgtList));
        viewpager.setOffscreenPageLimit(3);
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


    @Override
    public int getChildLayoutRes() {
        return R.layout.fgt_order_manager;
    }


    List<Map<String, String>> dateList = new ArrayList<Map<String, String>>();//右上角时间选择

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDateList();


    }

    private void getDateList() {

        if (dateList.size() != 0) return;

        //获取查询时间ID
        OkHttpUtils.post().url(UrlConfig.QueryDateTimeByType_URL)
                .addParams("token", App.token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        getDateList();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 100) {
                                JSONArray jsonArray = jsonObject.optJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject o = jsonArray.getJSONObject(i);
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("name", o.optString("name"));
                                    map.put("id", o.optInt("id") + "");
                                    dateList.add(map);
                                }

                                App.dateList =dateList;
                            } else getDateList();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 切换顶部选择栏目
     *
     * @param isComplite 是否选中了已完成
     */
    private void changeCheckItem(boolean isComplite) {
        if (isComplite) {
            tvComplite.setTextColor(Color.WHITE);
            tvComplite.setBackground(getResources().getDrawable(R.drawable.right_round_stoke_with_solid));
            PopwindowMenu.TextviewAnimitor(tvDate);
            tvDate.setText(dateTitleMap.get(1));
            tvNotPay.setTextColor(getResources().getColor(R.color.textSelected));
            tvNotPay.setBackground(getResources().getDrawable(R.drawable.left_round_stoke));
        } else {
            tvNotPay.setTextColor(Color.WHITE);
            tvNotPay.setBackground(getResources().getDrawable(R.drawable.left_round_stoke_with_solid));
            PopwindowMenu.TextviewAnimitor(tvDate);
            tvDate.setText(dateTitleMap.get(0));
            tvComplite.setTextColor(getResources().getColor(R.color.textSelected));
            tvComplite.setBackground(getResources().getDrawable(R.drawable.right_round_stoke));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (App.ORDER_VIEWPAGER_SELECTED_POSTION != -1 && null != viewpager) {
                viewpager.setCurrentItem(App.ORDER_VIEWPAGER_SELECTED_POSTION);
                App.ORDER_VIEWPAGER_SELECTED_POSTION = -1;
            }
        }
    }

    public String Action = "Wongxd/CarInsurance/OrderManager";




    //时间选择title
    private Map<Integer, String> dateTitleMap = new HashMap<Integer, String>();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                if (dateList.size() == 0) {
                    ToastUtil.CustomToast(getContext(), "未获取到服务器时间列表");
                } else {
                    PopwindowMenu.datePopWindow((AppCompatActivity) getActivity(), tvDate, dateList, new PopwindowMenu.PopMenuCallBack() {
                        @Override
                        public void selectedItemChange(String str, int position, String iid) {
                            PopwindowMenu.TextviewAnimitor(tvDate);
                            Intent intent = new Intent();
                            intent.putExtra("id", iid);
                            intent.putExtra("visblePositon", viewpager.getCurrentItem());
                            dateTitleMap.put(viewpager.getCurrentItem(), str);
                            intent.setAction(Action);
                            getActivity().sendBroadcast(intent);

                        }
                    });
                }
                break;
            case R.id.tv_yet:
                changeCheckItem(false);
                viewpager.setCurrentItem(0, true);
                break;
            case R.id.tv_complite:
                changeCheckItem(true);
                viewpager.setCurrentItem(1, true);
                break;
        }
    }


}
