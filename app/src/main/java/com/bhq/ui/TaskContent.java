package com.bhq.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.SysUserInfo;
import com.bhq.bean.dt_manager;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @since 2015
 * @author hc_mj {@code}
 * @category action
 */
@EActivity(R.layout.taskcontent)
public class TaskContent extends Activity
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
	dt_manager dt_manager;
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
		dialog_layout = (LinearLayout) ((Activity) TaskContent.this).getLayoutInflater().inflate(R.layout.customdialog_callback, null);
		myDialog = new MyDialog(TaskContent.this, R.style.MyDialog, dialog_layout, "任务完成", dialog_content, "确定", "取消", new CustomDialogListener()
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
		rw_RW = (RW_RW) getIntent().getSerializableExtra("bean");
		RWID = String.valueOf(rw_RW.getRWID());
		if (rw_RW.getZRR().equals(dt_manager.getid()))
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
		dt_manager = AppContext.getUserInfo(TaskContent.this);
		getActionBar().hide();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	private void completeRenWu()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("RYID", "36");
		hashMap.put("RWID", RWID);
		String params = HttpUrlConnect.setParams("APP.completeRW_RW", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Toast.makeText(TaskContent.this, "保存成功！", Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(TaskContent.this, "联网失败！", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getYWCRWRY()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("RWID", RWID);
		String params = HttpUrlConnect.setParams("APP.getYWCRWRY", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				List<RW_CYR> listNewData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					listNewData = JSON.parseArray(ResultDeal.getAllRow(result), RW_CYR.class);
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
				} else
				{
					AppContext.makeToast(TaskContent.this, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(TaskContent.this, "error_connectServer");
			}
		});

	}

	private void showData(RW_RW rw_RW)
	{
		tv_WRTXSJ.setText(utils.DateString2Date(rw_RW.getWRTXSJ()));
		tv_WRJZSJ.setText(utils.DateString2Date(rw_RW.getWRJZSJ()));
		tv_WRKSSJ.setText(utils.DateString2Date(rw_RW.getWRKSSJ()));
		tv_RWMC.setText(rw_RW.getRWMC());
		tv_ZCRXM.setText(rw_RW.getZCRXM());
		tv_ZYD.setText("重要");
		tv_RWMS.setText(rw_RW.getRWMS());
		tv_CYR.setText(rw_RW.getZCRXM());

	}

	private void getRW_CYR()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("RWID", RWID);
		String params = HttpUrlConnect.setParams("APP.getRW_CYRFromServer", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				List<RW_CYR> listNewData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					listNewData = JSON.parseArray(ResultDeal.getAllRow(result), RW_CYR.class);
					if (listNewData == null)
					{
						listNewData = new ArrayList<RW_CYR>();
					} else
					{
						String ryxm = new String();
						for (int i = 0; i < listNewData.size(); i++)
						{
							if (listNewData.get(i).getSFWC().equals("true") && listNewData.get(i).getRYID().equals(dt_manager.getid()))
							{
								btn_wc.setText("已完成");
								btn_wc.setClickable(false);
							}
							ryxm = ryxm + listNewData.get(i).getRYXM() + ";";
						}
						tv_CYR.setText(ryxm);
					}
				} else
				{
					AppContext.makeToast(TaskContent.this, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
			}
		});
	}

	private void setRenWuComplete()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("RWID", RWID);
		hashMap.put("RYID", dt_manager.getid());
		String params = HttpUrlConnect.setParams("APP.setRenWuComplete", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					Toast.makeText(TaskContent.this, "标记任务完成失败", Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(TaskContent.this, "恭喜您完成了任务", Toast.LENGTH_SHORT).show();
					finish();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(TaskContent.this, "标记任务完成失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
