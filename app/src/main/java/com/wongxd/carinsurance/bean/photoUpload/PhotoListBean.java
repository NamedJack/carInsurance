package com.wongxd.carinsurance.bean.photoUpload;

import java.util.List;

/**
 * Created by wxd1 on 2017/3/22.
 */

public class PhotoListBean {

    /**
     * msg : 获取成功！
     * code : 100
     * data : [{"policyCarNo":"鲁AG8A85","policyNo":"66220170502172633","id":23,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170502173310","id":26,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170502173411","id":27,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170502173645","id":28,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170502174624","id":31,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170502183118","id":32,"policyPeople":"杜修文"},{"policyCarNo":"鲁AG8A85","policyNo":"66220170503134118","id":33,"policyPeople":"杜修文"},{"policyCarNo":"鲁AJ909D","policyNo":"21220170503144849","id":34,"policyPeople":"邓涵"}]
     * pageNo : 1
     * totlePageNo : 1
     */

    private String msg;
    private int code;
    private int pageNo;
    private int totlePageNo;
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotlePageNo() {
        return totlePageNo;
    }

    public void setTotlePageNo(int totlePageNo) {
        this.totlePageNo = totlePageNo;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * policyCarNo : 鲁AG8A85
         * policyNo : 66220170502172633
         * id : 23
         * policyPeople : 杜修文
         */

        private String policyCarNo;
        private String policyNo;
        private int id;
        private String policyPeople;

        public String getPolicyCarNo() {
            return policyCarNo;
        }

        public void setPolicyCarNo(String policyCarNo) {
            this.policyCarNo = policyCarNo;
        }

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
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
