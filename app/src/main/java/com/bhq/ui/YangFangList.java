package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

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
@EActivity(R.layout.yangfanglist)
public class YangFangList extends Activity implements OnClickListener
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
		showPop();
	}

	Fragment mContent = new Fragment();
	PopupWindow popupWindow;
	View popupWindowView;
	Bundle savedInstanceState;
	YangFangListFragment_ yangFangListFragment_;

	@AfterViews
	void afterOncreate()
	{
		if (savedInstanceState == null)
		{
			switchContent(mContent, yangFangListFragment_);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		yangFangListFragment_ = new YangFangListFragment_();
		this.savedInstanceState = savedInstanceState;
	}

	private void showPop()
	{
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popupWindowView = layoutInflater.inflate(R.layout.popup_addyf, null);// 外层
		popupWindowView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if ((keyCode == KeyEvent.KEYCODE_MENU) && (popupWindow.isShowing()))
				{
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		popupWindowView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
				}
				return false;
			}
		});
		popupWindow = new PopupWindow(popupWindowView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.showAsDropDown(imgbtn_add, 0, 0);
		popupWindow.setOutsideTouchable(true);
		popupWindowView.findViewById(R.id.btn_qmc).setOnClickListener(this);
		popupWindowView.findViewById(R.id.btn_xmcbc).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.btn_qmc:
			intent = new Intent(YangFangList.this, AddQMC_.class);
			popupWindow.dismiss();
			break;
		case R.id.btn_xmcbc:
			intent = new Intent(YangFangList.this, AddXMCBC_.class);
			popupWindow.dismiss();
			break;
		default:
			break;
		}
		startActivity(intent);
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
