package com.wongxd.carinsurance.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wxd1 on 2017/3/15.
 */


/**
 * 可以嵌套的viewpager
 */
public class WDoubleViewPager extends ViewPager {


    private boolean isLeftToRight = false;

    public WDoubleViewPager(Context context) {
        super(context);
    }

    public WDoubleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float initX = -1;
    float initY = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                initX = ev.getX();
                initY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (initX == -1) {
                    initX = ev.getX();
                    initY = ev.getY();
                }
                float x = ev.getX();
                float y = ev.getY();
                if ((Math.abs(initY - y) < Math.abs(initX - x)) && x - initX > 0) {
//                    向右滑动
                    isLeftToRight = true;
                } else {
                    isLeftToRight = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                initX = -1;
                initY = -1;
                isLeftToRight = false;
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

    /**
     * 嵌套的滑动冲突解决  外层ViewPager复写canScroll方法，这种处理同样适用于ScrollView等其他滑动控件。
     */
    @Override
    public boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {
//            int count = ((ViewPager) v).getChildCount();
//            int positon = ((ViewPager) v).getCurrentItem();
//            if (positon == 0 && isLeftToRight) return false;
//            if (positon == count - 1 && !isLeftToRight) return false;
            return true;

        }
        return super.canScroll(v, checkV, dx, x, y);
    }


}
