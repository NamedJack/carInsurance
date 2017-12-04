package com.wongxd.carinsurance.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class TopListView extends ListView {
    public TopListView(Context context) {
        this(context, null);
    }

    public TopListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}