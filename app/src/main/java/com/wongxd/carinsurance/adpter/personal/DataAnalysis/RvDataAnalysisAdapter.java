package com.wongxd.carinsurance.adpter.personal.DataAnalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.bean.personal.DataAnalysisBean;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/17.
 */

public class RvDataAnalysisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<DataAnalysisBean.DataBean> list;
    private ITEM_TYPE type;

    //建立枚举 2个item 类型
    public static enum ITEM_TYPE {
        ITEM_FOUR,
        ITEM_FOUR_2,//业绩统计
        ITEM_FIVE
    }

    public RvDataAnalysisAdapter(Context context, List<DataAnalysisBean.DataBean> items, ITEM_TYPE type) {
        this.list = items;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ITEM_TYPE.ITEM_FIVE.ordinal()) {
            return new InsurancePolicyViewHolder(mLayoutInflater.inflate(R.layout.insurance_policy_item, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_FOUR.ordinal()) { //出单分析
            return new PolicyAnalysisViewHolder(mLayoutInflater.inflate(R.layout.data_analysis_item, parent, false));
        } else {//业绩分析
            return new PerformanceStatisticsViewHolder(mLayoutInflater.inflate(R.layout.data_analysis_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHelper.setScaleX(holder.itemView,0.8f);
        ViewHelper.setScaleY(holder.itemView,0.8f);
        ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

        DataAnalysisBean.DataBean item = list.get(position);

        if (holder instanceof PolicyAnalysisViewHolder) { //出单分析
            ((PolicyAnalysisViewHolder) holder).tvName.setText(item.getInsurerName()+"");
            ((PolicyAnalysisViewHolder) holder).tvSuanDan.setText(item.getCalNum()+"");
            ((PolicyAnalysisViewHolder) holder).tvChuBao.setText(item.getSendNum()+"");
            ((PolicyAnalysisViewHolder) holder).tvPercent.setText(item.getSendPi()+"");

        } else if (holder instanceof PerformanceStatisticsViewHolder) { //业绩统计
            ((PerformanceStatisticsViewHolder) holder).tvName.setText(item.getInsurerName()+"");
            ((PerformanceStatisticsViewHolder) holder).tvForce.setText(item.getForceValue()+"");
            ((PerformanceStatisticsViewHolder) holder).tvBusiness.setText(item.getBizValue()+"");
            ((PerformanceStatisticsViewHolder) holder).tvPerformance.setText(item.getAchievement()+"");

        } else if (holder instanceof InsurancePolicyViewHolder) { //五项

            ((InsurancePolicyViewHolder) holder).tvName.setText(item.getInsurerName()+"");
            ((InsurancePolicyViewHolder) holder).tvForceNum.setText(item.getForceNum()+"");
            ((InsurancePolicyViewHolder) holder).tvForce.setText(item.getForceValue()+"");
            ((InsurancePolicyViewHolder) holder).tvBusinessNum.setText(item.getBizNum()+"");
            ((InsurancePolicyViewHolder) holder).tvBusiness.setText(item.getBizValue()+"");

        }
    }

    //设置ITEM类型，可以自由发挥，
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM_FOUR.ordinal()代表0， ITEM_TYPE.ITEM_FIVE.ordinal()代表1
        return type.ordinal();
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    private class PolicyAnalysisViewHolder extends RecyclerView.ViewHolder { //出单分析
        private TextView tvName; //保险公司名
        private TextView tvSuanDan; //交强险
        private TextView tvChuBao;//商业险
        private TextView tvPercent; //出单率

        public PolicyAnalysisViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvSuanDan = (TextView) itemView.findViewById(R.id.tv_force);
            tvChuBao = (TextView) itemView.findViewById(R.id.tv_business);
            tvPercent = (TextView) itemView.findViewById(R.id.tv_performance);
        }
    }

    private class PerformanceStatisticsViewHolder extends RecyclerView.ViewHolder { //业绩统计
        private TextView tvName; //保险公司名
        private TextView tvForce; //交强险
        private TextView tvBusiness;//商业险
        private TextView tvPerformance; //业绩

        public PerformanceStatisticsViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvForce = (TextView) itemView.findViewById(R.id.tv_force);
            tvBusiness = (TextView) itemView.findViewById(R.id.tv_business);
            tvPerformance = (TextView) itemView.findViewById(R.id.tv_performance);
        }
    }


    //五项用
    private class InsurancePolicyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName; //保险公司名
        private TextView tvForceNum;//交强险单数
        private TextView tvForce; //交强险
        private TextView tvBusinessNum; //商业险单数
        private TextView tvBusiness;//商业险

        public InsurancePolicyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvForce = (TextView) itemView.findViewById(R.id.tv_force);
            tvForceNum = (TextView) itemView.findViewById(R.id.tv_force_num);
            tvBusinessNum = (TextView) itemView.findViewById(R.id.tv_business_num);
            tvBusiness = (TextView) itemView.findViewById(R.id.tv_business);
        }
    }

}
