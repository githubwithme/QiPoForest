package com.swipelistview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.ui.Offline_TaskContent_;
import com.bhq.widget.MyDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.swipelistview.ExpandAniLinearLayout.OnLayoutAnimatListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Offline_SwipeListViewImpl_RW implements OnLayoutAnimatListener
{
	String dialog_content;
	MyDialog myDialog;
	View dialog_layout;
	View parent;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private ListView listView_fuwuchaxun;
	Context context;
	MyListAdapter listAdapter;
	List<RW_RW> list_RW_RW;
	String from;
	ExpandAniLinearLayout swipeListAni;
	AnimateDismissAdapter animateDismissAdapter;
	dt_manager_offline dt_manager_offline;

	public void setMyadapter(String from, Context context, ExpandAniLinearLayout swipeListAni, List<RW_RW> list_RW_RW, ListView listView_fuwuchaxun)
	{
		dt_manager_offline =(com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(context, dt_manager_offline.class);
		this.from = from;
		this.listView_fuwuchaxun = listView_fuwuchaxun;
		this.context = context;
		this.swipeListAni = swipeListAni;
		this.list_RW_RW = list_RW_RW;
		swipeListAni.removeAllViews();
		int totalHeight = 0;
		int position = 0;
		View testView = newConvertView(0);

		for (int i = 0; i < list_RW_RW.size(); i++)
		{
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			screenWidth = displayMetrics.widthPixels;
			screenHeight = displayMetrics.heightPixels;
			int height = measureHeight(i, list_RW_RW.get(i), testView);
			totalHeight = totalHeight + height;
			position = i;
			if (totalHeight > screenHeight)
			{
				break;
			}
			System.out.println("measureHeight" + height);
		}
		for (int i = 0; i <= position; i++)
		{
			View child = newConvertView(i);
			ViewHolder viewHolder = (ViewHolder) child.getTag();
			generateUi(i, list_RW_RW.get(i), viewHolder);
			swipeListAni.addView(child, R.id.rotate_y_view, R.id.message_contents, -1, -1, false);
		}
		ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
		listView_fuwuchaxun.setDivider(colorDrawable);
		swipeListAni.setDividerDrawable(colorDrawable);
		swipeListAni.setOnLayoutAnimatListener(Offline_SwipeListViewImpl_RW.this);// ???????????

		listAdapter = new MyListAdapter(context, R.layout.activity_animateremoval_row, list_RW_RW);
		animateDismissAdapter = new AnimateDismissAdapter(listAdapter, new MyOnDismissCallback());
		listView_fuwuchaxun.setAdapter(animateDismissAdapter);
		animateDismissAdapter.setAbsListView(listView_fuwuchaxun);
		swipeListAni.startExpand();
	}

	private View newConvertView(int position)
	{
		final int mposition = position;
		LayoutInflater mInflater = LayoutInflater.from(context);
		View convertView = mInflater.inflate(R.layout.message_item_layout, null);
		final ViewHolder holder = new ViewHolder();
		holder.mMessageIcon = (ImageView) convertView.findViewById(R.id.message_icon);
		holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
		holder.news_listitem_author = (TextView) convertView.findViewById(R.id.news_listitem_author);
		holder.message_contents = (LinearLayout) convertView.findViewById(R.id.message_contents);
		holder.mMessageTitle = (TextView) convertView.findViewById(R.id.message_title);
		holder.message_day = (TextView) convertView.findViewById(R.id.message_day);
		holder.news_listitem_date = (TextView) convertView.findViewById(R.id.news_listitem_date);
		holder.btn_SFWC = (Button) convertView.findViewById(R.id.btn_SFWC);
		holder.message_time = (TextView) convertView.findViewById(R.id.message_time);
		holder.mRotateYView = (RotateYView) convertView.findViewById(R.id.rotate_y_view);
		holder.mMessageTitle.setTag(position);
		holder.message_contents.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context, Offline_TaskContent_.class);
				intent.putExtra("bean", list_RW_RW.get(mposition));
				context.startActivity(intent);
			}
		});
		holder.mMessageTitle.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context, Offline_TaskContent_.class);
				intent.putExtra("bean", list_RW_RW.get(v.getId()));
				context.startActivity(intent);
			}
		});
		if (dt_manager_offline.getid().equals(list_RW_RW.get(mposition).getZRR()))
		{
			holder.btn_SFWC.setVisibility(View.GONE);
		} else
		{
			getRW_CYR(list_RW_RW.get(mposition), holder.btn_SFWC);
		}
		convertView.setTag(holder);
		return convertView;
	}

	private static final class ViewHolder
	{
		public LinearLayout message_contents;
		public TextView mMessageTitle;
		public TextView message_time;
		public ImageView mMessageIcon;
		public TextView message_day;
		public TextView news_listitem_date;
		public TextView tv_status;
		public TextView news_listitem_author;
		public RotateYView mRotateYView;
		public Button btn_SFWC;
	}

	private void generateUi(int position, RW_RW RW_RW, ViewHolder viewHolder)
	{
		int imgres;
		String status = new String();
		viewHolder.message_time.setText(utils.DateString2Day(list_RW_RW.get(position).getWRJZSJ()));
		viewHolder.message_day.setText(utils.DateString2Day(list_RW_RW.get(position).getWRKSSJ()));
		status = utils.getDayDifference(utils.getToday(), utils.DateString2Day(list_RW_RW.get(position).getWRJZSJ()));
		status = utils.getDayDifference(utils.getToday(), utils.DateString2Day(list_RW_RW.get(position).getWRJZSJ()));
		if (status.toString().equals("已到期"))
		{
			imgres = R.drawable.point_round;
			// viewHolder.tv_status.setBackgroundColor(context.getResources().getColor(R.color.gray));
		} else
		{
			imgres = R.drawable.point_round;
		}

		viewHolder.mMessageIcon.setImageResource(imgres);
		viewHolder.mMessageTitle.setId(position);
		viewHolder.news_listitem_date.setText(utils.DateString2Day(list_RW_RW.get(position).getCJSJ()));
		viewHolder.mMessageTitle.setText(list_RW_RW.get(position).getRWMC());
		viewHolder.news_listitem_author.setText(list_RW_RW.get(position).getCJRXM());
		viewHolder.tv_status.setText(status);
	}

	private void getRW_CYR(RW_RW rw, final TextView textView)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("RWID", rw.getRWID());
		hashMap.put("RYID", dt_manager_offline.getid());
		String params = HttpUrlConnect.setParams("APP.getRW_CYRInfo", "0", hashMap);
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
					if (result.getAffectedRows() > 0)
					{
						listNewData = JSON.parseArray(ResultDeal.getAllRow(result), RW_CYR.class);
						if (listNewData == null)
						{
						} else
						{
							if (listNewData.get(0).getSFWC().equals("true"))
							{
								textView.setText("已完成");
							} else
							{
								textView.setText("未完成");
							}

						}
					} else
					{

					}

				} else
				{
					AppContext.makeToast(context, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(context, "error_connectServer");
			}
		});
	}

	boolean isAnimat = false;

	@Override
	public void onAnimatEnd()
	{
		listView_fuwuchaxun.setVisibility(View.VISIBLE);
		parent = (View) swipeListAni.getParent();
		parent.setVisibility(View.GONE);
		isAnimat = false;
	}

	@Override
	public void onAnimatStart()
	{
		listView_fuwuchaxun.setVisibility(View.GONE);
		parent = (View) swipeListAni.getParent();
		parent.setVisibility(View.VISIBLE);
		isAnimat = true;
	}

	private class MyListAdapter extends ArrayAdapter<RW_RW>
	{
		@Override
		public boolean isEnabled(int position)
		{
			return false;
		}

		public MyListAdapter(Context context, int resource, List<RW_RW> list)
		{
			super(context, resource, list);
		}

		@Override
		public RW_RW getItem(int position)
		{
			return super.getItem(position);
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent)
		{
			List<RotateYView> mRotatteYViews = new ArrayList<RotateYView>();
			ViewHolder holder = null;
			System.out.println("getView" + position);
			// if (convertView == null) //有异常
			// {
			convertView = newConvertView(position);
			holder = (ViewHolder) convertView.getTag();
			mRotatteYViews.add(holder.mRotateYView);
			// } else {
			// holder = (ViewHolder) convertView.getTag();
			// }
			holder.mRotateYView.setIndex(position);
			generateUi(position, getItem(position), holder);
			return convertView;
		}

	}

	private class MyOnDismissCallback implements OnDismissCallback
	{
		@Override
		public void onDismiss(AbsListView listView, int[] reverseSortedPositions)
		{
			for (int position : reverseSortedPositions)
			{
				list_RW_RW.remove(position);
			}
			listAdapter.notifyDataSetChanged();
			new Handler().postDelayed(new Runnable()
			{

				@Override
				public void run()
				{
					// edit.setChecked(false);
				}
			}, 10);
		}

	}

	private int measureHeight(int positon, RW_RW RW_RW, View convertView)
	{
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		int measureSpecHeight = MeasureSpec.makeMeasureSpec(screenHeight, MeasureSpec.AT_MOST);
		int measureSpecWidth = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
		generateUi(positon, RW_RW, viewHolder);
		convertView.measure(measureSpecWidth, measureSpecHeight);
		return convertView.getMeasuredHeight();
	}
}
