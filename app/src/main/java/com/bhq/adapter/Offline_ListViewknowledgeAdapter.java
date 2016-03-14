package com.bhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.BHQ_ZSK;

import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Offline_ListViewknowledgeAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<BHQ_ZSK> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	BHQ_ZSK BHQ_ZSK;

	static class ListItemView
	{ // 自定义控件集合
		public TextView tv_title;
		public TextView tv_time;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 */
	public Offline_ListViewknowledgeAdapter(Context context, List<BHQ_ZSK> data)
	{
		this.context = context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
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
		BHQ_ZSK = listItems.get(position);
		// 自定义视图
		ListItemView listItemView = null;
		if (lmap.get(position) == null)
		{
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.knowledge_item, null);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			listItemView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			// 设置控件集到convertView
			lmap.put(position, convertView);
			convertView.setTag(listItemView);
		} else
		{
			convertView = lmap.get(position);
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片

		listItemView.tv_title.setText(BHQ_ZSK.getZSBT());
		listItemView.tv_time.setText("入库时间："+BHQ_ZSK.getCJSJ().toString());
		return convertView;
	}

}