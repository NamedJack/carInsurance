package com.wongxd.carinsurance.bean.photoUpload;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/22.
 */

public class PhotoDetailBean  {

    /**
     * msg : 鑾峰彇鎴愬姛!
     * code : 100
     * data : {"listVehicles":[{"vehiclesImg":"upload/vehicles/vehiclesImgs_1.jpg","vehiclesFlag":1,"id":14}],"listDriver":[{"driverFlag":1,"driverImg":"upload/driver/driverImgs_1.jpg","id":12}]}
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
        private List<ListVehiclesBean> listVehicles;
        private List<ListDriverBean> listDriver;

        public List<ListVehiclesBean> getListVehicles() {
            return listVehicles;
        }

        public void setListVehicles(List<ListVehiclesBean> listVehicles) {
            this.listVehicles = listVehicles;
        }

        public List<ListDriverBean> getListDriver() {
            return listDriver;
        }

        public void setListDriver(List<ListDriverBean> listDriver) {
            this.listDriver = listDriver;
        }

        public static class ListVehiclesBean {
            /**
             * vehiclesImg : upload/vehicles/vehiclesImgs_1.jpg
             * vehiclesFlag : 1
             * id : 14
             */

            private String vehiclesImg;
            private int vehiclesFlag;
            private int id;

            public String getVehiclesImg() {
                return vehiclesImg;
            }

            public void setVehiclesImg(String vehiclesImg) {
                this.vehiclesImg = vehiclesImg;
            }

            public int getVehiclesFlag() {
                return vehiclesFlag;
            }

            public void setVehiclesFlag(int vehiclesFlag) {
                this.vehiclesFlag = vehiclesFlag;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class ListDriverBean {
            /**
             * driverFlag : 1
             * driverImg : upload/driver/driverImgs_1.jpg
             * id : 12
             */

            private int driverFlag;
            private String driverImg;
            private int id;

            public int getDriverFlag() {
                return driverFlag;
            }

            public void setDriverFlag(int driverFlag) {
                this.driverFlag = driverFlag;
            }

            public String getDriverImg() {
                return driverImg;
            }

            public void setDriverImg(String driverImg) {
                this.driverImg = driverImg;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
