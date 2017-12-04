package com.wongxd.carinsurance.bean.carInsurance;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wxd1 on 2017/3/23.
 */

public class InsuranceCompareBean implements Serializable{


    /**
     * msg : 请求成功
     * code : 100
     * data : [{"msg":"算单成功","totalValue":10086,"forceBeginDate":"20170323105925","licenseNo":"川B57652","brandName":"别克SGM7240EAAB","bizBeginDate":"20170323105925","policyNo":"66120170323112355","policyCode":200,"seatNumber":5,"imgUrl":"static/img/abc.png","ownerName":"童俊旗","name":"大地保险","actions":[{"actionTitle":"1000元加油卡","content":"全国加油站全程使用，让您的爱车充满活力！"}],"carValue":210900},{"msg":"算单成功","totalValue":10086,"forceBeginDate":"20170323105925","licenseNo":"川B57652","brandName":"别克SGM7240EAAB","bizBeginDate":"20170323105925","policyNo":"66220170323112355","policyCode":200,"seatNumber":5,"imgUrl":"static/img/abc.png","ownerName":"童俊旗","name":"渤海保险","actions":[{"actionTitle":"1000元加油卡","content":"全国加油站全程使用，让您的爱车充满活力！"}],"carValue":210900}]
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

    public static class DataBean implements Serializable{
        /**
         * msg : 算单成功
         * totalValue : 10086
         * forceBeginDate : 20170323105925
         * licenseNo : 川B57652
         * brandName : 别克SGM7240EAAB
         * bizBeginDate : 20170323105925
         * policyNo : 66120170323112355
         * policyCode : 200
         * seatNumber : 5
         * imgUrl : static/img/abc.png
         * ownerName : 童俊旗
         * name : 大地保险
         * actions : [{"actionTitle":"1000元加油卡","content":"全国加油站全程使用，让您的爱车充满活力！"}]
         * carValue : 210900
         */

        private String msg;
        private double totalValue;
        private String forceBeginDate;
        private String licenseNo;
        private String brandName;
        private String bizBeginDate;
        private String policyNo;
        private int policyCode;
        private int seatNumber;
        private String imgUrl;
        private String ownerName;
        private String name;
        private double carValue;
        private List<ActionsBean> actions;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(int totalValue) {
            this.totalValue = totalValue;
        }

        public String getForceBeginDate() {
            return forceBeginDate;
        }

        public void setForceBeginDate(String forceBeginDate) {
            this.forceBeginDate = forceBeginDate;
        }

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBizBeginDate() {
            return bizBeginDate;
        }

        public void setBizBeginDate(String bizBeginDate) {
            this.bizBeginDate = bizBeginDate;
        }

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public int getPolicyCode() {
            return policyCode;
        }

        public void setPolicyCode(int policyCode) {
            this.policyCode = policyCode;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getCarValue() {
            return carValue;
        }

        public void setCarValue(int carValue) {
            this.carValue = carValue;
        }

        public List<ActionsBean> getActions() {
            return actions;
        }

        public void setActions(List<ActionsBean> actions) {
            this.actions = actions;
        }

        public static class ActionsBean  implements Serializable {
            /**
             * actionTitle : 1000元加油卡
             * content : 全国加油站全程使用，让您的爱车充满活力！
             */

            private String actionTitle;
            private String content;

            public String getActionTitle() {
                return actionTitle;
            }

            public void setActionTitle(String actionTitle) {
                this.actionTitle = actionTitle;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
