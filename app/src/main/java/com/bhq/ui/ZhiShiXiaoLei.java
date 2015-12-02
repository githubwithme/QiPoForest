//package com.bhq.ui;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.app.FragmentActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.bhq.R;
//import com.bhq.bean.AppKnowledge;
//
//@SuppressLint({ "NewApi", "ValidFragment" })
//public class ZhiShiXiaoLei extends FragmentActivity
//{
//	ListView listView_knowledge;
//	List<AppKnowledge> list_AppKnowledge = new ArrayList<AppKnowledge>();
//	TextView tv_titlebar;
//	ImageButton btn_search;
//	ImageButton btn_previous;
//	Bundle bundle;
//	String Type;
//	EditText et_search;
//	ZhiShiXiaoLeiAdapter zhiShiXiaoLeiAdapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.zhishixiaolei);
//		getActionBar().hide();
//		findView();
//		Type = getIntent().getStringExtra("Type");
//		list_AppKnowledge = SqliteDb.getAppKnowledge_type(ZhiShiXiaoLei.this, AppKnowledge.class, Type);
//		zhiShiXiaoLeiAdapter = new ZhiShiXiaoLeiAdapter();
//		listView_knowledge.setAdapter(zhiShiXiaoLeiAdapter);
//		listView_knowledge.setOnItemClickListener(new OnItemClickListener()
//		{
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
//			{
//				Intent intent = new Intent(ZhiShiXiaoLei.this, ZhiShiContent.class);
//				Bundle mBundle = new Bundle();
//				mBundle.putParcelable("AppKnowledge", (Parcelable) list_AppKnowledge.get(position));
//				intent.putExtras(mBundle);
//				startActivity(intent);
//			}
//		});
//
//	}
//
//	private void findView()
//	{
//		listView_knowledge = (ListView) findViewById(R.id.lv);
//		et_search = (EditText) findViewById(R.id.et_search);
//		btn_search = (ImageButton) this.findViewById(R.id.btn_search);
//		tv_titlebar = (TextView) this.findViewById(R.id.tv_titlebar);
//		tv_titlebar.setText(Type);
//		btn_previous = (ImageButton) this.findViewById(R.id.btn_previous);
//		btn_previous.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View arg0)
//			{
//				finish();
//			}
//		});
//		btn_search.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				list_AppKnowledge = SqliteDb.getAppKnowledge_search(ZhiShiXiaoLei.this, AppKnowledge.class, et_search.getText().toString());
//				zhiShiXiaoLeiAdapter.notifyDataSetChanged();
//				// MyDatabase myDatabase=new MyDatabase(ZhiShiXiaoLei.this);
//				// zhishixiaolei=myDatabase.searchZhiShiXiaoLei(et_search.getText().toString());
//				// zhishixiaolei=SqliteDb.seleteAll(ZhiShiXiaoLei.this, ,
//				// columnName)
//				// if (zhishixiaolei!=null)
//				// {
//				// zhiShiXiaoLeiAdapter.notifyDataSetChanged();
//				// }
//			}
//		});
//	}
//
//	public class ZhiShiXiaoLeiAdapter extends BaseAdapter
//	{
//		ViewHolder holder;
//		LayoutInflater mInflater = (LayoutInflater) ZhiShiXiaoLei.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//		@Override
//		public int getCount()
//		{
//			return list_AppKnowledge.size();
//		}
//
//		@Override
//		public Object getItem(int position)
//		{
//			return list_AppKnowledge.get(position);
//		}
//
//		@Override
//		public long getItemId(int position)
//		{
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent)
//		{
//			if (convertView == null)
//			{
//				holder = new ViewHolder();
//				convertView = mInflater.inflate(R.layout.zhishixiaolei_item, null);
//				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
//				// holder.iv_left = (ImageView)
//				// convertView.findViewById(R.id.iv_left);
//				convertView.setTag(holder);
//			} else
//			{
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			holder.tv_title.setText(list_AppKnowledge.get(position).getTitle());
//			// holder.iv_left.setText(list_knowledge.get(position).getImage_Description());
//			return convertView;
//		}
//
//		class ViewHolder
//		{
//			private TextView tv_title;
//			private ImageView iv_left;
//
//		}
//	}
// }
