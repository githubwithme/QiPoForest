package com.bhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.Animal;
import com.bhq.common.BitmapHelper;

import java.util.List;

public class GridViewInterfereAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<Animal> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	static class ListItemView
	{ // 自定义控件集合
		public TextView description;
		public TextView name;
		public ImageButton img_interfere;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public GridViewInterfereAdapter(Context context, List<Animal> data, int resource)
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
			convertView = listContainer.inflate(this.itemViewResource, parent,false);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.img_interfere = (ImageButton) convertView.findViewById(R.id.img_interfere);
			listItemView.description = (TextView) convertView.findViewById(R.id.description);
			listItemView.name = (TextView) convertView.findViewById(R.id.name);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else
		{
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Animal animal = listItems.get(position);
		listItemView.name.setText(animal.getName());
		listItemView.description.setText(animal.getDescription());
		BitmapHelper.setImageViewBackground(context, animal.getDwid(), listItemView.img_interfere);
		return convertView;
	}
}
