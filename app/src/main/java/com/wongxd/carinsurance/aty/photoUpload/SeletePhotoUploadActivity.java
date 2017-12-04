package com.wongxd.carinsurance.aty.photoUpload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.photoUpload.PhotoDetailBean;
import com.wongxd.carinsurance.utils.FileUtil;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.bitMap.BitMapUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideLoader;
import com.wongxd.carinsurance.utils.net.WNetUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Call;

public class SeletePhotoUploadActivity extends BaseSwipeActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;
    @BindView(R.id.text33)
    TextView text33;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.login_lin3)
    RelativeLayout loginLin3;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.textone)
    TextView textone;
    @BindView(R.id.iv1_x)
    ImageView iv1X;
    @BindView(R.id.iv2_x)
    ImageView iv2X;
    @BindView(R.id.iv3_x)
    ImageView iv3X;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.login_lin2)
    RelativeLayout loginLin2;
    @BindView(R.id.texttwo)
    TextView texttwo;
    @BindView(R.id.iv4_x)
    ImageView iv4X;
    @BindView(R.id.iv5_x)
    ImageView iv5X;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.iv1_mark)
    ImageView iv1Mark;
    @BindView(R.id.iv2_mark)
    ImageView iv2Mark;
    @BindView(R.id.iv3_mark)
    ImageView iv3Mark;
    @BindView(R.id.iv4_mark)
    ImageView iv4Mark;
    @BindView(R.id.iv5_mark)
    ImageView iv5Mark;
    private AppCompatActivity thisActivity;
    private Context mContext;
    private int clickIv = -1;
    private String policyId = "";
    private boolean isUploadAgin;
    private List<ImageView> imageViews1;
    private List<ImageView> imageViews1_marks;
    private List<ImageView> imageViews2;
    private List<ImageView> imageViews2_marks;
    private ProgressDialog pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selete_photo_upload);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = this.getApplicationContext();
        policyId = getIntent().getStringExtra("policyId");
        isUploadAgin = getIntent().getBooleanExtra("isUploadAgin", false);

        imageViews1 = new ArrayList<>();
        imageViews1.add(iv1);
        imageViews1.add(iv2);
        imageViews1.add(iv3);


        imageViews2 = new ArrayList<>();
        imageViews2.add(iv4);
        imageViews2.add(iv5);


        if (isUploadAgin) {
            btn.setText("重新上传");
            getPhotoList();
            textone.setText("行驶证照片(请重新上传)");
            texttwo.setText("驾驶证照片(请重新上传)");
        }

    }

    //获取服务器图片
    private void getPhotoList() {


        WNetUtil.StringCallBack(OkHttpUtils.post().url(UrlConfig.QueryImgByPolicyId_URL)
                        .addParams("token", App.token)
                        .addParams("policyId", policyId)
                , UrlConfig.QueryImgByPolicyId_URL, thisActivity,
                "获取照片中", new WNetUtil.WNetStringCallback() {
                    @Override
                    public void success(String response, int id) {
                        Gson gson = new Gson();
                        PhotoDetailBean photoDetailBean = gson.fromJson(response, PhotoDetailBean.class);
                        if (photoDetailBean.getCode() == 100) {
                            List<PhotoDetailBean.DataBean.ListVehiclesBean> listVehicles = photoDetailBean.getData().getListVehicles();
                            List<PhotoDetailBean.DataBean.ListDriverBean> listDrivers = photoDetailBean.getData().getListDriver();


//                            行驶证审核状态 0.待上传 1.待审核 2.审核通过 3.审核失败
                            for (int i = 0; i < listVehicles.size(); i++) {

                                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + listVehicles.get(i).getVehiclesImg(),
                                        imageViews1.get(i));

                                if (i == 0)
                                    iv1Mark.setVisibility(View.VISIBLE);
                                if (i == 1)
                                    iv2Mark.setVisibility(View.VISIBLE);
                                if (i == 2)
                                    iv3Mark.setVisibility(View.VISIBLE);

                            }


//                            驾驶证审核状态 0.待上传 1.待审核 2.审核通过 3.审核失败
                            for (int i = 0; i < listDrivers.size(); i++) {

                                GlideLoader.LoadAsRoundImage(mContext, UrlConfig.Host_URL + listDrivers.get(i).getDriverImg(),
                                        imageViews2.get(i));
                                if (i == 0)
                                    iv4Mark.setVisibility(View.VISIBLE);
                                if (i == 1)
                                    iv5Mark.setVisibility(View.VISIBLE);
                            }


                        } else ToastUtil.CustomToast(mContext, photoDetailBean.getMsg());
                    }

                    @Override
                    public void error(Call call, Exception e, int id) {
                        ToastUtil.CustomToast(mContext, "获取请求出错");
                    }
                });
    }

    @OnClick({R.id.iv_return, R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv1_x, R.id.iv2_x, R.id.iv3_x, R.id.iv4_x, R.id.iv5_x})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.iv1:
                clickIv = 1;
                pickImage();
                break;
            case R.id.iv2:
                clickIv = 2;
                pickImage();
                break;
            case R.id.iv3:
                clickIv = 3;
                pickImage();
                break;
            case R.id.iv4:
                clickIv = 4;
                pickImage();
                break;
            case R.id.iv5:
                clickIv = 5;
                pickImage();
                break;
            case R.id.iv1_x:
                if (null != picPaths.get("iv1")) {
                    picPaths.remove("iv1");
                    GlideLoader.LoadAsRoundImage(mContext, R.drawable.icon_addpic_focused, iv1);
                    iv1X.setVisibility(View.GONE);
                }
                break;
            case R.id.iv2_x:
                if (null != picPaths.get("iv2")) {
                    picPaths.remove("iv2");
                    GlideLoader.LoadAsRoundImage(mContext, R.drawable.icon_addpic_focused, iv2);
                    iv2X.setVisibility(View.GONE);
                }
                break;
            case R.id.iv3_x:
                if (null != picPaths.get("iv3")) {
                    picPaths.remove("iv3");
                    GlideLoader.LoadAsRoundImage(mContext, R.drawable.icon_addpic_focused, iv3);
                    iv3X.setVisibility(View.GONE);
                }
                break;
            case R.id.iv4_x:
                if (null != picPaths.get("iv4")) {
                    picPaths.remove("iv4");
                    GlideLoader.LoadAsRoundImage(mContext, R.drawable.icon_addpic_focused, iv4);
                    iv4X.setVisibility(View.GONE);
                }
                break;
            case R.id.iv5_x:
                if (null != picPaths.get("iv5")) {
                    picPaths.remove("iv5");
                    GlideLoader.LoadAsRoundImage(mContext, R.drawable.icon_addpic_focused, iv5);
                    iv5X.setVisibility(View.GONE);
                }
                break;
        }


    }


    private static final int REQUEST_IMAGE = 2;

    ProgressDialog progressDialog = null;

    @OnClick(R.id.btn)
    public void onClick() {
        if (picPaths.isEmpty()) {
            ToastUtil.CustomToast(mContext, "请选择图片上传");
            return;
        }

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission(new CheckPermListener() {
                @Override
                public void superPermission() {
                    zipImag();
                }
            }, "需要读写存储卡的权限保存压缩的图片", Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else zipImag();
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1://压缩图片完成
                    if (null != pb) pb.dismiss();

                    uploadImg();

                    break;
            }
            return true;
        }
    });


    //上传图片的builder
    PostFormBuilder builder;
    List<File> fileList = new ArrayList<>();//压缩图片用的file

    private void zipImag() {

        builder = OkHttpUtils.post().url(UrlConfig.UploadImg_URL)
                .addHeader("Content-Type", "multipart/form-data")
                .addParams("policyId", policyId + "")
                .addParams("token", App.token);


        pb = new ProgressDialog(thisActivity);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setMessage("    打包上传图片中,请稍后");
        pb.setCanceledOnTouchOutside(false);
        pb.setCancelable(false);
        pb.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 4; i++) {
                    String key = "";
                    switch (i) {
                        case 1:
                            key = "iv1";
                            break;
                        case 2:
                            key = "iv2";
                            break;
                        case 3:
                            key = "iv3";
                            break;
                    }
                    if (null != picPaths.get(key)) {
                        Bitmap bitmap = BitMapUtil.getimage(picPaths.get(key));

                        try {
                            FileUtil.saveMyBitmap(bitmap, "vehiclesImgs_0" + i);
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Wongxd" + File.separator
                                    + "vehiclesImgs_0" + i + ".png");
                            fileList.add(file);
                            builder.addFile("vehiclesImgs", "vehiclesImgs_0" + i + ".png", file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }


                for (int i = 4; i < 6; i++) {
                    String key = "";
                    switch (i) {
                        case 4:
                            key = "iv4";
                            break;
                        case 5:
                            key = "iv5";
                            break;
                    }
                    if (null != picPaths.get(key)) {
                        int j = 1;
                        if (i == 5) j = 2;
                        Bitmap bitmap = BitMapUtil.getimage(picPaths.get(key));
                        try {
                            FileUtil.saveMyBitmap(bitmap, "driverImgs_0" + j);
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "Wongxd" + File.separator + "driverImgs_0" + j + ".png");
                            fileList.add(file);
                            builder.addFile("driverImgs", "driverImgs_0" + i + ".png", file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                handler.sendEmptyMessage(1);

            }
        }).start();
    }

    private void uploadImg() {

        progressDialog = new ProgressDialog(thisActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setTitle("上传进度");
        progressDialog.show();


        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
                ToastUtil.CustomToast(mContext, "上传请求失败");
                for (File f : fileList) {
                    FileUtil.delete(f);
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                int p = (int) (progress * 100);
                progressDialog.setProgress(p);
            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") != 100) {
                        ToastUtil.CustomToast(mContext, "出错  " + jsonObject.optString("msg"));

                    } else {
                        ToastUtil.CustomToast(mContext, "msg:  " + jsonObject.optString("msg"));
                        App.Should_PhotoUpload_Refresh = true;
                        thisActivity.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    for (File f : fileList) {
                        FileUtil.delete(f);
                    }

                }

            }
        });


    }

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private ArrayList<String> mSelectPath;

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            int maxNum = 1;
            MultiImageSelector selector = MultiImageSelector.create(thisActivity);
            selector.showCamera(true);
            selector.count(maxNum);
            selector.single();
            selector.origin(mSelectPath);
            selector.start(thisActivity, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(thisActivity, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) return;
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                    sb.append("\n");
                }
                if (mSelectPath.size() == 1) {
                    showSeletedPic(mSelectPath.get(0));
                }
            }
        }

    }


    Map<String, String> picPaths = new HashMap();//上传图片的路径

    private void showSeletedPic(String picPath) {
        if (clickIv == -1) return;
        ImageView iv = null;
        switch (clickIv) {
            case 1:
                iv = iv1;
                picPaths.put("iv1", picPath);
                iv1X.setVisibility(View.VISIBLE);
                iv1Mark.setVisibility(View.GONE);
                break;
            case 2:
                iv = iv2;
                picPaths.put("iv2", picPath);
                iv2X.setVisibility(View.VISIBLE);
                iv2Mark.setVisibility(View.GONE);
                break;
            case 3:
                iv = iv3;
                picPaths.put("iv3", picPath);
                iv3X.setVisibility(View.VISIBLE);
                iv3Mark.setVisibility(View.GONE);
                break;
            case 4:
                iv = iv4;
                picPaths.put("iv4", picPath);
                iv4X.setVisibility(View.VISIBLE);
                iv4Mark.setVisibility(View.GONE);
                break;
            case 5:
                iv = iv5;
                picPaths.put("iv5", picPath);
                iv5X.setVisibility(View.VISIBLE);
                iv5Mark.setVisibility(View.GONE);
                break;
        }
        clickIv = -1;
        GlideLoader.LoadAsRoundImage(mContext, picPath, iv);
    }


}
