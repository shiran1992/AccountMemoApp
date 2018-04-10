package com.example.memoaccountapp.activity;

import java.lang.reflect.Type;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
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
import com.example.memoaccountapp.activity.RegisterActivity;
import com.example.memoaccountapp.entity.User;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.util.Tus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {

	final static int REGISTER = 1;
	final static int FORGETPASSWORD = 1;

	// 电话号码输入框
	@ViewInject(R.id.et_phonenum)
	EditText et_phonenum;
	// 密码输入框
	@ViewInject(R.id.et_psw)
	EditText et_password;
	// 登陆按钮
	@ViewInject(R.id.btn_login)
	Button btn_login;
	// “注册”
	@ViewInject(R.id.login_register)
	TextView tv_register;

	SharedPreferences sp;
	String phonenum;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);

		init();
	}

	// 初始化数据
	public void init() {
		if ((sp = getSharedPreferences("userinfo.propertity", MODE_PRIVATE)) != null) {
			et_phonenum.setText(sp.getString("phonenum", null));
			et_password.setText(sp.getString("password", null));
		}

		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 点击登录
		case R.id.btn_login:

			// 改变按钮点击状态̬
			btn_login.setSelected(true);
			// 登陆
			selectlogin();
			break;
		// 点击注册
		case R.id.login_register:
			Intent intent2 = new Intent();
			intent2.setClass(this, RegisterActivity.class);
			startActivityForResult(intent2, REGISTER);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REGISTER && resultCode == RESULT_OK) {
			et_phonenum.setText(data.getStringExtra("phonenum").toString());
			et_password.setText(data.getStringExtra("password").toString());
		} else if (requestCode == FORGETPASSWORD && resultCode == RESULT_OK) {
			et_phonenum.setText(data.getStringExtra("phonenum").toString());
			et_password.setText(data.getStringExtra("password").toString());
		}
	}

	/**
	 * 点击登陆
	 */
	public void selectlogin() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "LoginServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("phone", et_phonenum.getText().toString()
				.trim());
		params.addBodyParameter("password", et_password.getText().toString()
				.trim());
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				new Tus(LoginActivity.this).toast("网络异常，请稍后······");
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (!result.result.equals("false")) {
					Gson gson = new Gson();
					Type type = new TypeToken<User>() {
					}.getType();
					User user = gson.fromJson(result.result, type);
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					Application.user = user;
					saveuserinfo(LoginActivity.this, et_phonenum.getText()
							.toString().trim(), et_password.getText()
							.toString().trim());
				} else {
					new Tus(LoginActivity.this).toast("手机号或密码有误，请重新输入······");
				}
			}
		});
	}

	/**
	 * 保存登陆账号和密码到本地
	 */
	public void saveuserinfo(Context context, String phonenum, String password) {
		SharedPreferences sp = getSharedPreferences("userinfo.propertity",
				MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString("phonenum", phonenum);
		edit.putString("password", password);
		// 把数据给保存到sp里面
		edit.commit();

	}

}
