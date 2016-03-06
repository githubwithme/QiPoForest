package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

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
	Offline_TaskFragment offline_taskFragment;
	Offline_TaskFragment_Complete offline_taskFragment_complete;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	TextView tv_rw_now;
	@ViewById
	TextView tv_rw_complete;
	Fragment mContent = new Fragment();

	@Click
	void imgbtn_back()
	{
		finish();
	}
	@Click
	void tv_rw_complete()
	{
		setBackground(1);
		switchContent(mContent, offline_taskFragment_complete);
	}
	@Click
	void tv_rw_now()
	{
		setBackground(0);
		switchContent(mContent, offline_taskFragment);
	}

	@AfterViews
	void afterOncreate()
	{
		setBackground(0);
		switchContent(mContent, offline_taskFragment);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		offline_taskFragment=new Offline_TaskFragment_();
		offline_taskFragment_complete=new Offline_TaskFragment_Complete_();
	}
	private void setBackground(int pos)
	{
		tv_rw_now.setSelected(false);
		tv_rw_complete.setSelected(false);

		tv_rw_now.setBackgroundResource(R.color.white);
		tv_rw_complete.setBackgroundResource(R.color.white);

		tv_rw_now.setTextColor(getResources().getColor(R.color.menu_textcolor));
		tv_rw_complete.setTextColor(getResources().getColor(R.color.menu_textcolor));
		switch (pos)
		{
			case 0:
				tv_rw_now.setSelected(false);
				tv_rw_now.setTextColor(getResources().getColor(R.color.bg_blue));
				tv_rw_now.setBackgroundResource(R.drawable.red_bottom);
				break;
			case 1:
				tv_rw_complete.setSelected(false);
				tv_rw_complete.setTextColor(getResources().getColor(R.color.bg_blue));
				tv_rw_complete.setBackgroundResource(R.drawable.red_bottom);
				break;
		}

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
