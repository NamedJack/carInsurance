package com.wongxd.carinsurance.aty.personal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.soundcloud.android.crop.Crop;
import com.wongxd.carinsurance.App;
import com.wongxd.carinsurance.R;
import com.wongxd.carinsurance.aty.BaseSwipeActivity;
import com.wongxd.carinsurance.bean.personal.PersonalBean;
import com.wongxd.carinsurance.utils.FileUtil;
import com.wongxd.carinsurance.utils.ToastUtil;
import com.wongxd.carinsurance.utils.conf.UrlConfig;
import com.wongxd.carinsurance.utils.glide.GlideCircleTransform;
import com.wongxd.carinsurance.utils.glide.GlideLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Call;

public class UserInfoActivity extends BaseSwipeActivity {


    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_infor_img)
    ImageView userInforImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.right1)
    ImageView right1;
    @BindView(R.id.rl_user_name_update)
    RelativeLayout rlUserNameUpdate;
    @BindView(R.id.tv_positon)
    TextView tvPositon;
    @BindView(R.id.user_job)
    RelativeLayout userJob;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.right4)
    ImageView right4;
    @BindView(R.id.rv_change_pwd)
    RelativeLayout rvChangePwd;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;

    private AppCompatActivity thisActivity;
    private Context mContext;
    private PersonalBean personalBean;
    private String tempPicPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        thisActivity = this;
        mContext = getApplicationContext();
        personalBean = (PersonalBean) getIntent().getSerializableExtra("person");
        if (null != personalBean) {
            tvName.setText(personalBean.getData().getUser().getRelName());
//            GlideLoader.LoadAsCircleImage(mContext,
//                    UrlConfig.Host_URL + personalBean.getData().getUser().getHeadUrl()
//                    , ivPic);

            Glide.with(getApplicationContext())
                    .load(personalBean.getData().getUser().getHeadUrl())
                    .transform(new GlideCircleTransform(getApplicationContext()))
                    .placeholder(R.drawable.user_img)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
                    .into(ivPic);

            tvPhone.setText(personalBean.getData().getUser().getPhoneNumber());
            tvPositon.setText(personalBean.getData().getUser().getPosition());

        } else ToastUtil.CustomToast(mContext, "数据初始失败!请后退，再次进入。");


    }


    @OnClick({R.id.iv_return, R.id.rl_pic, R.id.rl_phone, R.id.rv_change_pwd, R.id.rl_user_name_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                thisActivity.finish();
                break;
            case R.id.rl_pic:
                pickImage();
                break;
            case R.id.rl_user_name_update:
                startActivity(new Intent(thisActivity, UpdateNameActivity.class));
                break;
            case R.id.rl_phone:
                break;
            case R.id.rv_change_pwd:
                startActivity(new Intent(thisActivity, UpdatePasswordActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.IS_NEED_REBOOT) thisActivity.finish();
        if (null != App.UserName) tvName.setText(App.UserName);
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            uploadPicture(file.getAbsolutePath());
            return true;
        }
    });

    ProgressDialog pb;


    /**
     * 上传图片
     */
    private void uploadPicture(final String picPath) {

        if (null != pb) pb.dismiss();

        final PostFormBuilder builder = OkHttpUtils.post().url(UrlConfig.UpdateUserImg_URL)
                .addParams("token", App.token)
                .addFile("img", picPath, file);
        final ProgressDialog progressDialog = new ProgressDialog(thisActivity);
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
                FileUtil.delete(file);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                int p = (int) (progress * 100);
                progressDialog.setProgress(p);
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.e(response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") != 100) {
                        ToastUtil.CustomToast(mContext, "出错  " + jsonObject.optString("msg"));
                    } else {
                        GlideLoader.LoadAsCircleImage(mContext, picPath, ivPic);
                        App.uploadNewHeader = true;
                        ToastUtil.CustomToast(mContext, "msg:  " + jsonObject.optString("msg"));
                        thisActivity.finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    FileUtil.delete(file);

                }

            }
        });


    }


    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    File file;

    private void zipImg(final String picPath) {
        tempPicPath = picPath;
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Wongxd");
        if (!dir.exists()) {
            file.mkdir();
        }
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Wongxd"
                + File.separator
                + "userImg.png");
        if (file.exists()) file.delete();
        File srcFile = new File(picPath);
        Uri inputUri = Uri.fromFile(srcFile);
        Uri outputUri = Uri.fromFile(file);
        Crop.of(inputUri, outputUri).asSquare().start(thisActivity);
    }

    private ArrayList<String> mSelectPath;

    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
                    zipImg(mSelectPath.get(0));
                }
            }
        }

        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            handler.sendEmptyMessage(1);
        }

    }

}

