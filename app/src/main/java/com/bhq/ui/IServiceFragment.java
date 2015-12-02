package com.bhq.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.Apk;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.ConnectionHelper;
import com.bhq.widget.CustomDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.service.SynchronousData;
import com.service.UpdateApk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-31 上午9:59:21
 * @description :意见反馈、技术支持、帮助、关于、
 */
@EFragment
public class IServiceFragment extends Fragment
{
	TextView tv_tips;
	Button btn_syn;
	Button btn_sure;
	Button btn_cancle;
	LinearLayout ll_syn;
	LinearLayout ll_sure;
	ProgressBar pb;
	CustomDialog customDialog;
	@ViewById
	TextView tv_changepwd;
	@ViewById
	TextView tv_renewversion;
	@ViewById
	FrameLayout fl_new;
	@ViewById
	RelativeLayout rl_tb;

	@Click
	void rl_gx()
	{
		Intent intent = new Intent(getActivity(), UpdateApk.class);
		intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
		getActivity().startService(intent);

	}

	@Click
	void rl_yj()
	{
		Intent intent = new Intent(getActivity(), YiJianFanKui_.class);
		getActivity().startActivity(intent);
	}

	// @Click
	// void rl_tb()
	// {
	// showSynchronousDialog();
	// }

	@Click
	void rl_bz()
	{
		Intent intent = new Intent(getActivity(), Helper_.class);
		getActivity().startActivity(intent);
	}

	@Click
	void rl_gy()
	{
		Intent intent = new Intent(getActivity(), GuanYu_.class);
		getActivity().startActivity(intent);
	}

	// @Click
	// void tv_renewversion()
	// {
	// // if (utils.isServiceRunning(getActivity(), "com.service.UpdateApk"))
	// // {
	// // Toast.makeText(getActivity(), "正在更新，请勿重复点击!",
	// // Toast.LENGTH_SHORT).show();
	// // } else
	// // {
	// Intent intent = new Intent(getActivity(), UpdateApk.class);
	// intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
	// getActivity().startService(intent);
	// // }
	//
	// }

	// @Click
	// void tv_changepwd()
	// {
	// Intent intent = new Intent(getActivity(), ChangePwd_.class);
	// startActivity(intent);
	// }

	@AfterViews
	void afterOncreate()
	{
		rl_tb.setVisibility(View.GONE);
		getListData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.iservicefragment, container, false);
		IntentFilter intentFilter = new IntentFilter(AppContext.Progress_SynchronousData);
		getActivity().registerReceiver(Progress_SynchronousData, intentFilter);
		return rootView;

	}

	BroadcastReceiver Progress_SynchronousData = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			int progress = intent.getIntExtra("progress", 0);
			int status = intent.getIntExtra("status", -1);
			pb.setProgress(progress);
			if (status == 0)
			{
				tv_tips.setText("数据同步失败!");
				pb.setVisibility(View.GONE);
			} else if (status == 1)
			{
				tv_tips.setText("数据同步成功!");
				pb.setVisibility(View.GONE);
			}

		}
	};

	private void getListData()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String params = ConnectionHelper.setParams("APP.getReNewInfo", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				List<Apk> listNewData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					listNewData = JSON.parseArray(ResultDeal.getAllRow(result), Apk.class);
					if (listNewData == null)
					{
						listNewData = new ArrayList<Apk>();
					} else
					{
						Apk apk = listNewData.get(0);
						PackageInfo packageInfo = null;
						try
						{
							packageInfo = getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
						} catch (NameNotFoundException e)
						{
							e.printStackTrace();
						}
						String localVersion = packageInfo.versionName;
						if (localVersion.equals(apk.getVersion()))
						{
						} else
						{
							fl_new.setVisibility(View.VISIBLE);
						}
					}
				} else
				{
					AppContext.makeToast(getActivity(), "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(getActivity(), "error_connectServer");
			}
		});
	}

	private void showSynchronousDialog()
	{
		View dialog_layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.synchronous_dialog, null);
		tv_tips = (TextView) dialog_layout.findViewById(R.id.tv_tips);
		btn_syn = (Button) dialog_layout.findViewById(R.id.btn_syn);
		btn_sure = (Button) dialog_layout.findViewById(R.id.btn_sure);
		btn_cancle = (Button) dialog_layout.findViewById(R.id.btn_cancle);
		ll_syn = (LinearLayout) dialog_layout.findViewById(R.id.ll_syn);
		ll_sure = (LinearLayout) dialog_layout.findViewById(R.id.ll_sure);
		pb = (ProgressBar) dialog_layout.findViewById(R.id.pb);
		btn_sure.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				customDialog.dismiss();
			}
		});
		btn_syn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				ll_sure.setVisibility(View.VISIBLE);
				ll_syn.setVisibility(View.GONE);
				tv_tips.setVisibility(View.GONE);
				pb.setVisibility(View.VISIBLE);
				Intent intent = new Intent(getActivity(), SynchronousData.class);
				intent.setAction(SynchronousData.ACTION_UPLOADDATA);
				getActivity().startService(intent);
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				customDialog.dismiss();
			}
		});
		customDialog = new CustomDialog(getActivity(), R.style.MyDialog, dialog_layout);
		customDialog.show();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		getActivity().unregisterReceiver(Progress_SynchronousData);
	}
}
