package com.bhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.BHQ_XHQK;

import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Offline_ListViewTrackAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<BHQ_XHQK> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	BHQ_XHQK bhq_XHQK;

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
	public Offline_ListViewTrackAdapter(Context context, List<BHQ_XHQK> data, int resource)
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

	HashMap<Integer, View> lmap = new HashMap<Integer, View>();

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent)
	{
		bhq_XHQK = listItems.get(position);
		// 自定义视图
		ListItemView listItemView = null;
		if (lmap.get(position) == null)
		{
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			listItemView.tv_starttime = (TextView) convertView.findViewById(R.id.tv_starttime);
			listItemView.tv_endtime = (TextView) convertView.findViewById(R.id.tv_endtime);
			listItemView.tv_xhlc = (TextView) convertView.findViewById(R.id.tv_xhlc);
			// 设置控件集到convertView
			lmap.put(position, convertView);
			convertView.setTag(listItemView);
		} else
		{
			convertView = lmap.get(position);
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片

		listItemView.tv_starttime.setText(bhq_XHQK.getXHKSSJ());
		listItemView.tv_description.setText("暂停次数" + bhq_XHQK.getZTCS());
		listItemView.tv_endtime.setText(bhq_XHQK.getXHJSSJ());
		listItemView.tv_xhlc.setText(bhq_XHQK.getXHLC());
		return convertView;
	}

}