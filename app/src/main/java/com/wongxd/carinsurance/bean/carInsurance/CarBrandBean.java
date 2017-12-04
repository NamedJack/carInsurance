package com.wongxd.carinsurance.bean.carInsurance;

/**
 * Created by wxd1 on 2017/4/25.
 */

import java.util.List;

/**
 * 根据车架号查询车辆信息bean
 */
public class CarBrandBean {


    /**
     * msg : 查询成功
     * code : 100
     * data : [{"modelsYear":"201312","brandName":"别克SGM7240EAAB","remark":"手自一体 领先舒适型 国Ⅴ","paCode":"JYD1075SHT","seatNumber":"5","tranType":"手自一体","carValue":"190900"},{"modelsYear":"201312","brandName":"别克SGM7240EAAB","remark":"手自一体 精英舒适型 国Ⅴ","paCode":"JYD1076SHT","seatNumber":"5","tranType":"手自一体","carValue":"210900"},{"modelsYear":"201312","brandName":"别克SGM7240EAAB","remark":"手自一体 豪华舒适型 国Ⅴ","paCode":"JYD1077SHT","seatNumber":"5","tranType":"手自一体","carValue":"230900"}]
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
         * modelsYear : 201312
         * brandName : 别克SGM7240EAAB
         * remark : 手自一体 领先舒适型 国Ⅴ
         * paCode : JYD1075SHT
         * seatNumber : 5
         * tranType : 手自一体
         * carValue : 190900
         */

        private String modelsYear;
        private String brandName;
        private String remark;
        private String paCode;
        private String seatNumber;
        private String tranType;
        private String carValue;
        private String powerType;

        public String getPowerType() {
            return powerType;
        }

        public void setPowerType(String powerType) {
            this.powerType = powerType;
        }

        public String getModelsYear() {
            return modelsYear;
        }

        public void setModelsYear(String modelsYear) {
            this.modelsYear = modelsYear;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPaCode() {
            return paCode;
        }

        public void setPaCode(String paCode) {
            this.paCode = paCode;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getTranType() {
            return tranType;
        }

        public void setTranType(String tranType) {
            this.tranType = tranType;
        }

        public String getCarValue() {
            return carValue;
        }

        public void setCarValue(String carValue) {
            this.carValue = carValue;
        }
    }
}
