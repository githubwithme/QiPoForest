package com.bhq.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bhq.R;
import com.bhq.common.BitmapHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.showphoto)
public class Offline_ShowPhoto extends Activity
{

	String url;
	@ViewById
	ImageView img;

	@AfterViews
	void img()
	{
		BitmapHelper.loadImage(this, img, url);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		url = getIntent().getStringExtra("url");
	}
}
