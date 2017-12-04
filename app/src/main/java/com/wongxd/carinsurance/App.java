package com.wongxd.carinsurance;

import android.app.Application;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.wongxd.carinsurance.utils.SPUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wxd1 on 2017/3/14.
 */

public class App extends Application {
    //个人信息相关
    public static boolean IS_NEED_REBOOT = false;
    public static String UserName = null;
    public static boolean uploadNewHeader = false; //true 上传了新的头像

    //与照片上传相关
    public static boolean Should_PhotoUpload_Refresh = false;


    public static List<Map<String, String>> dateList = null;//查询时间 数据分析 和 保单管理用
    public static int requestCount = 1; //保险比价接口的请求次数
    public static int buyStatu = 0;//保险购买的状态   1  为成功


    public static boolean orderStatusChanged = false; // true 保单 从 待支付 变为 已经完成


    private SPUtil spUtilInstance;

    public static int RECYCLEVIEW_LOADMORE_STYLE = ProgressStyle.SquareSpin;
    public static int ORDER_VIEWPAGER_SELECTED_POSTION = -1;//保单管理的viewpager选中项目

    public static String token = " ";

    @Override
    public void onCreate() {
        super.onCreate();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...

        Logger.init().logLevel(LogLevel.FULL);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(90 * 1000L, TimeUnit.MILLISECONDS)
                .readTimeout(90 * 1000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

        //初始化Sputil
        spUtilInstance = SPUtil.getInstance(this);
    }


}
