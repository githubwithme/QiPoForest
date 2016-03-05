package com.bhq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.ui.ShowLocationInMap_;

import java.util.HashMap;
import java.util.List;

/**
 * 新闻资讯Adapter类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class Offline_ListViewEventAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<BHQ_XHSJ> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	BHQ_XHSJ bhq_XHSJ;

	static class ListItemView
	{ // 自定义控件集合
		public Button btn_location;
		public TextView tv_type;
		public TextView tv_description;
		public TextView tv_time;
		public ImageView img_plant;
		public LinearLayout ll_time;
		public LinearLayout ll_loc;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public Offline_ListViewEventAdapter(Context context, List<BHQ_XHSJ> data, int resource)
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
		bhq_XHSJ = listItems.get(position);
		// 自定义视图
		ListItemView listItemView = null;
		if (lmap.get(position) == null)
		{
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(this.itemViewResource, null);

			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.ll_time = (LinearLayout) convertView.findViewById(R.id.ll_time);
			listItemView.ll_loc = (LinearLayout) convertView.findViewById(R.id.ll_loc);
			listItemView.btn_location = (Button) convertView.findViewById(R.id.btn_location);
			listItemView.img_plant = (ImageView) convertView.findViewById(R.id.img_plant);
			listItemView.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			listItemView.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			listItemView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			listItemView.ll_time.setId(position);
			listItemView.ll_loc.setId(position);
			listItemView.ll_loc.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
//					Intent intent = new Intent(context, ShowLocationInMap_.class);
//					intent.putExtra("lat", listItems.get(v.getId()).getX());
//					intent.putExtra("lng", listItems.get(v.getId()).getY());
//					context.startActivity(intent);
					if (!listItems.get(v.getId()).getX().equals("") && !listItems.get(v.getId()).getY().equals(""))
					{
						Intent intent = new Intent(context, ShowLocationInMap_.class);
						intent.putExtra("lat", listItems.get(v.getId()).getX());
						intent.putExtra("lng", listItems.get(v.getId()).getY());
						context.startActivity(intent);
					}else
					{
						Toast.makeText(context,"暂无定位信息！",Toast.LENGTH_SHORT).show();
					}

				}
			});
			// 设置控件集到convertView
			lmap.put(position, convertView);
			convertView.setTag(listItemView);
		} else
		{
			convertView = lmap.get(position);
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		listItemView.tv_type.setText(bhq_XHSJ.getSJLXMC());
		listItemView.tv_description.setText(bhq_XHSJ.getSJMS());
		listItemView.tv_time.setText(bhq_XHSJ.getSBSJ());
		List<FJ_SCFJ> list_FJ_SCFJ = SqliteDb.getFJ_SCFJList(context, FJ_SCFJ.class, bhq_XHSJ.getSJID(), "1");
		if (list_FJ_SCFJ.size()>0)
		{
			BitmapHelper.loadImage(context, listItemView.img_plant, list_FJ_SCFJ.get(0).getFJBDLJ());
		}

		return convertView;
	}
}