package com.ty.highway.highwaysystem.support.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;
import com.ty.highway.highwaysystem.support.utils.tree.Node;
import com.ty.highway.highwaysystem.support.utils.tree.TreeListViewAdapter;

import java.util.List;


public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T>
{

	public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
							 int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		super(mTree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent)
	{

		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.adapter_tree_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.id_treenode_icon);
			viewHolder.iconN = (ImageView) convertView
					.findViewById(R.id.id_treenode_next);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_label);
			viewHolder.num = (TextView) convertView
					.findViewById(R.id.id_treenode_num);
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
			if(node.getLevel()==3){
				viewHolder.iconN.setVisibility(View.VISIBLE);
			}else{
				viewHolder.iconN.setVisibility(View.INVISIBLE);
			}
		} else
		{
			viewHolder.iconN.setVisibility(View.INVISIBLE);
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
		}

		viewHolder.label.setText(node.getName());
		int num = node.getNum();
		if(num==0){
			viewHolder.num.setVisibility(View.INVISIBLE);
		}else{
			viewHolder.num.setText(num + "");
			viewHolder.num.setVisibility(View.VISIBLE);
		}


		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		ImageView iconN;
		TextView label;
		TextView num;
	}

}
