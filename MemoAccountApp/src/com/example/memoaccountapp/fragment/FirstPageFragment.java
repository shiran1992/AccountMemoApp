package com.example.memoaccountapp.fragment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.activity.AddExpendActivity;
import com.example.memoaccountapp.activity.AddIncomeActivity;
import com.example.memoaccountapp.activity.AddMemoActivity;
import com.example.memoaccountapp.activity.DataManageActivity;
import com.example.memoaccountapp.activity.ExpendListActivity;
import com.example.memoaccountapp.activity.IncomeListActivity;
import com.example.memoaccountapp.activity.MainActivity;
import com.example.memoaccountapp.activity.MemoListActivity;
import com.example.memoaccountapp.util.Application;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Administrator 首页fragment
 */
public class FirstPageFragment extends BaseFragment implements OnClickListener {
	// 昵称
	@ViewInject(R.id.tv_name)
	TextView tv_name;
	// 新增支出
	@ViewInject(R.id.linear_add_expend)
	LinearLayout linear_add_expend;

	// 新增收入
	@ViewInject(R.id.linear_add_income)
	LinearLayout linear_add_income;

	// 我的支出
	@ViewInject(R.id.linear_expend_list)
	LinearLayout linear_expend_list;

	// 我的收入
	@ViewInject(R.id.linear_income_list)
	LinearLayout linear_income_list;

	// 数据管理
	@ViewInject(R.id.linear_data_manage)
	LinearLayout linear_data_manage;

	// 增加收支便签
	@ViewInject(R.id.linear_show_memo)
	LinearLayout linear_show_memo;

	// 收支便签列表
	@ViewInject(R.id.linear_memo_list)
	LinearLayout linear_memo_list;

	// 退出
	@ViewInject(R.id.linear_exit)
	LinearLayout linear_exit;
	View v;

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

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

	}

	// 初始化数据
	public void init() {
		linear_add_expend.setOnClickListener(this);
		linear_add_income.setOnClickListener(this);
		linear_expend_list.setOnClickListener(this);
		linear_income_list.setOnClickListener(this);
		linear_memo_list.setOnClickListener(this);
		linear_data_manage.setOnClickListener(this);
		linear_show_memo.setOnClickListener(this);
		linear_exit.setOnClickListener(this);
		tv_name.setText(Application.user.getName());
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.linear_add_expend:
			intent.setClass(getActivity(), AddExpendActivity.class);
			break;
		case R.id.linear_add_income:
			intent.setClass(getActivity(), AddIncomeActivity.class);
			break;
		case R.id.linear_expend_list:
			intent.setClass(getActivity(), ExpendListActivity.class);
			break;
		case R.id.linear_income_list:
			intent.setClass(getActivity(), IncomeListActivity.class);
			break;
		case R.id.linear_memo_list:
			intent.setClass(getActivity(), MemoListActivity.class);
			break;

		case R.id.linear_data_manage:
			intent.setClass(getActivity(), DataManageActivity.class);
			break;
		case R.id.linear_show_memo:
			intent.setClass(getActivity(), AddMemoActivity.class);
			break;
		case R.id.linear_exit:
			getActivity().finish();
			return;
		}
		startActivity(intent);
	}

}