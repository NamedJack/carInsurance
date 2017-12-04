package com.wongxd.carinsurance.bean.carInsurance;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/22.
 */

public class InsuranceSelectBean {

    /**
     * msg : 获取成功
     * code : 100
     * data : {"licenseNo":"川B56752","brandName":"东风日产","ownerName":"童俊旗","insures":[{"insuranceKind":{"id":1,"insuranceName":"机动车损失险","insuranceType":1,"abatementFlag":1},"insuranceInfos":[]},{"insuranceKind":{"id":2,"insuranceName":"机动车第三责任险","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":1},{"showValue":"5万","id":2},{"showValue":"10万","id":3},{"showValue":"15万","id":4},{"showValue":"10万","id":5},{"showValue":"25万","id":6},{"showValue":"30万","id":7},{"showValue":"50万","id":8},{"showValue":"100万","id":9},{"showValue":"150万","id":10},{"showValue":"200万","id":11}]},{"insuranceKind":{"id":3,"insuranceName":"机动车全车盗抢险","insuranceType":1,"abatementFlag":0},"insuranceInfos":[]},{"insuranceKind":{"id":4,"insuranceName":"机动车车上人员责任险（司机）","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":12},{"showValue":"1万","id":13},{"showValue":"2万","id":14},{"showValue":"3万","id":15},{"showValue":"4万","id":16},{"showValue":"5万","id":17},{"showValue":"8万","id":18},{"showValue":"10万","id":19},{"showValue":"15万","id":20},{"showValue":"20万","id":21},{"showValue":"30万","id":22},{"showValue":"40万","id":23},{"showValue":"50万","id":24}]},{"insuranceKind":{"id":5,"insuranceName":"机动车车上人员责任险（乘客）","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":25},{"showValue":"1万/座","id":26},{"showValue":"2万/座","id":27},{"showValue":"3万/座","id":28},{"showValue":"4万/座","id":29},{"showValue":"5万/座","id":30},{"showValue":"8万/座","id":31},{"showValue":"10万/座","id":32},{"showValue":"15万/座","id":33},{"showValue":"20万/座","id":34},{"showValue":"30万/座","id":35},{"showValue":"40万/座","id":36},{"showValue":"50万/座","id":37}]},{"insuranceKind":{"id":6,"insuranceName":"机动车车身划痕损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":7,"insuranceName":"机动车自然损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":8,"insuranceName":"机动车发动机涉水损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":9,"insuranceName":"机动车玻璃单独破碎险","insuranceType":-1,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":10,"insuranceName":"机动车强制保险和车船使用税","insuranceType":-1,"abatementFlag":3},"insuranceInfos":[]}]}
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
         * licenseNo : 川B56752
         * brandName : 东风日产
         * ownerName : 童俊旗
         * insures : [{"insuranceKind":{"id":1,"insuranceName":"机动车损失险","insuranceType":1,"abatementFlag":1},"insuranceInfos":[]},{"insuranceKind":{"id":2,"insuranceName":"机动车第三责任险","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":1},{"showValue":"5万","id":2},{"showValue":"10万","id":3},{"showValue":"15万","id":4},{"showValue":"10万","id":5},{"showValue":"25万","id":6},{"showValue":"30万","id":7},{"showValue":"50万","id":8},{"showValue":"100万","id":9},{"showValue":"150万","id":10},{"showValue":"200万","id":11}]},{"insuranceKind":{"id":3,"insuranceName":"机动车全车盗抢险","insuranceType":1,"abatementFlag":0},"insuranceInfos":[]},{"insuranceKind":{"id":4,"insuranceName":"机动车车上人员责任险（司机）","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":12},{"showValue":"1万","id":13},{"showValue":"2万","id":14},{"showValue":"3万","id":15},{"showValue":"4万","id":16},{"showValue":"5万","id":17},{"showValue":"8万","id":18},{"showValue":"10万","id":19},{"showValue":"15万","id":20},{"showValue":"20万","id":21},{"showValue":"30万","id":22},{"showValue":"40万","id":23},{"showValue":"50万","id":24}]},{"insuranceKind":{"id":5,"insuranceName":"机动车车上人员责任险（乘客）","insuranceType":1,"abatementFlag":1},"insuranceInfos":[{"showValue":"不投保","id":25},{"showValue":"1万/座","id":26},{"showValue":"2万/座","id":27},{"showValue":"3万/座","id":28},{"showValue":"4万/座","id":29},{"showValue":"5万/座","id":30},{"showValue":"8万/座","id":31},{"showValue":"10万/座","id":32},{"showValue":"15万/座","id":33},{"showValue":"20万/座","id":34},{"showValue":"30万/座","id":35},{"showValue":"40万/座","id":36},{"showValue":"50万/座","id":37}]},{"insuranceKind":{"id":6,"insuranceName":"机动车车身划痕损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":7,"insuranceName":"机动车自然损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":8,"insuranceName":"机动车发动机涉水损失险","insuranceType":0,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":9,"insuranceName":"机动车玻璃单独破碎险","insuranceType":-1,"abatementFlag":2},"insuranceInfos":[]},{"insuranceKind":{"id":10,"insuranceName":"机动车强制保险和车船使用税","insuranceType":-1,"abatementFlag":3},"insuranceInfos":[]}]
         */

        private String licenseNo;
        private String brandName;
        private String ownerName;
        private List<InsuresBean> insures;

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

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public List<InsuresBean> getInsures() {
            return insures;
        }

        public void setInsures(List<InsuresBean> insures) {
            this.insures = insures;
        }

        public static class InsuresBean {
            /**
             * insuranceKind : {"id":1,"insuranceName":"机动车损失险","insuranceType":1,"abatementFlag":1}
             * insuranceInfos : []
             */


            private int isSelected = 0;//是否选择该险种   0 未选 1 选中
            private int CurrentAbatementFlag = -1; //不计免赔    0 未选 1 选中 -1不存在

            public int getCurrentAbatementFlag() {
                return CurrentAbatementFlag;
            }

            public void setCurrentAbatementFlag(int currentAbatementFlag) {
                CurrentAbatementFlag = currentAbatementFlag;
            }

            public int getIsSelected() {
                return isSelected;
            }

            public void setIsSelected(int isSelected) {
                this.isSelected = isSelected;
            }

            private InsuranceKindBean insuranceKind;

            public List<InsuranceInfoBean> getInsuranceInfos() {
                return insuranceInfos;
            }

            public void setInsuranceInfos(List<InsuranceInfoBean> insuranceInfos) {
                this.insuranceInfos = insuranceInfos;
            }

            private List<InsuranceInfoBean> insuranceInfos;

            public InsuranceKindBean getInsuranceKind() {
                return insuranceKind;
            }

            public void setInsuranceKind(InsuranceKindBean insuranceKind) {
                this.insuranceKind = insuranceKind;
            }

            private InsuranceInfoBean currentInsuranceInfoBean;

            public InsuranceInfoBean getCurrentInsuranceInfoBean() {
                return currentInsuranceInfoBean;
            }

            public void setCurrentInsuranceInfoBean(InsuranceInfoBean currentInsuranceInfoBean) {
                this.currentInsuranceInfoBean = currentInsuranceInfoBean;
            }

            public static class InsuranceInfoBean {
                private String showValue;
                private int id;

                /**
                 * "showValue": "不投保",
                 * "id": 1
                 */

                public String getShowValue() {
                    return showValue;
                }

                public void setShowValue(String showValue) {
                    this.showValue = showValue;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }

            public static class InsuranceKindBean {
                /**
                 * id : 1
                 * insuranceName : 机动车损失险
                 * insuranceType : 1
                 * abatementFlag : 1
                 * insurenceKindMark:A
                 */
                private int currentAbatementFlag = -2; //当前不计免赔的flag

                public int getCurrentAbatementFlag() {
                    return currentAbatementFlag;
                }

                public void setCurrentAbatementFlag(int currentAbatementFlag) {
                    this.currentAbatementFlag = currentAbatementFlag;
                }

                private int id;
                private String insuranceName;
                private int insuranceType;
                private int abatementFlag;
                private String insurenceKindMark;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getInsuranceName() {
                    return insuranceName;
                }

                public void setInsuranceName(String insuranceName) {
                    this.insuranceName = insuranceName;
                }

                public int getInsuranceType() {
                    return insuranceType;
                }

                public void setInsuranceType(int insuranceType) {
                    this.insuranceType = insuranceType;
                }

                public int getAbatementFlag() {
                    return abatementFlag;
                }

                public void setAbatementFlag(int abatementFlag) {
                    this.abatementFlag = abatementFlag;
                }

                public String getInsurenceKindMark() {
                    return insurenceKindMark;
                }

                public void setInsurenceKindMark(String insurenceKindMark) {
                    this.insurenceKindMark = insurenceKindMark;
                }
            }
        }
    }
}
