package com.wongxd.carinsurance.bean.carInsurance;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/23.
 */

public class InsuranceDetailBean {

    /**
     * msg : 获取成功
     * code : 100
     * data : {"forceBeginDate":"20170323142932","bizBeginDate":"20170323142932","insuranceKind":[{"insureName":"不计免赔","policyFei":610,"remarkMsg":"(包括：车损、三者、盗抢、司机、乘客)","policyType":2,"showPolicyMoney":""}],"forcePolicyMoney":5000,"bizPolicyMoney":5086,"totalMoney":10086,"policyNo":"66120170323143219"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * forceBeginDate : 20170323142932
         * bizBeginDate : 20170323142932
         * insuranceKind : [{"insureName":"不计免赔","policyFei":610,"remarkMsg":"(包括：车损、三者、盗抢、司机、乘客)","policyType":2,"showPolicyMoney":""}]
         * forcePolicyMoney : 5000
         * bizPolicyMoney : 5086
         * totalMoney : 10086
         * policyNo : 66120170323143219
         */

        private String   imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        private String forceBeginDate;
        private String bizBeginDate;
        private double forcePolicyMoney;
        private double bizPolicyMoney;
        private double totalMoney;
        private String policyNo;
        private List<InsuranceKindBean> insuranceKind;

        public String getForceBeginDate() {
            return forceBeginDate;
        }

        public void setForceBeginDate(String forceBeginDate) {
            this.forceBeginDate = forceBeginDate;
        }

        public String getBizBeginDate() {
            return bizBeginDate;
        }

        public void setBizBeginDate(String bizBeginDate) {
            this.bizBeginDate = bizBeginDate;
        }

        public double getForcePolicyMoney() {
            return forcePolicyMoney;
        }

        public void setForcePolicyMoney(int forcePolicyMoney) {
            this.forcePolicyMoney = forcePolicyMoney;
        }

        public double getBizPolicyMoney() {
            return bizPolicyMoney;
        }

        public void setBizPolicyMoney(int bizPolicyMoney) {
            this.bizPolicyMoney = bizPolicyMoney;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(int totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public List<InsuranceKindBean> getInsuranceKind() {
            return insuranceKind;
        }

        public void setInsuranceKind(List<InsuranceKindBean> insuranceKind) {
            this.insuranceKind = insuranceKind;
        }

        public static class InsuranceKindBean {
            /**
             * insureName : 不计免赔
             * policyFei : 610
             * remarkMsg : (包括：车损、三者、盗抢、司机、乘客)
             * policyType : 2
             * showPolicyMoney :
             */

            private String insureName;
            private double policyFei;
            private String remarkMsg;
            private int policyType;
            private String showPolicyMoney;

            public String getInsureName() {
                return insureName;
            }

            public void setInsureName(String insureName) {
                this.insureName = insureName;
            }

            public double getPolicyFei() {
                return policyFei;
            }

            public void setPolicyFei(int policyFei) {
                this.policyFei = policyFei;
            }

            public String getRemarkMsg() {
                return remarkMsg;
            }

            public void setRemarkMsg(String remarkMsg) {
                this.remarkMsg = remarkMsg;
            }

            public int getPolicyType() {
                return policyType;
            }

            public void setPolicyType(int policyType) {
                this.policyType = policyType;
            }

            public String getShowPolicyMoney() {
                return showPolicyMoney;
            }

            public void setShowPolicyMoney(String showPolicyMoney) {
                this.showPolicyMoney = showPolicyMoney;
            }
        }
    }
}
