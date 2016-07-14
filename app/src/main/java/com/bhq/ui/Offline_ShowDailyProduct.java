package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.common.SqliteDb;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:33:24
 * @description :展示动物类
 */
@SuppressLint("NewApi")
@EActivity(R.layout.offline_showdailyproduct)
public class Offline_ShowDailyProduct extends Activity
{
	MyDialog myDialog;
	BHQ_XHSJCJ bhq_XHSJCJ;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
FrameLayout img_container;	@ViewById
ImageView img_tip;
	@ViewById
	TextView tv_type;
	@ViewById
	TextView tv_bz;

	List<FJ_SCFJ> list_fj_scfj = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		bhq_XHSJCJ = (BHQ_XHSJCJ) getIntent().getParcelableExtra("bean");
	}

	@AfterViews
	void afterOncreate()
	{
		show(bhq_XHSJCJ);
		getFJ_SCFJ(bhq_XHSJCJ.getCJID());
	}

	@Click
	void tv_delete()
	{
		showDialog();
	}

	@Click
	void tv_edit()
	{
		Intent intent = new Intent(Offline_ShowDailyProduct.this, Offline_EditCJXX_.class);
		intent.putExtra("bean", bhq_XHSJCJ);
		intent.putParcelableArrayListExtra("beanlist", (ArrayList<? extends Parcelable>) list_fj_scfj);
		startActivity(intent);
		finish();
	}

	@Click
	void imgbtn_back()
	{
		finish();
	}

	private void show(BHQ_XHSJCJ bhq_XHSJCJ)
	{
		tv_type.setText( bhq_XHSJCJ.getZYXLMC());
		tv_bz.setText(bhq_XHSJCJ.getBDZYBZ());
	}

	private void setImage(List<FJ_SCFJ> list_fj_scfj)
	{
		Offline_PictureScrollFragment offline_PictureScrollFragment = new Offline_PictureScrollFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("imgurl", (ArrayList<? extends Parcelable>) list_fj_scfj);
		offline_PictureScrollFragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.img_container, offline_PictureScrollFragment).commit();
	}

	private void getFJ_SCFJ(String glid)
	{
		list_fj_scfj = SqliteDb.getFJ_SCFJList(Offline_ShowDailyProduct.this, FJ_SCFJ.class, glid, "1");
		if (list_fj_scfj != null)
		{
			setImage(list_fj_scfj);
		} else
		{
			img_container.setVisibility(View.GONE);
			img_tip.setVisibility(View.VISIBLE);
		}
	}

	private void deleteCJXX()
	{
		bhq_XHSJCJ.setIsUpload("0");
		bhq_XHSJCJ.setSFSC("1");
		boolean issuccess = SqliteDb.deleteCJXX(Offline_ShowDailyProduct.this, bhq_XHSJCJ);
		if (issuccess)
		{
			Toast.makeText(Offline_ShowDailyProduct.this, "删除成功！", Toast.LENGTH_SHORT).show();
			finish();
		} else
		{
			Toast.makeText(Offline_ShowDailyProduct.this, "删除失败！", Toast.LENGTH_SHORT).show();
		}
	}

	public void showDialog()
	{
		View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
		myDialog = new MyDialog(Offline_ShowDailyProduct.this, R.style.MyDialog, dialog_layout, "删除采集信息", "确定删除该采集信息?", "确定", "取消", new CustomDialogListener()
		{
			@Override
			public void OnClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btn_sure:
					deleteCJXX();
					break;
				case R.id.btn_cancle:
					myDialog.dismiss();
					break;
				}
			}
		});
		myDialog.show();
	}
}
