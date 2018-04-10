package com.example.memoaccountapp.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public List<T> list;
	Context context;
	int resoureId;
	
	
	
	public MyBaseAdapter(List<T> list,Context context,int resoureId){
		this.list=list;
		this.context=context;
		this.resoureId=resoureId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public T getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	
	//???
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder=ViewHolder.get(context, parent, resoureId, convertView);
		
		convert(viewHolder,getItem(position));
		return viewHolder.getConvertView();
		
	}


	public abstract void convert(ViewHolder viewHolder,T item);
}
