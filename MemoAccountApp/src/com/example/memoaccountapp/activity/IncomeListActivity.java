package com.example.memoaccountapp.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
import com.example.memoaccountapp.adapter.MyBaseAdapter;
import com.example.memoaccountapp.adapter.ViewHolder;
import com.example.memoaccountapp.entity.Expend;
import com.example.memoaccountapp.entity.Income;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.view.RefreshListView;
import com.example.memoaccountapp.view.RefreshListView.OnRefreshCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class IncomeListActivity extends Activity {

	// listview
	@ViewInject(R.id.lv_income)
	RefreshListView lv_income;

	ArrayList<Income> incomes = new ArrayList<Income>();
	BaseAdapter adapter;
	private int count = 0;// 刷新的次数
	// 判断是否在刷新的布尔类型
	Boolean isRefresh = true;
	// 用来延迟线程
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_income_list);
		ViewUtils.inject(this);
		initView();
		initData();
	}

	public void initView() {
		setIncomeList();
	}

	public void initData() {
		// TODO Auto-generated method stub
		lv_income.setOnRefreshCallBack(new OnRefreshCallBack() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 延迟2秒
				isRefresh = false;
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						downToRefreshData();
						lv_income.completeRefresh();
					}
				}, 2000);
			}

			@Override
			public void onPull() {
				// TODO Auto-generated method stub
				isRefresh = false;
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						upToLoadData();
						lv_income.completePull();
					}
				}, 2000);

			}

		});
	}

	/**
	 * 获取列表
	 */
	public void setIncomeList() {
		// 连接网络获取数据
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "GetIncomesServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", Application.user.getUid() + "");
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(IncomeListActivity.this, "网络异常，请稍后······", 0)
						.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Income>>() {
					}.getType();
					incomes = gson.fromJson(result.result, type);
					adapter = new MyBaseAdapter<Income>(incomes,
							IncomeListActivity.this, R.layout.item_expend) {

						@Override
						public void convert(ViewHolder viewHolder, Income item) {
							// TODO Auto-generated method stub
							viewHolder.setText(R.id.tv_expend_time,
									item.getIntime());
							viewHolder.setText(R.id.tv_expend_account, "收入金额：+"
									+ item.getAccount());
							viewHolder.setText(R.id.tv_expend_type, "类型："
									+ item.getType());
							viewHolder.setText(R.id.tv_expend_address, "付款方："
									+ item.getAddress());
							viewHolder.setText(R.id.tv_expend_note, "备注："
									+ item.getNote());
						}
					};
					lv_income.setAdapter(adapter);
				}
			}
		});
	}

	/**
	 * 上拉加载
	 */
	private void upToLoadData() {

	}

	/**
	 * 下拉刷新
	 */
	private void downToRefreshData() {

	}

}
