package com.wongxd.carinsurance.bean.orderManager;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/24.
 */

public class OrderListBean {

    /**
     * msg : 获取成功！
     * code : 100
     * “totlePageNo” : 5
     * “pageNo” : 3
     * data : [{"policyCarNo":"川B56752","tolPolicyMoney":5000,"name":"渤海保险","splash":"static/img/abc.png","id":2,"policyPeople":"童俊旗"}]
     */

    private String msg;
    private int code;
    private int totlePageNo;
    private int pageNo;
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

    public int getTotlePageNo() {
        return totlePageNo;
    }

    public void setTotlePageNo(int totlePageNo) {
        this.totlePageNo = totlePageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * policyCarNo : 川B56752
         * tolPolicyMoney : 5000
         * name : 渤海保险
         * splash : static/img/abc.png
         * id : 2
         * policyPeople : 童俊旗
         */

        private String policyCarNo;
        private double tolPolicyMoney;
        private String name;
        private String icon;
        private int id;
        private String policyPeople;

        private String policyNo;

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }


        public String getPolicyCarNo() {
            return policyCarNo;
        }

        public void setPolicyCarNo(String policyCarNo) {
            this.policyCarNo = policyCarNo;
        }

        public double getTolPolicyMoney() {
            return tolPolicyMoney;
        }

        public void setTolPolicyMoney(double tolPolicyMoney) {
            this.tolPolicyMoney = tolPolicyMoney;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPolicyPeople() {
            return policyPeople;
        }

        public void setPolicyPeople(String policyPeople) {
            this.policyPeople = policyPeople;
        }
    }
}
