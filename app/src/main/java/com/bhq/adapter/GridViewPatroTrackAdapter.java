package com.bhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.Track;
import com.bhq.common.utils;

import java.util.List;

public class GridViewPatroTrackAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<Track> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	static class ListItemView
	{ // 自定义控件集合
		public TextView tv_description;
		public TextView tv_starttime;
		public TextView tv_endtime;
		public TextView tv_xhlc;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public GridViewPatroTrackAdapter(Context context, List<Track> data, int resource)
	{
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		this.listItems = data;
	}

	public int getCount()
	{
		return listItems.size();
	}

	public Object getItem(int arg0)
	{
		return null;
	}

	public long getItemId(int arg0)
	{
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// Log.d("method", "getView");

		// 自定义视图
		ListItemView listItemView = null;

		if (convertView == null)
		{
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, parent, false);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			listItemView.tv_xhlc = (TextView) convertView.findViewById(R.id.tv_xhlc);
			listItemView.tv_starttime = (TextView) convertView.findViewById(R.id.tv_starttime);
			listItemView.tv_endtime = (TextView) convertView.findViewById(R.id.tv_endtime);
			convertView.setTag(listItemView);
		} else
		{
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Track track = listItems.get(position);
		listItemView.tv_description.setText(track.getDescription());
		listItemView.tv_starttime.setText(utils.getTime());
		listItemView.tv_endtime.setText(utils.getTime());
		listItemView.tv_xhlc.setText(String.valueOf(utils.randomCommon(5d, 20d, 1)[0]).substring(0, 2));
		return convertView;
	}
}
