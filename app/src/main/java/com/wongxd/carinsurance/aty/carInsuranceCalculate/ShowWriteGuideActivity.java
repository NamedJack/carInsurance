package com.wongxd.carinsurance.aty.carInsuranceCalculate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wongxd.carinsurance.R;

public class ShowWriteGuideActivity extends Activity implements View.OnClickListener {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_write_guide);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        ShowWriteGuideActivity.this.finish();
    }

}