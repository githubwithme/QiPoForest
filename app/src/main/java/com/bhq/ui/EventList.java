package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.bhq.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author :sima
 * @version :
 * @createTime：2015-8-9 下午10:27:08
 * @description :
 */
@EActivity(R.layout.eventlist)
public class EventList extends Activity
{
	@ViewById
	ProgressBar main_head_progress;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	ImageButton imgbtn_add;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void imgbtn_add()
	{
		Intent intent = new Intent(this, AddEvent_.class);
		startActivity(intent);
	}

	Fragment mContent = new Fragment();
	PopupWindow popupWindow;
	View popupWindowView;
	Bundle savedInstanceState;
	EventListFragment plantListFragment_;

	@AfterViews
	void afterOncreate()
	{
		if (savedInstanceState == null)
		{
			switchContent(mContent, plantListFragment_);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		plantListFragment_ = new EventListFragment_();
		this.savedInstanceState = savedInstanceState;
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
