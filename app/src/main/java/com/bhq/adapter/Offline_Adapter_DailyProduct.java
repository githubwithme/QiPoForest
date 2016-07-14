package com.bhq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.ui.ShowLocationInMap_;

import java.util.HashMap;
import java.util.List;

/**
 * 
 */
public class Offline_Adapter_DailyProduct extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<BHQ_XHSJCJ> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	BHQ_XHSJCJ BHQ_XHSJCJ;

	static class ListItemView
	{ // 自定义控件集合
		public RelativeLayout rl_location;
		public ImageButton ib_location;
		public ImageView img;
		public TextView status_review;
		public TextView tv_fl;
		public TextView tv_zm;
		public TextView tv_fxsj;
	}

	/**
	 * 实例化Adapter
	 *
	 * @param context
	 * @param data
	 */
	public Offline_Adapter_DailyProduct(Context context, List<BHQ_XHSJCJ> data)
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
		BHQ_XHSJCJ = listItems.get(position);
		// 自定义视图
		ListItemView listItemView = null;
		if (lmap.get(position) == null)
		{
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.offline_adapter_dailyproduct, null);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.rl_location = (RelativeLayout) convertView.findViewById(R.id.rl_location);
			listItemView.ib_location = (ImageButton) convertView.findViewById(R.id.ib_location);
			listItemView.img = (ImageView) convertView.findViewById(R.id.img);
			listItemView.tv_fl = (TextView) convertView.findViewById(R.id.tv_fl);
			listItemView.tv_zm = (TextView) convertView.findViewById(R.id.tv_zm);
			listItemView.tv_fxsj = (TextView) convertView.findViewById(R.id.tv_fxsj);
			listItemView.status_review = (TextView) convertView.findViewById(R.id.status_review);
			listItemView.rl_location.setId(position);
			listItemView.rl_location.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (!listItems.get(v.getId()).getX().equals("") && !listItems.get(v.getId()).getY().equals(""))
					{
						Intent intent = new Intent(context, ShowLocationInMap_.class);
						intent.putExtra("lat", listItems.get(v.getId()).getX());
						intent.putExtra("lng", listItems.get(v.getId()).getY());
						context.startActivity(intent);
					}else
					{
						Toast.makeText(context, "暂无定位信息！", Toast.LENGTH_SHORT).show();
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

		listItemView.tv_zm.setText(BHQ_XHSJCJ.getBDZYBZ());
		listItemView.tv_fl.setText(BHQ_XHSJCJ.getZYXLMC());
		if (BHQ_XHSJCJ.getSFSP().toString().equals("true"))
		{
			listItemView.status_review.setText("已审批");
			listItemView.status_review.setTextColor(context.getResources().getColor(R.color.bg_blue));
		}
		listItemView.tv_fxsj.setText(BHQ_XHSJCJ.getCJSJ().toString());

		List<FJ_SCFJ> list_FJ_SCFJ = SqliteDb.getFJ_SCFJList(context, FJ_SCFJ.class, BHQ_XHSJCJ.getCJID(), "1");
		if (list_FJ_SCFJ != null && list_FJ_SCFJ.size()>0)
		{
			BitmapHelper.loadImage(context, listItemView.img, list_FJ_SCFJ.get(0).getFJBDLJ());
		}

		return convertView;
	}

}