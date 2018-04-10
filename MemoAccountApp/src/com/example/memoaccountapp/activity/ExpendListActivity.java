package com.example.memoaccountapp.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
import com.example.memoaccountapp.adapter.MyBaseAdapter;
import com.example.memoaccountapp.adapter.ViewHolder;
import com.example.memoaccountapp.entity.Expend;
import com.example.memoaccountapp.view.RefreshListView;
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
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.view.RefreshListView;
import com.example.memoaccountapp.view.RefreshListView.OnRefreshCallBack;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ExpendListActivity extends Activity{
	// listview
	@ViewInject(R.id.lv_expend)
	RefreshListView lv_expend;
	
	ArrayList<Expend> expends = new ArrayList<Expend>();
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
		setContentView(R.layout.activity_expend_list);
		ViewUtils.inject(this);
		initView();
		initData();
	}
	
	public void initView() {
		setExpendList();
	}
	
	public void initData() {
		// TODO Auto-generated method stub
		lv_expend.setOnRefreshCallBack(new OnRefreshCallBack() {

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
						lv_expend.completeRefresh();
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
						lv_expend.completePull();
					}
				}, 2000);

			}

		});
	}

	/**
	 * 获取列表
	 */
	public void setExpendList() {
		// 连接网络获取数据
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "GetExpendsServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", Application.user.getUid() + "");
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(ExpendListActivity.this, "网络异常，请稍后······", 0)
						.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					Gson gson = new Gson();
					Type type = new TypeToken<ArrayList<Expend>>() {
					}.getType();
					expends = gson.fromJson(result.result, type);
					adapter = new MyBaseAdapter<Expend>(expends,
							ExpendListActivity.this, R.layout.item_expend) {

						@Override
						public void convert(ViewHolder viewHolder, Expend item) {
							// TODO Auto-generated method stub
							viewHolder.setText(R.id.tv_expend_time, item.getOuttime());
							viewHolder.setText(R.id.tv_expend_account, "支出金额：-"+item.getAccount());
							viewHolder.setText(R.id.tv_expend_type, "类型："+item.getType());
							viewHolder.setText(R.id.tv_expend_address, "地点："+item.getAddress());
							viewHolder.setText(R.id.tv_expend_note, "备注："+item.getNote());
						}
					};
					lv_expend.setAdapter(adapter);
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
