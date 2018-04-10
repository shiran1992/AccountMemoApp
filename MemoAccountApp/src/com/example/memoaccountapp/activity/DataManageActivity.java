package com.example.memoaccountapp.activity;

import java.text.DecimalFormat;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
import com.example.memoaccountapp.entity.Expend;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.util.FormatTime;
import com.example.memoaccountapp.util.Tus;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataManageActivity extends Activity {

	// 收入
	@ViewInject(R.id.tv_account_in)
	TextView tv_account_in;

	// 支出
	@ViewInject(R.id.tv_account_out)
	TextView tv_account_out;

	// 总收入
	@ViewInject(R.id.tv_in_out)
	TextView tv_in_out;

	// 状态
	@ViewInject(R.id.tv_in_out_state)
	TextView tv_in_out_state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_data_manage);
		ViewUtils.inject(this);
		initView();
	}

	public void initView() {
		getOutIn();
	}

	// 获取数据
	public void getOutIn() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "DataManagerServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", Application.user.getUid() + "");
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				new Tus(DataManageActivity.this).toast("网络异常，请稍后······");
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result != null) {
					String res = result.result;
					String[] resArr = res.split(",");
					String in = resArr[0].split(":")[1];
					String out = resArr[1].split(":")[1];
					tv_account_in.setText(in);
					tv_account_out.setText(out);
					
					Long in_long = Long.parseLong(in);
					Long out_long = Long.parseLong(out);
					Long all = in_long - out_long;
					tv_in_out.setText(all + "");
					
					calInOutState(in_long, out_long);
				}
			}
		});
	}
	
	//计算收支状态
	public void calInOutState(Long in, Long out){
		String state = "";
		if (out >= in) {
			state = "收支赤子";
			tv_in_out_state.setTextColor(Color.rgb(255, 0, 0));
		} else if ((out * 1.0 / in) > 0.7) {
			state = "严重超支";
			tv_in_out_state.setTextColor(Color.rgb(237, 74, 74));
		} else if ((out * 1.0 / in) > 0.5) {
			state = "稍微超支";
			tv_in_out_state.setTextColor(Color.rgb(255, 208, 0));
		} else if ((out * 1.0 / in) > 0.3) {
			state = "收支平衡";
			tv_in_out_state.setTextColor(Color.rgb(8, 132, 63));
		} else {
			state = "收支富余";
			tv_in_out_state.setTextColor(Color.rgb(6, 211, 0));
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		tv_in_out_state.setText(state + "(" +df.format(out * 1.0 / in) + ")");
	}

}
