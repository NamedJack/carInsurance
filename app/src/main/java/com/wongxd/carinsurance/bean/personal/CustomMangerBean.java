package com.wongxd.carinsurance.bean.personal;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/20.
 */

public class CustomMangerBean {

    /**
     * msg : 获取成功
     * code : 100
     * data : [{"number":0,"policyCarNo":"川B56752","dueDate":365,"policyNo":"201703201125","policyPeople":"童俊旗"}]
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
         * number : 0
         * policyCarNo : 川B56752
         * dueDate : 365
         * policyNo : 201703201125
         * policyPeople : 童俊旗
         */

        private int number;
        private String policyCarNo;
        private int dueDate;
        private String policyNo;
        private String policyPeople;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPolicyCarNo() {
            return policyCarNo;
        }

        public void setPolicyCarNo(String policyCarNo) {
            this.policyCarNo = policyCarNo;
        }

        public int getDueDate() {
            return dueDate;
        }

        public void setDueDate(int dueDate) {
            this.dueDate = dueDate;
        }

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public String getPolicyPeople() {
            return policyPeople;
        }

        public void setPolicyPeople(String policyPeople) {
            this.policyPeople = policyPeople;
        }
    }
}
