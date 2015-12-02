package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.bhq.R;

import org.androidannotations.annotations.EActivity;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:44:17
 * @description :
 */
@SuppressLint("NewApi")
@EActivity(R.layout.addxmcbc)
public class AddXMCBC extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
	};
}
