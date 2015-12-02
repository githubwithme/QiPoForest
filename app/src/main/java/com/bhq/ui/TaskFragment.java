package com.bhq.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.RW_RW;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager;
import com.bhq.common.ConnectionHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.swipelistview.SwipeListViewImpl_RW;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-23 下午3:37:11
 * @description :
 */
@EFragment
public class TaskFragment extends Fragment
{
	@ViewById
	com.swipelistview.ExpandAniLinearLayout swipe_list_ani;
	@ViewById
	ListView swipe_list;
	@ViewById
	TextView tv_tip;

	@AfterViews
	void afterOncreate()
	{
		getTask();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.taskfragment, container, false);
		return rootView;
	}

	private void getTask()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		dt_manager dt_manager = AppContext.getUserInfo(getActivity());
		hashMap.put("RYID", dt_manager.getid());
		String params = ConnectionHelper.setParams("APP.getRenWuByRYID", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				List<RW_RW> list_rw = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{

					list_rw = JSON.parseArray(ResultDeal.getAllRow(result), RW_RW.class);
					if (list_rw == null)
					{
						list_rw = new ArrayList<RW_RW>();
					}
				} else
				{
					AppContext.makeToast(getActivity(), "error_connectDataBase");
					return;
				}

				if (list_rw.size() != 0)
				{
					SwipeListViewImpl_RW swipeListViewImpl_RW = new SwipeListViewImpl_RW();
					swipeListViewImpl_RW.setMyadapter("CYR", getActivity(), swipe_list_ani, list_rw, swipe_list);
				} else
				{
					tv_tip.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{
				Toast.makeText(getActivity(), "获取任务失败!", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
