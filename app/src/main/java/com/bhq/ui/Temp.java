package com.bhq.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bhq.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.changepwd)
public class Temp extends Activity {

    @AfterViews
    void afterOncreate() {
        Toast.makeText(this, "go的发生的方式发生发生和社会生活od", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
    }


}
