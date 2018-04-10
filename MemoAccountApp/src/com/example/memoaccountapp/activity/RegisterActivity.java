package com.example.memoaccountapp.activity;

import java.util.Random;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.id;
import com.example.memoaccountapp.R.layout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.util.Tus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class RegisterActivity extends Activity implements OnClickListener {

	// 号码输入框
	@ViewInject(R.id.et_phonenum_register)
	EditText et_phonenum;
	// 密码输入框
	@ViewInject(R.id.et_psw_register)
	EditText et_psw;
	// 昵称输入框
	@ViewInject(R.id.et_name_register)
	EditText et_name;
	// 验证码输入框
	@ViewInject(R.id.et_verication_code)
	EditText et_verication_code;
	// 获取验证码按钮
	@ViewInject(R.id.get_verication)
	Button get_verication;
	// 注册按钮
	@ViewInject(R.id.btn_register)
	Button btn_register;
	// 返回按钮
	@ViewInject(R.id.im_return_register)
	ImageView im_return_register;

	String phonenum;
	String password;
	String name;
	int code;
	// 是否可以注册
	boolean flag_isregister = true;
	// 是否注册成功
	boolean flag_register = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_register);
		ViewUtils.inject(this);
		init();
		createCode();
	}

	// 初始化数据
	public void init() {
		get_verication.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		im_return_register.setOnClickListener(this);
	}

	// 创建验证码
	public void createCode() {
		Random rand = new Random();
		int a = rand.nextInt(20);
		int b = rand.nextInt(20);
		code = a + b;
		get_verication.setText(a + "+" + b + "=?");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.get_verication:
			get_verication.setSelected(true);
			// 重新获取验证码
			createCode();
			break;
		case R.id.btn_register:
			btn_register.setSelected(true);
			if (et_verication_code.getText().toString().trim()
					.equals(code + "")) {
				phonenum = et_phonenum.getText().toString().trim();
				// 手机号码正则表达式
				String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$";
				// 判断此手机号码是否已经注册
				if (phonenum == null || !phonenum.matches(pattern)) {
					new Tus(RegisterActivity.this).toast("手机号不正确！");
					return;
				}
				// android端输入的验证码
				// 验证对应手机，返回的短信验证码，会回调前面的afterEvent方法
				phonenum = et_phonenum.getText().toString().trim();
				password = et_psw.getText().toString().trim();
				name = et_name.getText().toString().trim();
				if (phonenum.length() == 0) {
					new Tus(RegisterActivity.this).toast("手机号码不可以为空");
				} else if (password.length() == 0) {
					new Tus(RegisterActivity.this).toast("密码不可以为空");
				} else if (name.length() == 0) {
					new Tus(RegisterActivity.this).toast("昵称不可以为空");
				} else if (register(phonenum, password, name)) {
					Intent intent = new Intent();
					intent.putExtra("phonenum", et_phonenum.getText()
							.toString().trim());
					intent.putExtra("password", et_psw.getText().toString()
							.trim());
					intent.putExtra("name", et_name.getText().toString().trim());
					setResult(RESULT_OK, intent);
					finish();
				} else {
					new Tus(RegisterActivity.this).toast("注册失败，请重试！");
				}
			} else {
				new Tus(RegisterActivity.this).toast("验证码错误，请重试！");
			}
			break;

		case R.id.im_return_register:

			finish();
			break;
		}
	}

	/**
	 * 注册用户
	 */
	public boolean register(String phonenum, String password, String name) {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "RegisterServlet";
		Log.i("sysout", url);
		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("phone", phonenum);
		params.addBodyParameter("password", password);
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				new Tus(RegisterActivity.this).toast("网络异常，请稍后······");
			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				// TODO Auto-generated method stub
				if (result.result.equals("false")) {
					flag_register = false;
				}
			}
		});
		return flag_register;
	}

}
