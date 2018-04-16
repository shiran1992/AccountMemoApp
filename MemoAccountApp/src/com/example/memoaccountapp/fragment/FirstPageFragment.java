package com.example.memoaccountapp.fragment;

import java.io.Console;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.activity.AddExpendActivity;
import com.example.memoaccountapp.activity.AddIncomeActivity;
import com.example.memoaccountapp.activity.AddMemoActivity;
import com.example.memoaccountapp.activity.MyTypeActivity;
import com.example.memoaccountapp.activity.SelectRevenueActivity;
import com.example.memoaccountapp.activity.DataManageActivity;
import com.example.memoaccountapp.activity.ExpendListActivity;
import com.example.memoaccountapp.activity.IncomeListActivity;
import com.example.memoaccountapp.activity.MainActivity;
import com.example.memoaccountapp.activity.MemoListActivity;
import com.example.memoaccountapp.adapter.MyBaseAdapter;
import com.example.memoaccountapp.adapter.ViewHolder;
import com.example.memoaccountapp.entity.Revenue;
import com.example.memoaccountapp.util.Application;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator 首页fragment
 */
public class FirstPageFragment extends BaseFragment implements OnClickListener {
	View v;
	TextView tv_date;
	TextView tv_date_week;
	TextView tv_out_num_day;
	TextView tv_left_num;
	TextView tv_out_number;
	TextView tv_in_number;
	ListView lv_revenue;
	BaseAdapter adapter;
	// 储存信息的集合
	ArrayList<Revenue> res;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_firstpage, null);
		return v;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		v.findViewById(R.id.img_add_revenue).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(getActivity(),
								SelectRevenueActivity.class);
						startActivity(intent);
					}
				});
		lv_revenue = (ListView) v.findViewById(R.id.lv_revenue);
		tv_date = (TextView) v.findViewById(R.id.tv_date);
		tv_date_week = (TextView) v.findViewById(R.id.tv_date_week);
		tv_out_num_day = (TextView) v.findViewById(R.id.tv_out_num_day);
		tv_out_number = (TextView) v.findViewById(R.id.tv_out_number);
		tv_in_number = (TextView) v.findViewById(R.id.tv_in_number);
		tv_left_num = (TextView) v.findViewById(R.id.tv_left_num);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		Date date = new Date();
		int y = date.getYear();
		int m = date.getMonth();
		int d = date.getDate();
		tv_date.setText(y + "年" + (m + 1) + "月" + d + "日");
		int w = date.getDay();
		if (w == 0) {
			tv_date_week.setText("星期天");
		} else if (w == 1) {
			tv_date_week.setText("星期一");
		} else if (w == 2) {
			tv_date_week.setText("星期二");
		} else if (w == 3) {
			tv_date_week.setText("星期三");
		} else if (w == 4) {
			tv_date_week.setText("星期四");
		} else if (w == 5) {
			tv_date_week.setText("星期五");
		} else if (w == 6) {
			tv_date_week.setText("星期六");
		}
	}
	
	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setRevenues();
		setMonthFee();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		}
	}

	/**
	 * 获取列表
	 */
	public void setRevenues() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "GetTodayRevenueServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", Application.user.getUid() + "");
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "网络异常，请稍后······", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Revenue>>() {
					}.getType();
					res = gson.fromJson(result.result, type);
					setOutAcc();
					adapter = new MyBaseAdapter<Revenue>(res, getActivity(),
							R.layout.item_revenue) {

						@Override
						public void convert(ViewHolder viewHolder, Revenue item) {
							// TODO Auto-generated method stub
							viewHolder.setText(R.id.tv_rev_title, item
									.getType().getName());
							if (item.getType().getType() == 1) {
								
								viewHolder.setImageResource(R.id.img_rev_type,
										R.drawable.icon_in);
								
								viewHolder.setText(R.id.tv_rev_acc,
										item.getAccount());
							} else {
								viewHolder.setText(R.id.tv_rev_acc,
										"-" + item.getAccount());
							}

						}
					};
					lv_revenue.setAdapter(adapter);
				}
			}

		});
	}
	
	/**
	 * 本月支出
	 */
	public void setMonthFee() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "GetMonthFeeServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", Application.user.getUid() + "");
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "网络异常，请稍后······", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					String[] arr = result.result.split(",");
					tv_out_number.setText(arr[0]);
					tv_in_number.setText(arr[1]);
					tv_left_num.setText(""+(Integer.parseInt(arr[1]) + Integer.parseInt(arr[0])));
				}
			}

		});
	}
	
	//设置今日支出
	void setOutAcc(){
		int out = 0;
		for(int i = 0; i<res.size();i++){
			Revenue r = res.get(i);
			if(r.getType().getType() == 0){
				out = out + Integer.parseInt(r.getAccount());
			}
		}
		tv_out_num_day.setText(out + "");
	}
}
