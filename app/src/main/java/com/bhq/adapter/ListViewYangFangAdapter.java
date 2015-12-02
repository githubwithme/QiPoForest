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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.QiaoMuTree;
import com.bhq.common.BitmapHelper;
import com.bhq.common.utils;
import com.bhq.ui.EditQMC_;

import java.util.List;

/**
 * 新闻资讯Adapter类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class ListViewYangFangAdapter extends BaseAdapter
{
	private Context context;// 运行上下文
	private List<QiaoMuTree> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源

	static class ListItemView
	{ // 自定义控件集合
		public TextView tv_SZZK;
		public TextView tv_xj;
		public TextView tv_sg;
		public TextView tv_DCRQ;
		public TextView tv_ZM;
		public ImageView img_animal;
		public Button btn_track;
		public Button btn_edit;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewYangFangAdapter(Context context, List<QiaoMuTree> data, int resource)
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
			convertView = listContainer.inflate(this.itemViewResource, null);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.img_animal = (ImageView) convertView.findViewById(R.id.img_animal);
			listItemView.tv_SZZK = (TextView) convertView.findViewById(R.id.tv_SZZK);
			listItemView.tv_xj = (TextView) convertView.findViewById(R.id.tv_xj);
			listItemView.tv_sg = (TextView) convertView.findViewById(R.id.tv_sg);
			listItemView.tv_DCRQ = (TextView) convertView.findViewById(R.id.tv_DCRQ);
			listItemView.tv_ZM = (TextView) convertView.findViewById(R.id.tv_ZM);
			listItemView.btn_track = (Button) convertView.findViewById(R.id.btn_track);
			listItemView.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
			listItemView.btn_track.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					Toast.makeText(context, "测试数据，不能删除", Toast.LENGTH_SHORT).show();
				}
			});
			listItemView.btn_edit.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					Intent intent = new Intent(context, EditQMC_.class);
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
		QiaoMuTree qiaoMuTree = listItems.get(position);
		listItemView.tv_SZZK.setText("生长状况：" + qiaoMuTree.getSZZK());
		listItemView.tv_xj.setText("胸径：" + qiaoMuTree.getXJ());
		listItemView.tv_sg.setText("树高：" + qiaoMuTree.getSG());
		listItemView.tv_DCRQ.setText(qiaoMuTree.getDCRQ());
		listItemView.tv_ZM.setText(qiaoMuTree.getZM());
		String result = utils.parseJsonFile(context, "qmtreefj.json");
		List<FJ_SCFJ> list_FJ_SCFJ = null;
		list_FJ_SCFJ = JSON.parseArray(result, FJ_SCFJ.class);
		for (int i = 0; i < list_FJ_SCFJ.size(); i++)
		{
			if (qiaoMuTree.getQMID().equals(list_FJ_SCFJ.get(i).getGLID()))
			{
				BitmapHelper.setImageViewBackground(context, listItemView.img_animal, AppConfig.url + list_FJ_SCFJ.get(i).getFJLJ());
			}
		}
		return convertView;
	}

}