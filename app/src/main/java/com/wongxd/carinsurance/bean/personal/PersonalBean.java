package com.wongxd.carinsurance.bean.personal;

import java.io.Serializable;

/**
 * Created by wxd1 on 2017/3/20.
 */

public class PersonalBean implements Serializable {
    /**
     * msg : 获取成功
     * code : 100
     * data : {"user":{"relName":"tjq","phoneNumber":"13981129786","headUrl":"splash","position":"业务员"}}
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
         * user : {"relName":"tjq","phoneNumber":"13981129786","headUrl":"splash","position":"业务员"}
         */

        private int PayCount;
        private int noPayCount;

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int payCount) {
            PayCount = payCount;
        }

        public int getNoPayCount() {
            return noPayCount;
        }

        public void setNoPayCount(int noPayCount) {
            this.noPayCount = noPayCount;
        }

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean implements Serializable {
            /**
             * relName : tjq
             * phoneNumber : 13981129786
             * headUrl : splash
             * position : 业务员
             */

            private String relName;
            private String phoneNumber;
            private String headUrl;
            private String position;

            public String getRelName() {
                return relName;
            }

            public void setRelName(String relName) {
                this.relName = relName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }
        }
    }
}
