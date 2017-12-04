package com.wongxd.carinsurance.bean.personal;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/17.
 */

public class DataAnalysisBean {


    /**
     * msg : 获取成功
     * code : 100
     * data : [{"calNum":0,"sendPi":"0%","bizValue":10172,"achievement":0,"forceValue":10000,"bizNum":2,"sendNum":2,"insurerName":"大地保险","forceNum":2},{"calNum":0,"sendPi":"0%","bizValue":0,"achievement":0,"forceValue":0,"bizNum":0,"sendNum":0,"insurerName":"渤海保险","forceNum":0}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * calNum : 0
         * sendPi : 0%
         * bizValue : 10172
         * achievement : 0
         * forceValue : 10000
         * bizNum : 2
         * sendNum : 2
         * insurerName : 大地保险
         * forceNum : 2
         */

        private int calNum;
        private String sendPi;
        private double bizValue;
        private double achievement;
        private double forceValue;
        private int bizNum;
        private int sendNum;
        private String insurerName;
        private int forceNum;

        public int getCalNum() {
            return calNum;
        }

        public void setCalNum(int calNum) {
            this.calNum = calNum;
        }

        public String getSendPi() {
            return sendPi;
        }

        public void setSendPi(String sendPi) {
            this.sendPi = sendPi;
        }

        public double getBizValue() {
            return bizValue;
        }

        public void setBizValue(double bizValue) {
            this.bizValue = bizValue;
        }

        public double getAchievement() {
            return achievement;
        }

        public void setAchievement(double achievement) {
            this.achievement = achievement;
        }

        public double getForceValue() {
            return forceValue;
        }

        public void setForceValue(double forceValue) {
            this.forceValue = forceValue;
        }

        public int getBizNum() {
            return bizNum;
        }

        public void setBizNum(int bizNum) {
            this.bizNum = bizNum;
        }

        public int getSendNum() {
            return sendNum;
        }

        public void setSendNum(int sendNum) {
            this.sendNum = sendNum;
        }

        public String getInsurerName() {
            return insurerName;
        }

        public void setInsurerName(String insurerName) {
            this.insurerName = insurerName;
        }

        public int getForceNum() {
            return forceNum;
        }

        public void setForceNum(int forceNum) {
            this.forceNum = forceNum;
        }
    }
}
