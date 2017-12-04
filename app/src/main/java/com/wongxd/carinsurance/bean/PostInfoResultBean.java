package com.wongxd.carinsurance.bean;

import java.io.Serializable;

/**
 * Created by json on 2017/11/2.
 */

public class PostInfoResultBean implements Serializable {

    /**
     * msg : 保存成功
     * code : 100
     * data : {"peopleId":105,"carId":105}
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

    public static class DataBean implements Serializable {
        /**
         * peopleId : 105
         * carId : 105
         */

        private int peopleId;
        private int carId;

        public int getPeopleId() {
            return peopleId;
        }

        public void setPeopleId(int peopleId) {
            this.peopleId = peopleId;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }
    }
}
