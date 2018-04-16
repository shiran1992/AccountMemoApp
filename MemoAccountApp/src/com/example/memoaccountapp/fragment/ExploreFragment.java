package com.example.memoaccountapp.fragment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.activity.WebViewActivity;
import com.example.memoaccountapp.adapter.MyBaseAdapter;
import com.example.memoaccountapp.adapter.ViewHolder;
import com.example.memoaccountapp.entity.Explore;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.view.RefreshListView;
import com.example.memoaccountapp.view.RefreshListView.OnRefreshCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 */
public class ExploreFragment extends BaseFragment implements OnItemClickListener {

	View v;
	BaseAdapter adapter;
	ListView lv_myfriends;
	// 储存信息的集合
	ArrayList<Explore> concerns;

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

		lv_myfriends = (ListView) v.findViewById(R.id.lv_myfriends);
		lv_myfriends.setOnItemClickListener(this);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		setconcernedfriends();
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取列表
	 */
	public void setconcernedfriends() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "GetExploresServlet";
		RequestParams params = new RequestParams();
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
					Type type = new TypeToken<ArrayList<Explore>>() {
					}.getType();
					concerns = gson.fromJson(result.result, type);
					adapter = new MyBaseAdapter<Explore>(concerns,
							getActivity(), R.layout.item_explore) {

						@Override
						public void convert(ViewHolder viewHolder, Explore item) {
							// TODO Auto-generated method stub
							viewHolder.setText(R.id.tv_explore_title, item.getTitle());
							viewHolder.setImageByUri(R.id.img_explore, item.getImg());
						}

					};
					lv_myfriends.setAdapter(adapter);
				}
			}

		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
			 Intent intent = new Intent();
			 intent.setClass(getActivity(), WebViewActivity.class);
			 Gson gson = new Gson();
			 intent.putExtra("explore",gson.toJson(concerns.get(position)));
			 startActivity(intent);
	}
}
