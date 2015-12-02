package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.utils;
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
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:33:24
 * @description :展示动物类
 */
@SuppressLint("NewApi")
@EActivity(R.layout.showcjxx)
public class ShowCJXX extends Activity
{
	AlertDialog dialog;
	BHQ_XHSJCJ bhq_XHSJCJ;
	@ViewById
	FrameLayout img_container;
	@ViewById
	ImageView img_tip;
	@ViewById
	TextView tv_edit;
	@ViewById
	TextView tv_delete;
	@ViewById
	TextView tv_title;
	@ViewById
	ImageButton imgbtn_back;

	@ViewById
	TextView tv_type;
	@ViewById
	TextView tv_sfxwz;
	@ViewById
	TextView tv_ldm;
	@ViewById
	TextView tv_ywm;
	@ViewById
	TextView tv_gang;
	@ViewById
	TextView tv_mu;
	@ViewById
	TextView tv_ke;
	@ViewById
	TextView tv_bhjb;
	@ViewById
	TextView tv_bwd;
	@ViewById
	TextView tv_tz;
	@ViewById
	TextView tv_xx;
	@ViewById
	TextView tv_szhj;
	@ViewById
	TextView tv_bwys;
	@ViewById
	TextView tv_bz;

	@ViewById
	TextView tv_cjsj;
	@ViewById
	TextView tv_spyj;
	@ViewById
	TextView tv_sprxm;
	@ViewById
	TextView tv_spsj;

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
		showDialog("删除信息", "删除信息？", "确定", "取消");
	}

	@Click
	void tv_edit()
	{
		Intent intent = new Intent(ShowCJXX.this, EditCJXX_.class);
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
		tv_type.setText(bhq_XHSJCJ.getZYDLMC() + "——" + bhq_XHSJCJ.getZYXLMC());
		if (bhq_XHSJCJ.getSFXWZ().equals("1"))
		{
			tv_sfxwz.setText("是");
		} else
		{
			tv_sfxwz.setText("否");
		}
		if (bhq_XHSJCJ.getBHJBMC().equals("null"))
		{
			tv_bhjb.setText("");
		} else
		{
			tv_bhjb.setText(bhq_XHSJCJ.getBHJBMC());
		}
		if (bhq_XHSJCJ.getBWDMC().equals("null"))
		{
			tv_bwd.setText("");
		} else
		{
			tv_bwd.setText(bhq_XHSJCJ.getBWDMC());
		}
		tv_title.setText(bhq_XHSJCJ.getZM());
		tv_ldm.setText(bhq_XHSJCJ.getZMLDM());
		tv_ywm.setText(bhq_XHSJCJ.getZMYWM());
		tv_gang.setText(bhq_XHSJCJ.getGANG());
		tv_mu.setText(bhq_XHSJCJ.getMU());
		tv_ke.setText(bhq_XHSJCJ.getKE());
		tv_tz.setText(bhq_XHSJCJ.getTZ());
		tv_xx.setText(bhq_XHSJCJ.getXX());
		tv_szhj.setText(bhq_XHSJCJ.getSZHJ());
		tv_bwys.setText(bhq_XHSJCJ.getBWYS());
		tv_bz.setText(bhq_XHSJCJ.getBDZYBZ());

		tv_cjsj.setText(utils.DateString2Date(bhq_XHSJCJ.getCJSJ()));
		tv_spyj.setText(bhq_XHSJCJ.getSPYJ());
		tv_sprxm.setText(bhq_XHSJCJ.getSPRXM());
		tv_spsj.setText(utils.DateString2Date(bhq_XHSJCJ.getSPSJ()));
	}

	private void setImage(List<FJ_SCFJ> list_fj_scfj)
	{
		PictureScrollFragment pictureScrollFragment = new PictureScrollFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("imgurl", (ArrayList<? extends Parcelable>) list_fj_scfj);
		pictureScrollFragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.img_container, pictureScrollFragment).commit();
	}

	private void getFJ_SCFJ(String glid)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("GLID", glid);
		hashMap.put("FJLX", "1");
		String params = ConnectionHelper.setParams("APP.SelectFJ_SCFJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_fj_scfj = JSON.parseArray(ResultDeal.getAllRow(result), FJ_SCFJ.class);
					if (list_fj_scfj != null)
					{
						setImage(list_fj_scfj);
					} else
					{
						img_container.setVisibility(View.GONE);
						img_tip.setVisibility(View.VISIBLE);
					}
				} else
				{
					Toast.makeText(ShowCJXX.this, "获取失败", Toast.LENGTH_SHORT).show();
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowCJXX.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void deleteCJXX(String CJID)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("CJID", CJID);
		hashMap.put("v_flag", "A");
		String params = ConnectionHelper.setParams("APP.DeleteCJXX", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					Toast.makeText(ShowCJXX.this, "删除成功", Toast.LENGTH_SHORT).show();
					finish();
				} else
				{
					Toast.makeText(ShowCJXX.this, "删除失败", Toast.LENGTH_SHORT).show();
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowCJXX.this, "删除失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void showDialog(String title, String message, String str_Positive, String str_Negative)
	{
		AlertDialog.Builder builder = new Builder(ShowCJXX.this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(str_Positive, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				deleteCJXX(bhq_XHSJCJ.getCJID());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(str_Negative, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}
}
