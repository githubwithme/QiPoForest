package com.bhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.BHQ_XHXL;
import com.bhq.common.SqliteDb;

import java.util.HashMap;
import java.util.List;

public class XHXL_Adapter extends BaseAdapter
{
    private Context context;// 运行上下文
    private List<String> list_department;// 数据集合
    private LayoutInflater listContainer;// 视图容器
    ListItemView listItemView = null;

    String xl;

    static class ListItemView
    {
        public TextView tv_name;
    }

    public XHXL_Adapter(Context context, List<String> data)
    {
        this.context = context;
        this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
        this.list_department = data;
    }

    public int getCount()
    {
        return list_department.size();
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        xl = list_department.get(position);
        // 自定义视图

        if (lmap.get(position) == null)
        {
            // 获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.xhxl_item, null);
            listItemView = new ListItemView();
            // 获取控件对象
            listItemView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            // 设置控件集到convertView
            lmap.put(position, convertView);
            convertView.setTag(listItemView);
        } else
        {
            convertView = lmap.get(position);
            listItemView = (ListItemView) convertView.getTag();
        }
        // 设置文字和图片

        BHQ_XHXL bhq_xhxl = SqliteDb.getXHLX(context, list_department.get(position));
        if (bhq_xhxl != null)
        {
            if (bhq_xhxl.getXLLX().equals("1"))
            {
                listItemView.tv_name.setText("日巡护-"+bhq_xhxl.getXLXHNR());
            } else if (bhq_xhxl.getXLLX().equals("2"))
            {
                listItemView.tv_name.setText("周巡护-"+bhq_xhxl.getXLXHNR());
            } else if (bhq_xhxl.getXLLX().equals("3"))
            {
                listItemView.tv_name.setText("月巡护-"+bhq_xhxl.getXLXHNR());
            }

        }

        return convertView;
    }
}