package com.example.memoaccountapp.activity;

import java.util.Calendar;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.R.layout;
import com.example.memoaccountapp.R.menu;
import com.example.memoaccountapp.entity.Expend;
import com.example.memoaccountapp.entity.Income;
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
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddIncomeActivity extends Activity implements OnClickListener{
	// 金额
		@ViewInject(R.id.et_account_in)
		EditText et_account;

		// 类型
		@ViewInject(R.id.et_type_in)
		EditText et_type;

		// 时间
		@ViewInject(R.id.tv_income_time)
		TextView tv_income_time;

		// 地点
		@ViewInject(R.id.et_address_in)
		EditText et_address;

		// 备注
		@ViewInject(R.id.et_note_in)
		EditText et_note;

		// 保存
		@ViewInject(R.id.btn_save_income)
		Button btn_save_income;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 除去标题栏
		setContentView(R.layout.activity_add_income);
		ViewUtils.inject(this);
		initView();
		init();
	}
	
	public void initView() {
		tv_income_time.setText(FormatTime.format(System.currentTimeMillis()
				+ ""));
	}

	// 初始化数据
	public void init() {
		tv_income_time.setOnClickListener(this);
		btn_save_income.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tv_income_time:
			showTime();
			break;
		case R.id.btn_save_income:
			saveIncomeInfo();
			break;

		default:
			break;
		}
	}

	// 选择时间
	void showTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				tv_income_time.setText(year + "-" + (monthOfYear + 1) + "-"
						+ dayOfMonth);
			}
		}, year, month, day).show();
	}

	// 保存收入信息
	public void saveIncomeInfo() {
		String account = et_account.getText().toString().trim();
		String type = et_type.getText().toString().trim();
		String address = et_address.getText().toString().trim();
		String time = tv_income_time.getText().toString().trim();
		String note = et_note.getText().toString().trim();
		if (account.length() == 0) {
			Toast.makeText(AddIncomeActivity.this, "金额不可以为空",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (type.length() == 0) {
			Toast.makeText(AddIncomeActivity.this, "类型不可以为空",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (address.length() == 0) {
			Toast.makeText(AddIncomeActivity.this, "付款方不可以为空",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (time.length() == 0) {
			Toast.makeText(AddIncomeActivity.this, "时间不可以为空",
					Toast.LENGTH_SHORT).show();
			return;
		} else if (note.length() == 0) {
			Toast.makeText(AddIncomeActivity.this, "备注不可以为空",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			HttpUtils http = new HttpUtils();
			String url = Application.servlet + "AddIncomeServlet";
			RequestParams params = new RequestParams();
			Income income = new Income();
			income.setAccount(account);
			income.setType(type);
			income.setAddress(address);
			income.setIntime(time);
			income.setUser(Application.user);
			income.setNote(note);
			Gson gson = new Gson();
			params.addBodyParameter("incomeinfo", gson.toJson(income));
			http.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							new Tus(AddIncomeActivity.this)
									.toast("网络异常，请稍后······");
						}

						@Override
						public void onSuccess(ResponseInfo<String> result) {
							// TODO Auto-generated method stub
							Toast.makeText(AddIncomeActivity.this, "保存成功！",
									Toast.LENGTH_SHORT).show();
							finish();
						}
					});
		}
	}

}
