package com.bhq.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.adapter.GridViewInterfereAdapter;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.Animal;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.ConnectionHelper;
import com.bhq.listener.InterfereListFragmentListener;
import com.bhq.widget.PullToRefreshLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-19 下午2:41:19
 * @description :
 */
@EFragment
public class InterfereListFragment extends Fragment
{
	List<Animal> listData = new ArrayList<Animal>();
	GridView gridView;
	GridViewInterfereAdapter adapter;

	@AfterViews
	void afterOncreate()
	{
		initGridView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.interferelistfragment, container, false);
		((PullToRefreshLayout) rootView.findViewById(R.id.refresh_view)).setOnRefreshListener(new InterfereListFragmentListener());
		gridView = (GridView) rootView.findViewById(R.id.content_view);
		return rootView;
	}

	/**
	 * GridView初始化方法
	 */
	private void initGridView()
	{
		adapter = new GridViewInterfereAdapter(getActivity(), listData, R.layout.interfere_listitem);
		gridView.setAdapter(adapter);
		gridView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getActivity(), "LongClick on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Toast.makeText(getActivity(), " Click on " + parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
			}
		});
		// 加载资讯数据
		if (listData.isEmpty())
		{
			getListData(gridView, adapter, AppContext.PAGE_SIZE, 0);
		}

	}

	private void getListData(final GridView gridView, final BaseAdapter adapter, final int PAGESIZE, int PAGEINDEX)
	{
		// getActivity().main_head_progress.setVisibility(ProgressBar.VISIBLE);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("PAGESIZE", String.valueOf(PAGESIZE));
		hashMap.put("PAGEINDEX", String.valueOf(PAGEINDEX));
		String params = ConnectionHelper.setParams("APP.GetanimalList", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				// List<Animal> listNewData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					listData = JSON.parseArray(ResultDeal.getAllRow(result), Animal.class);
				} else
				{
					AppContext.makeToast(getActivity(), "error_connectDataBase");
					return;
				}
				// 数据处理
				int size = listData.size();

				// 刷新列表
				if (size >= 0)
				{
					if (size < PAGESIZE)
					{
						GridViewInterfereAdapter adapter = new GridViewInterfereAdapter(getActivity(), listData, R.layout.interfere_listitem);
						gridView.setAdapter(adapter);
					} else if (size == PAGESIZE)
					{// 还有数据可以加载
						// adapter.notifyDataSetChanged();
						GridViewInterfereAdapter adapter = new GridViewInterfereAdapter(getActivity(), listData, R.layout.interfere_listitem);
						gridView.setAdapter(adapter);
					}

				} else if (size == -1)
				{
					// 有异常--显示加载出错 & 弹出错误消息
					AppContext.makeToast(getActivity(), "load_error");
				}
				if (adapter.getCount() == 0)
				{
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1)
			{

			}
		});
	}
}
