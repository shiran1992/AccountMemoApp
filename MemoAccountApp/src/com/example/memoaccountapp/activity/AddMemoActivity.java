package com.example.memoaccountapp.activity;

import java.util.Date;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
import com.example.memoaccountapp.entity.Expend;
import com.example.memoaccountapp.entity.Memo;
import com.example.memoaccountapp.util.Application;
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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMemoActivity extends Activity implements OnClickListener {

	// 内容
	@ViewInject(R.id.et_memo_content)
	EditText et_memo;

	// 保存
	@ViewInject(R.id.btn_save_memo)
	Button btn_save_memo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_add_memo);
		ViewUtils.inject(this);
		init();
	}

	// 初始化数据
	public void init() {
		btn_save_memo.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_save_memo:
			saveExpendInfo();
			break;

		default:
			break;
		}
	}

	// 保存便签信息
	public void saveExpendInfo() {
		String content = et_memo.getText().toString().trim();
		if (content.length() == 0) {
			Toast.makeText(AddMemoActivity.this, "金额不可以为空", Toast.LENGTH_SHORT)
					.show();
			return;
		} else {
			HttpUtils http = new HttpUtils();
			String url = Application.servlet + "AddMemoServlet";
			RequestParams params = new RequestParams();
			Memo m = new Memo();
			m.setContent(content);
			m.setMtime(new Date().getTime() + "");
			m.setUser(Application.user);
			Gson gson = new Gson();
			params.addBodyParameter("memoinfo", gson.toJson(m));
			http.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							new Tus(AddMemoActivity.this)
									.toast("网络异常，请稍后······");
						}

						@Override
						public void onSuccess(ResponseInfo<String> result) {
							// TODO Auto-generated method stub
							Toast.makeText(AddMemoActivity.this, "保存成功！",
									Toast.LENGTH_SHORT).show();
							finish();
						}
					});
		}
	}
}
