package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.adapter.Offline_ListViewCJXXAdapter;
import com.bhq.app.AppContext;
import com.bhq.app.AppManager;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;
import com.bhq.common.StringUtils;
import com.bhq.common.UIHelper;
import com.bhq.widget.NewDataToast;
import com.bhq.widget.PullToRefreshListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-19 下午2:34:29
 * @description :信息采集类
 */
@EActivity(R.layout.discoveryactivity)
public class Offline_DiscoveryActivity extends Activity implements OnClickListener
{
	private AppContext appContext;// 全局Context
	private List<BHQ_XHSJCJ> listData = new ArrayList<BHQ_XHSJCJ>();
	private Offline_ListViewCJXXAdapter listAdapter;
	private View list_footer;
	private TextView list_foot_more;
	private ProgressBar list_foot_progress;
	private int listSumData;

	@ViewById
	PullToRefreshListView frame_listview_news;
	@ViewById
	ProgressBar main_head_progress;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	ImageButton imgbtn_add;
	@ViewById
	Button btn_animal;
	@ViewById
	Button btn_plant;
	@ViewById
	Button btn_pest;

	String ZYDL;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void btn_animal()
	{
		ZYDL = "01";
		getListData(UIHelper.LISTVIEW_ACTION_INIT, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
		btn_animal.setSelected(true);
		btn_plant.setSelected(false);
		btn_pest.setSelected(false);
	}

	@Click
	void btn_plant()
	{
		ZYDL = "02";
		getListData(UIHelper.LISTVIEW_ACTION_INIT, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
		btn_plant.setSelected(true);
		btn_animal.setSelected(false);
		btn_pest.setSelected(false);
	}

	@Click
	void btn_pest()
	{
		ZYDL = "03";
		getListData(UIHelper.LISTVIEW_ACTION_INIT, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
		btn_pest.setSelected(true);
		btn_animal.setSelected(false);
		btn_plant.setSelected(false);
	}

	@Click
	void imgbtn_add()
	{
		showPop();
	}

	Fragment mContent = new Fragment();
	PopupWindow popupWindow;
	View popupWindowView;

	@Override
	protected void onResume()
	{
		super.onResume();
		// btn_plant.setSelected(true);
		// ZYDL = "02";
		getListData(UIHelper.LISTVIEW_ACTION_REFRESH, UIHelper.LISTVIEW_DATATYPE_NEWS, frame_listview_news, listAdapter, list_foot_more, list_foot_progress, AppContext.PAGE_SIZE, 0);
	}

	@AfterViews
	void afterOncreate()
	{
		btn_plant.setSelected(true);
		ZYDL = "02";
		initAnimalListView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		AppManager.getAppManager().addActivity(this);
		appContext = (AppContext) getApplication();
	}

	private void showPop()
	{
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popupWindowView = layoutInflater.inflate(R.layout.popup_addinfo, null);// 外层
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
		popupWindowView.findViewById(R.id.btn_animal).setOnClickListener(this);
		popupWindowView.findViewById(R.id.btn_plant).setOnClickListener(this);
		popupWindowView.findViewById(R.id.btn_pest).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
		case R.id.btn_animal:
			intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
			intent.putExtra("type", "动物");
			popupWindow.dismiss();
			break;
		case R.id.btn_plant:
			intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
			intent.putExtra("type", "植物");
			popupWindow.dismiss();
			break;
		case R.id.btn_pest:
			intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
			intent.putExtra("type", "微生物");
			popupWindow.dismiss();
			break;
		default:
			break;
		}
		startActivity(intent);
	}

	private void getListData(final int actiontype, final int objtype, final PullToRefreshListView lv, final BaseAdapter adapter, final TextView more, final ProgressBar progressBar, final int PAGESIZE, int PAGEINDEX)
	{
		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(Offline_DiscoveryActivity.this, dt_manager_offline.class);
		List<BHQ_XHSJCJ> listNewData = SqliteDb.getCJXXList(Offline_DiscoveryActivity.this, BHQ_XHSJCJ.class, dt_manager_offline.getid(), ZYDL);
		if (listNewData == null)
		{
			listNewData = new ArrayList<BHQ_XHSJCJ>();
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
						for (BHQ_XHSJCJ BHQ_XHSJCJ1 : listNewData)
						{
							boolean b = false;
							for (BHQ_XHSJCJ BHQ_XHSJCJ2 : listData)
							{
								if (BHQ_XHSJCJ1.getCJID().equals(BHQ_XHSJCJ2.getCJID()))
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
				listData.addAll(listNewData);// listNewData为获取回来的数据，没去重
				break;
			case UIHelper.LISTVIEW_DATATYPE_BLOG:
			case UIHelper.LISTVIEW_DATATYPE_COMMENT:
			}
			if (actiontype == UIHelper.LISTVIEW_ACTION_REFRESH)
			{
				// 提示新加载数据
				if (newdata > 0)
				{
					NewDataToast.makeText(Offline_DiscoveryActivity.this, getString(R.string.new_data_toast_message, newdata), appContext.isAppSound(), R.raw.newdatatoast).show();
				} else
				{
					NewDataToast.makeText(Offline_DiscoveryActivity.this, getString(R.string.new_data_toast_none), false, R.raw.newdatatoast).show();
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
					for (BHQ_XHSJCJ BHQ_XHSJCJ1 : listNewData)
					{
						boolean b = false;
						for (BHQ_XHSJCJ BHQ_XHSJCJ2 : listData)
						{
							if (BHQ_XHSJCJ1.getCJID() == BHQ_XHSJCJ2.getCJID())
							{
								b = true;
								break;
							}
						}
						if (!b)
							listData.add(BHQ_XHSJCJ1);
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
			AppContext.makeToast(Offline_DiscoveryActivity.this, "load_error");
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

	/**
	 * @description:初始化动物列表
	 * @createTime：2015-8-10 上午11:25:25
	 */
	private void initAnimalListView()
	{
		listAdapter = new Offline_ListViewCJXXAdapter(Offline_DiscoveryActivity.this, listData);
		list_footer = Offline_DiscoveryActivity.this.getLayoutInflater().inflate(R.layout.listview_footer, null);
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
				BHQ_XHSJCJ BHQ_XHSJCJ = listData.get(position - 1);
				if (BHQ_XHSJCJ == null)
					return;
				Intent intent = new Intent(Offline_DiscoveryActivity.this, Offline_ShowCJXX_.class);
				intent.putExtra("bean", BHQ_XHSJCJ);// 因为list中添加了头部,因此要去掉一个
				Offline_DiscoveryActivity.this.startActivity(intent);
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
}
