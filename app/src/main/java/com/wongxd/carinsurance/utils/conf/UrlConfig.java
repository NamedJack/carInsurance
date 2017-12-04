package com.wongxd.carinsurance.utils.conf;

/**
 * Created by wxd1 on 2017/3/15.
 */

public class UrlConfig {
            public static final String Host_URL = "http://47.93.28.63:8111/";
//    public static final String Host_URL = "http://192.168.100.220:8099/HelloSSM/";
    //登录页面
    public static final String Login_URL = Host_URL + "userLogin";

    //个人
    public static final String GetPersonalInfo_URL = Host_URL + "user/getPersonalInfo";
    public static final String UpdateUserPassword_URL = Host_URL + "user/updateUserPassword";
    public static final String UpdateUserName_URL = Host_URL + "user/updateUserName";
    public static final String CustomManager_URL = Host_URL + "user/getClient";
    public static final String CustomDetailInfo_URL = Host_URL + "user/getPolicyInfo";

    public static final String GetVesionNo_URL = Host_URL + "user/getVesionNo";
    public static final String UpdateUserImg_URL = Host_URL + "user/updateUserImg";

    public static final String GetData_URL = Host_URL + "user/getData";


    //照片上传
    public static final String UploadImg_URL = Host_URL + "img/uploadImg";
    public static final String QueryImgByPolicyId_URL = Host_URL + "img/queryImgByPolicyId";
    public static final String QueryPolicyByUpImgFlag_URL = Host_URL + "img/queryPolicyByUpImgFlag";


    //车险算单
    public static final String GetBeforeCar_URL = Host_URL + "user/getBeforeCar";
    public static final String GetBrandByVehicleNo_URL = Host_URL + "user/getBrand";
    public static final String SaveCarAndPeople_URL = Host_URL + "user/saveCarAndPeople";
    public static final String GetPolicyInfoByCarNo_URL = Host_URL + "user/getPolicyInfoByCarNo";

    public static final String QueryInsuranceKind_URL = Host_URL + "user/queryInsuranceKind";

    public static final String SubmitInsuranceKind_URL = Host_URL + "queryInsuranceKinds";

    public static final String PolicyDetail_URL = Host_URL + "user/getMyPolicy";

    public static final String ConfirmBuy_URL = Host_URL + "user/confirmBuy";


    //订单管理
    public static final String QueryPolicyInfoByPayFlag_URL = Host_URL + "policy/queryPolicyInfoByPayFlag";
    public static final String QueryDateTimeByType_URL = Host_URL + "policy/queryDateTimeByType";
    public static final String QueryPolicyInfoByPayFlagTpye_URL = Host_URL + "policy/queryPolicyInfoByPayFlagTpye";
    public static final String DelPolicyByPolicyNo_URL = Host_URL + "policy/delPolicyByPolicyNo";


}
