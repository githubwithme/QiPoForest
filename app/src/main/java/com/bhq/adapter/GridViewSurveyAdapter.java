package com.bhq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.Animal;
import com.bhq.ui.YangFangList_;

import java.util.List;

public class GridViewSurveyAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<Animal> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	static class ListItemView
	{ // 自定义控件集合
		public TextView tv_name;
		public TextView tv_ydbh;
		public TextView tv_dcr;
		public ImageButton img_interfere;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public GridViewSurveyAdapter(Context context, List<Animal> data, int resource)
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
			listItemView.img_interfere = (ImageButton) convertView.findViewById(R.id.img_interfere);
			listItemView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			listItemView.tv_ydbh = (TextView) convertView.findViewById(R.id.tv_ydbh);
			listItemView.tv_dcr = (TextView) convertView.findViewById(R.id.tv_dcr);
			listItemView.img_interfere.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(context, YangFangList_.class);
					context.startActivity(intent);
				}
			});
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else
		{
			listItemView = (ListItemView) convertView.getTag();
		}

		// 设置文字和图片
		Animal animal = listItems.get(position);
		listItemView.tv_name.setText(animal.getName());
		// listItemView.tv_dcr.setText(animal.getDescription());
		listItemView.tv_ydbh.setText(animal.getDescription());
		listItemView.img_interfere.setBackgroundResource(R.drawable.yf01);
		// BitmapHelper.setImageViewBackground(context, animal.getDwid(),
		// listItemView.img_interfere);
		return convertView;
	}
}
