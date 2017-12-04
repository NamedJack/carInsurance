package com.wongxd.carinsurance.bean.personal;

/**
 * Created by wxd1 on 2017/3/20.
 */

public class CustomDetailBean {

    /**
     * msg : 获取成功
     * code : 100
     * data : {"car":{"specialCarFlag":0,"engineNo":"80558571","brandName":"东风日产","loanCarFlag":0,"bizBeginDate":"201611281000","bizEndDate":"201711281000","vehicleFrameNo":"DF9527PLD9527","registerTime":"201611281000"},"people":{"ownerName":"童俊旗","ownerIdNo":"510725199303026619","ownerPhoneNo":"18181769786"}}
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
         * car : {"specialCarFlag":0,"engineNo":"80558571","brandName":"东风日产","loanCarFlag":0,"bizBeginDate":"201611281000","bizEndDate":"201711281000","vehicleFrameNo":"DF9527PLD9527","registerTime":"201611281000"}
         * people : {"ownerName":"童俊旗","ownerIdNo":"510725199303026619","ownerPhoneNo":"18181769786"}
         */

        private CarBean car;
        private PeopleBean people;

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public PeopleBean getPeople() {
            return people;
        }

        public void setPeople(PeopleBean people) {
            this.people = people;
        }

        public static class CarBean {
            /**
             * specialCarFlag : 0
             * engineNo : 80558571
             * brandName : 东风日产
             * loanCarFlag : 0
             * bizBeginDate : 201611281000
             * bizEndDate : 201711281000
             * vehicleFrameNo : DF9527PLD9527
             * registerTime : 201611281000
             */

            private int specialCarFlag;
            private String engineNo;
            private String brandName;
            private int loanCarFlag;
            private String bizBeginDate;
            private String bizEndDate;
            private String vehicleFrameNo;
            private String registerTime;
            private String specialCarDate; //过户时间
            private String forceBeginDate;
            private String forceEndDate;

            public String getSpecialCarDate() {
                return specialCarDate;
            }

            public void setSpecialCarDate(String specialCarDate) {
                this.specialCarDate = specialCarDate;
            }

            public String getForceBeginDate() {
                return forceBeginDate;
            }

            public void setForceBeginDate(String forceBeginDate) {
                this.forceBeginDate = forceBeginDate;
            }

            public String getForceEndDate() {
                return forceEndDate;
            }

            public void setForceEndDate(String forceEndDate) {
                this.forceEndDate = forceEndDate;
            }

            public int getSpecialCarFlag() {
                return specialCarFlag;
            }

            public void setSpecialCarFlag(int specialCarFlag) {
                this.specialCarFlag = specialCarFlag;
            }

            public String getEngineNo() {
                return engineNo;
            }

            public void setEngineNo(String engineNo) {
                this.engineNo = engineNo;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public int getLoanCarFlag() {
                return loanCarFlag;
            }

            public void setLoanCarFlag(int loanCarFlag) {
                this.loanCarFlag = loanCarFlag;
            }

            public String getBizBeginDate() {
                return bizBeginDate;
            }

            public void setBizBeginDate(String bizBeginDate) {
                this.bizBeginDate = bizBeginDate;
            }

            public String getBizEndDate() {
                return bizEndDate;
            }

            public void setBizEndDate(String bizEndDate) {
                this.bizEndDate = bizEndDate;
            }

            public String getVehicleFrameNo() {
                return vehicleFrameNo;
            }

            public void setVehicleFrameNo(String vehicleFrameNo) {
                this.vehicleFrameNo = vehicleFrameNo;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }
        }


        public static class PeopleBean {
            /**
             * address : 四川 绵阳 涪城区
             * ownerName : 梁利
             * ownerArea :
             * ownerIdNo : 510823198009073885
             * detailedAddress : 南河路
             * ownerPhoneNo : 15882889599
             * ownerProvince :
             * ownerCity :
             */

            private String address;
            private String ownerName;
            private String ownerArea;
            private String ownerIdNo;
            private String detailedAddress;
            private String ownerPhoneNo;
            private String ownerProvince;
            private String ownerCity;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getOwnerArea() {
                return ownerArea;
            }

            public void setOwnerArea(String ownerArea) {
                this.ownerArea = ownerArea;
            }

            public String getOwnerIdNo() {
                return ownerIdNo;
            }

            public void setOwnerIdNo(String ownerIdNo) {
                this.ownerIdNo = ownerIdNo;
            }

            public String getDetailedAddress() {
                return detailedAddress;
            }

            public void setDetailedAddress(String detailedAddress) {
                this.detailedAddress = detailedAddress;
            }

            public String getOwnerPhoneNo() {
                return ownerPhoneNo;
            }

            public void setOwnerPhoneNo(String ownerPhoneNo) {
                this.ownerPhoneNo = ownerPhoneNo;
            }

            public String getOwnerProvince() {
                return ownerProvince;
            }

            public void setOwnerProvince(String ownerProvince) {
                this.ownerProvince = ownerProvince;
            }

            public String getOwnerCity() {
                return ownerCity;
            }

            public void setOwnerCity(String ownerCity) {
                this.ownerCity = ownerCity;
            }
        }


    }
}
