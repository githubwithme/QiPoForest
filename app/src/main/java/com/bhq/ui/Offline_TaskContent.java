package com.bhq.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.Dictionary;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.SysUserInfo;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @since 2015
 * @author hc_mj {@code}
 * @category action
 */
@EActivity(R.layout.taskcontent)
public class Offline_TaskContent extends Activity
{

	String dialog_content;
	View dialog_layout;
	@ViewById
	LinearLayout ll_CYR;
	@ViewById
	Button btn_check_yqsq;
	@ViewById
	Button btn_sqyq_bpq;
	@ViewById
	Button btn_wc;
	@ViewById
	TextView tv_CYR;
	@ViewById
	TextView tv_WRKSSJ;
	@ViewById
	TextView tv_RWMC;
	@ViewById
	TextView tv_WRJZSJ;
	@ViewById
	TextView tv_WRTXSJ;
	@ViewById
	TextView tv_RWMS;
	@ViewById
	TextView tv_ZCRXM;
	@ViewById
	TextView tv_ywcry;
	@ViewById
	TextView tv_ZYD;
	dt_manager_offline dt_manager_offline;
	LayoutInflater layoutInflater;
	LinearLayout ll_contain;
	String RWID = "";
	RW_RW rw_RW;
	MyDialog myDialog;
	RW_CYR zrr = new RW_CYR();
	String RWSFJS = "0";
	String HYSFQX = "0";
	String ACTION;
	// List<RW_CYR> list_RW_CYR = new ArrayList<RW_CYR>();
	List<RW_CYR> list_RW_WCRWRY;
	int pos_user;
	List<SysUserInfo> list_SysUserInfo = new ArrayList<SysUserInfo>();

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void btn_wc()
	{
		dialog_content = "确定标记这个任务完成吗？";
		dialog_layout = (LinearLayout) ((Activity) Offline_TaskContent.this).getLayoutInflater().inflate(R.layout.customdialog_callback, null);
		myDialog = new MyDialog(Offline_TaskContent.this, R.style.MyDialog, dialog_layout, "任务完成", dialog_content, "确定", "取消", new CustomDialogListener()
		{
			@Override
			public void OnClick(View view)
			{
				switch (view.getId())
				{
				case R.id.btn_sure:
					myDialog.dismiss();
					setRenWuComplete();
					break;
				case R.id.btn_cancle:
					myDialog.dismiss();
					break;
				}
			}
		});
		myDialog.show();
	}

	@AfterViews
	void afterOncreate()
	{
		RWID = String.valueOf(rw_RW.getRWID());
		if (rw_RW.getZRR().equals(dt_manager_offline.getid()))
		{
			btn_wc.setVisibility(View.GONE);
		}
		getRW_CYR();
		getYWCRWRY();
		if (rw_RW != null)
		{
			showData(rw_RW);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(Offline_TaskContent.this, dt_manager_offline.class);
		rw_RW = (RW_RW) getIntent().getParcelableExtra("bean");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	private void getYWCRWRY()
	{
		List<RW_CYR> listNewData = SqliteDb.getYWCRWRY(Offline_TaskContent.this, RW_CYR.class, RWID);
		if (listNewData == null)
		{
			listNewData = new ArrayList<RW_CYR>();
		} else
		{
			String ryxm = new String();
			for (int i = 0; i < listNewData.size(); i++)
			{
				ryxm = ryxm + listNewData.get(i).getRYXM() + ";";
			}
			tv_ywcry.setText(ryxm);
		}

	}

	private void showData(RW_RW rw_RW)
	{
		Dictionary dictionary= (Dictionary) SqliteDb.getZYDbyID(Offline_TaskContent.this, Dictionary.class, rw_RW.getZYD());
		tv_WRTXSJ.setText(utils.DateString2Day(rw_RW.getWRTXSJ()));
		tv_WRJZSJ.setText(utils.DateString2Day(rw_RW.getWRJZSJ()));
		tv_WRKSSJ.setText(utils.DateString2Day(rw_RW.getWRKSSJ()));
		tv_RWMC.setText(rw_RW.getRWMC());
		tv_ZCRXM.setText(rw_RW.getZCRXM());
		tv_ZYD.setText(dictionary.getNAME());
		tv_RWMS.setText(rw_RW.getRWMS());
	}

	private void getRW_CYR()
	{
		List<RW_CYR> listNewData = SqliteDb.getRW_CYRList(Offline_TaskContent.this, RW_CYR.class, RWID);
		if (listNewData == null)
		{
			listNewData = new ArrayList<RW_CYR>();
		} else
		{
			String ryxm = new String();
			for (int i = 0; i < listNewData.size(); i++)
			{
				if (listNewData.get(i).getSFWC()!=null && listNewData.get(i).getSFWC().equals("true") && listNewData.get(i).getRYID().equals(dt_manager_offline.getid()))
				{
					btn_wc.setText("已完成");
					btn_wc.setClickable(false);
				}
				ryxm = ryxm + listNewData.get(i).getRYXM() + ";";
			}
			tv_CYR.setText(ryxm);
		}
	}

	private void setRenWuComplete()
	{
		RW_CYR rw_CYR = new RW_CYR();
		rw_CYR.setRWID(RWID);
		rw_CYR.setRYID(dt_manager_offline.getid());
		rw_CYR.setSFWC("true");
		rw_CYR.setWCSJ(utils.getTime());
		rw_CYR.setXGSJ(utils.getTime());
		rw_CYR.setIsUpload("0");
		boolean issuccess = SqliteDb.setRenWuComplete(Offline_TaskContent.this, rw_CYR, RWID, dt_manager_offline.getid());
		if (issuccess)
		{
			Toast.makeText(Offline_TaskContent.this, "恭喜您完成了任务", Toast.LENGTH_SHORT).show();
			finish();
		} else
		{
			Toast.makeText(Offline_TaskContent.this, "标记任务完成失败", Toast.LENGTH_SHORT).show();
		}
	}
}
