package com.example.memoaccountapp.fragment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.activity.AddTypeActivity;
import com.example.memoaccountapp.activity.MyTypeActivity;
import com.example.memoaccountapp.activity.RevenueListActivity;
import com.example.memoaccountapp.util.Application;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator 我的fragment
 */
public class MineFragment extends BaseFragment implements OnClickListener {

	View v;
	TextView tv_name;
	RelativeLayout rl_manage_type;
	RelativeLayout rl_revenue;
	TextView tv_in_num;
	TextView tv_out_num;
	TextView tv_left_num;
	TextView tv_month_num;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_mine, null);
		return v;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		tv_name = (TextView) v.findViewById(R.id.tv_name);
		rl_manage_type = (RelativeLayout) v.findViewById(R.id.rl_manage_type);
		rl_revenue = (RelativeLayout) v.findViewById(R.id.rl_revenue);
		tv_in_num = (TextView) v.findViewById(R.id.tv_in_num);
		tv_out_num = (TextView) v.findViewById(R.id.tv_out_num);
		tv_left_num = (TextView) v.findViewById(R.id.tv_left_num);
		tv_month_num = (TextView) v.findViewById(R.id.tv_month_num);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		if (Application.user.getName() != null) {
			tv_name.setText(Application.user.getName());
		}
		tv_month_num.setText((new Date().getMonth() + 1) + "");
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		rl_manage_type.setOnClickListener(this);
		rl_revenue.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setMonthFee();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rl_manage_type:
			intent.setClass(getActivity(), MyTypeActivity.class);
			break;
		case R.id.rl_revenue:
			intent.setClass(getActivity(), RevenueListActivity.class);
		}
		startActivity(intent);
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
					tv_out_num.setText(arr[0]);
					tv_in_num.setText(arr[1]);
					tv_left_num.setText(""+(Integer.parseInt(arr[1]) + Integer.parseInt(arr[0])));
				}
			}

		});
	}

}
