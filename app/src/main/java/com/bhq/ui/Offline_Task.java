package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ImageButton;

import com.bhq.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-19 下午2:34:29
 * @description :信息采集类
 */
@EActivity(R.layout.task)
public class Offline_Task extends Activity
{

	@ViewById
	ImageButton imgbtn_back;
	Fragment mContent = new Fragment();

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@AfterViews
	void afterOncreate()
	{
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		switchContent(mContent, new Offline_TaskFragment_());
	}

	public void switchContent(Fragment from, Fragment to)
	{
		if (mContent != to)
		{
			mContent = to;
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if (!to.isAdded())
			{ // 先判断是否被add过
				transaction.hide(from).add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else
			{
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

}
