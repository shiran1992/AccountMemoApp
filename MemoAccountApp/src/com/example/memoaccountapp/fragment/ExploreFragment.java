package com.example.memoaccountapp.fragment;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.memoaccountapp.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * 
 * @author Administrator
 *首页fragment
 */
public class ExploreFragment extends BaseFragment implements OnClickListener{
	 
	View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_explore, null);
		return v;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
	   
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		}
	}
	
}
