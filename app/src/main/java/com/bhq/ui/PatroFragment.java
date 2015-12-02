package com.bhq.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.adapter.GridViewPatroTrackAdapter;
import com.bhq.adapter.ListViewTrackAdapter;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.StringUtils;
import com.bhq.common.UIHelper;
import com.bhq.widget.NewDataToast;
import com.bhq.widget.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-19 下午2:41:19
 * @description :
 */
@EFragment
public class PatroFragment extends Fragment
{
	private AppContext appContext;// 全局Context
	private ListViewTrackAdapter listAdapter;
	private View list_footer;
	private TextView list_foot_more;
	private ProgressBar list_foot_progress;
	private int listSumData;
	Fragment mContent = new Fragment();
	private PopupWindow popupWindow;
	private View popupWindowView;
	List<BHQ_XHQK> listData = new ArrayList<BHQ_XHQK>();
	GridView gridView;
	GridViewPatroTrackAdapter adapter;

	// @ViewById
	// FrameLayout fl_chart;
	@ViewById
	PullToRefreshListView frame_listview_news;

	@Override
	public void onResume()
	{
		super.onResume();
		getListData(UIHelper.LISTVIEW_ACTION_REFRESH, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
	}

	@AfterViews
	void afterOncreate()
	{
		// PatroMiniChartFragment patroChartFragment = new
		// PatroMiniChartFragment();
		// switchContent(mContent, patroChartFragment);
		initAnimalListView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.patrofragment, container, false);
		appContext = (AppContext) getActivity().getApplication();
		IntentFilter intentfilter_map_more = new IntentFilter(AppContext.BROADCAST_MAP_MORE);
		getActivity().registerReceiver(receiver_map_more, intentfilter_map_more);
		return rootView;
	}

	BroadcastReceiver receiver_map_more = new BroadcastReceiver()// 从扩展页面返回信息
	{
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent)
		{
			initPWV();
		}
	};

	private void initPWV()
	{
		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		popupWindowView = layoutInflater.inflate(R.layout.map_popupwindow, null);// 外层
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
		popupWindow.showAsDropDown(getActivity().findViewById(R.id.btn_more), 0, 0);
		popupWindow.setOutsideTouchable(true);
	}

	/**
	 * GridView初始化方法
	 */
	// private void initGridView()
	// {
	// adapter = new GridViewPatroTrackAdapter(getActivity(), listData,
	// R.layout.patrotrack_listitem);
	// gridView.setAdapter(adapter);
	// gridView.setOnItemLongClickListener(new OnItemLongClickListener()
	// {
	//
	// @Override
	// public boolean onItemLongClick(AdapterView<?> parent, View view, int
	// position, long id)
	// {
	// Toast.makeText(getActivity(), "LongClick on " +
	// parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
	// return true;
	// }
	// });
	// gridView.setOnItemClickListener(new OnItemClickListener()
	// {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id)
	// {
	// Intent intent = new Intent(getActivity(), PatrolContent_.class);
	// getActivity().startActivity(intent);
	// }
	// });
	// // 加载资讯数据
	// if (listData.isEmpty())
	// {
	// getListData(gridView, adapter, AppContext.PAGE_SIZE, 0);
	// }
	//
	// }
	private void initAnimalListView()
	{
		listAdapter = new ListViewTrackAdapter(getActivity(), listData, R.layout.patrotrack_listitem);
		list_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
		list_foot_more = (TextView) list_footer.findViewById(R.id.listview_foot_more);
		list_foot_progress = (ProgressBar) list_footer.findViewById(R.id.listview_foot_progress);
		frame_listview_news.addFooterView(list_footer);// 添加底部视图 必须在setAdapter前
		frame_listview_news.setAdapter(listAdapter);
		frame_listview_news.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// 点击头部、底部栏无效
				if (position == 0 || view == list_footer)
					return;

				// Animal animal = null;
				// // 判断是否是TextView
				// if (view instanceof TextView)
				// {
				// animal = (Animal) view.getTag();
				// } else
				// {
				// TextView tv = (TextView)
				// view.findViewById(R.id.news_listitem_title);
				// animal = (Animal) tv.getTag();
				// }
				// if (animal == null)
				// return;
				BHQ_XHQK bhq_XHQK = listData.get(position - 1);
				if (bhq_XHQK == null)
					return;
				// Intent intent = new Intent(getActivity(), ShowPlant_.class);
				// intent.putExtra("track", track);// 因为list中添加了头部,因此要去掉一个
				// getActivity().startActivity(intent);

				Intent intent = new Intent(getActivity(), PatrolContent_.class);
				intent.putExtra("bean", bhq_XHQK);
				getActivity().startActivity(intent);
			}
		});
		frame_listview_news.setOnScrollListener(new AbsListView.OnScrollListener()
		{
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				frame_listview_news.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (listData.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try
				{
					if (view.getPositionForView(list_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e)
				{
					scrollEnd = false;
				}

				int lvDataState = StringUtils.toInt(frame_listview_news.getTag());
				if (scrollEnd && lvDataState == UIHelper.LISTVIEW_DATA_MORE)
				{
					frame_listview_news.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					list_foot_more.setText(R.string.load_ing);// 之前显示为"完成"加载
					list_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = listSumData / AppContext.PAGE_SIZE;// 总数里面包含几个PAGE_SIZE
					getListData(UIHelper.LISTVIEW_ACTION_SCROLL, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, pageIndex);
					// loadLvNewsData(curNewsCatalog, pageIndex, lvNewsHandler,
					// UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				frame_listview_news.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		frame_listview_news.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener()
		{
			public void onRefresh()
			{
				// loadLvNewsData(curNewsCatalog, 0, lvNewsHandler,
				// UIHelper.LISTVIEW_ACTION_REFRESH);
				getListData(UIHelper.LISTVIEW_ACTION_REFRESH, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
			}
		});
		// 加载资讯数据
		if (listData.isEmpty())
		{
			getListData(UIHelper.LISTVIEW_ACTION_INIT, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
		}
	}

	private void getListData(final int actiontype, final int objtype, final PullToRefreshListView lv, final BaseAdapter adapter, final TextView more, final ProgressBar progressBar, final int PAGESIZE, int PAGEINDEX)
	{
		dt_manager dt_manager = AppContext.getUserInfo(getActivity());
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("PAGESIZE", String.valueOf(PAGESIZE));
		hashMap.put("PAGEINDEX", String.valueOf(PAGEINDEX));
		hashMap.put("XHRY", String.valueOf(dt_manager.getid()));
		String params = ConnectionHelper.setParams("APP.GetBHQ_XHQKList", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				List<BHQ_XHQK> listNewData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					listNewData = JSON.parseArray(ResultDeal.getAllRow(result), BHQ_XHQK.class);
					if (listNewData == null)
					{
						listNewData = new ArrayList<BHQ_XHQK>();
					}
				} else
				{
					AppContext.makeToast(getActivity(), "error_connectDataBase");
					return;
				}

				// 数据处理
				int size = listNewData.size();

				switch (actiontype)
				{
				case UIHelper.LISTVIEW_ACTION_INIT:// 初始化
				case UIHelper.LISTVIEW_ACTION_REFRESH:// 顶部刷新
				case UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG:// 页面切换
					int newdata = 0;// 该变量为新加载数据数量-只有顶部刷新才会使用到
					switch (objtype)
					{
					case UIHelper.LISTVIEW_DATATYPE_NEWS:
						listSumData = size;
						if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH)
						{
							if (listData.size() > 0)// 页面切换时，若之前列表中已有数据，则往上面添加，并判断去除重复
							{
								for (BHQ_XHQK BHQ_XHQK1 : listNewData)
								{
									boolean b = false;
									for (BHQ_XHQK BHQ_XHQK2 : listData)
									{
										if (BHQ_XHQK1.getXHID().equals(BHQ_XHQK2.getXHID()))
										{
											b = true;
											break;
										}
									}
									if (!b)// 两个不相等才添加
										newdata++;
								}
							} else
							{
								newdata = size;
							}
						}
						listData.clear();// 先清除原有数据
						listData.addAll(listNewData);
						break;
					case UIHelper.LISTVIEW_DATATYPE_BLOG:
					case UIHelper.LISTVIEW_DATATYPE_COMMENT:
					}
					if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH)
					{
						// 提示新加载数据
						if (newdata > 0)
						{
							NewDataToast.makeText(getActivity(), getString(R.string.new_data_toast_message, newdata), appContext.isAppSound(), R.raw.newdatatoast).show();
						} else
						{
							NewDataToast.makeText(getActivity(), getString(R.string.new_data_toast_none), false, R.raw.newdatatoast).show();
						}
					}
					break;
				case UIHelper.LISTVIEW_ACTION_SCROLL:// 底部刷新，并且判断去除重复数据
					switch (objtype)
					{
					case UIHelper.LISTVIEW_DATATYPE_NEWS:
						listSumData += size;
						if (listNewData.size() > 0)
						{
							for (BHQ_XHQK BHQ_XHQK1 : listNewData)
							{
								boolean b = false;
								for (BHQ_XHQK BHQ_XHQK2 : listData)
								{
									if (BHQ_XHQK1.getXHID() == BHQ_XHQK2.getXHID())
									{
										b = true;
										break;
									}
								}
								if (!b)
									listData.add(BHQ_XHQK1);
							}
						} else
						{
							listData.addAll(listNewData);
						}
						break;
					case UIHelper.LISTVIEW_DATATYPE_BLOG:
						break;
					}
					break;
				}
				// 刷新列表
				if (size >= 0)
				{
					if (size < PAGESIZE)
					{
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);// 已经全部加载完毕
					} else if (size == PAGESIZE)
					{// 还有数据可以加载
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}

				} else if (size == -1)
				{
					// 有异常--显示加载出错 & 弹出错误消息
					lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
					AppContext.makeToast(getActivity(), "load_error");
				}
				if (adapter.getCount() == 0)
				{
					lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progressBar.setVisibility(ProgressBar.GONE);
				// main_head_progress.setVisibility(ProgressBar.GONE);
				if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH)
				{
					lv.onRefreshComplete(getString(R.string.pull_to_refresh_update) + new Date().toLocaleString());
					lv.setSelection(0);
				} else if (actiontype == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG)
				{
					lv.onRefreshComplete();
					lv.setSelection(0);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(getActivity(), "error_connectServer");
			}
		});
	}

	// public void switchContent(Fragment from, Fragment to)
	// {
	// if (mContent != to)
	// {
	// mContent = to;
	// FragmentTransaction transaction =
	// getActivity().getFragmentManager().beginTransaction();
	// if (!to.isAdded())
	// { // 先判断是否被add过
	// transaction.hide(from).add(R.id.fl_chart, to).commit(); //
	// 隐藏当前的fragment，add下一个到Activity中
	// } else
	// {
	// transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
	// }
	// }
	// }
}
