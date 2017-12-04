package com.wongxd.carinsurance.base;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wongxd.carinsurance.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wxd1 on 2017/3/17.
 */

public class PopwindowMenu {
    private static PopupWindow popLeft;
    @SuppressLint("StaticFieldLeak")
    private static View layoutLeft;
    @SuppressLint("StaticFieldLeak")
    private static ListView menulistLeft;
    private static List<Map<String, String>> listLeft;


    public static void datePopWindow(AppCompatActivity activity, final TextView tvDate, List<Map<String, String>> list, final PopMenuCallBack callBack) {
        listLeft = list;
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = activity.getLayoutInflater().inflate(R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            SimpleAdapter listAdapter = new SimpleAdapter(
                    activity, listLeft,
                    R.layout.pop_menuitem,
                    new String[]{"name"},
                    new int[]{R.id.tv_name});
            menulistLeft.setAdapter(listAdapter);

            // 点击listview中item的处理
            menulistLeft.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                // 改变顶部对应TextView值
                String strItem = listLeft.get(arg2).get("name");
                String id = listLeft.get(arg2).get("id");
                tvDate.setText(strItem);
                //回调
                if (null != callBack) {
                    callBack.selectedItemChange(strItem, arg2, id);
                }
                // 隐藏弹出窗口
                if (popLeft != null && popLeft.isShowing()) {
                    popLeft.dismiss();
                }
            });

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟tvLeft一样
            popLeft = new PopupWindow(layoutLeft, (int) (tvDate.getWidth() * 1.8f),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            Drawable d = new ColorDrawable(activity.getResources().getColor(R.color.white));
            popLeft.setBackgroundDrawable(d);
            popLeft.setAnimationStyle(R.style.PopupAnimation);
            popLeft.update();
            popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            // 设置pop被键盘顶上去，而不是遮挡
            popLeft.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popLeft.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popLeft.setTouchable(true); // 设置popupwindow可点击
            popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popLeft.setFocusable(true); // 获取焦点S

            int topBarHeight = tvDate.getBottom();
            popLeft.showAsDropDown(tvDate, 0,
                    (topBarHeight - tvDate.getHeight()) / 2);

            popLeft.setTouchInterceptor((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popLeft.dismiss();
                    return true;
                }
                return false;
            });
        }
    }


    public interface PopMenuCallBack {
         void selectedItemChange(String str, int position, String id);
    }


    public static void TextviewAnimitor(TextView v) {

        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.7f, 1.1f,
                1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0.7f, 1.1f,
                1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0.7f, 1.1f,
                1f);
        ObjectAnimator.ofPropertyValuesHolder(v, pvhAlpha, pvhY, pvhZ).setDuration(500).start();

        ObjectAnimator animator = ObjectAnimator.ofInt(v, "TextColor", R.color.black, R.color.red, R.color.gray);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(500);
        animator.start();

    }
}
