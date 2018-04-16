package com.example.memoaccountapp.fragment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

import com.example.memoaccountapp.R;
import com.example.memoaccountapp.activity.RevenueListActivity;
import com.example.memoaccountapp.adapter.MyBaseAdapter;
import com.example.memoaccountapp.adapter.ViewHolder;
import com.example.memoaccountapp.entity.Revenue;
import com.example.memoaccountapp.util.Application;
import com.example.memoaccountapp.util.FormatTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * 
 * @author Administrator 首页fragment
 */
public class DetailFragment extends BaseFragment implements OnClickListener {

	View v;
	private LineChartView lineChart;
	private LineChartView lineChart1;
	String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };// X轴的标注
	int[] weather = { 0, 0, 0, 0, 0, 0, 0 };// 图表的数据
	int[] weather1 = { 0, 0, 0, 0, 0, 0, 0 };// 图表的数据
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
	private List<PointValue> mPointValues1 = new ArrayList<PointValue>();
	private List<AxisValue> mAxisValues1 = new ArrayList<AxisValue>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_detail, null);
		return v;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		lineChart = (LineChartView) v.findViewById(R.id.line_chart);
		lineChart1 = (LineChartView) v.findViewById(R.id.line_chart1);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		}
	}

	/**
	 * 初始化LineChart的一些设置
	 */
	private void initLineChart() {
		Line line = new Line(mPointValues).setColor(Color.WHITE)
				.setCubic(false); // 折线的颜色
		List<Line> lines = new ArrayList<Line>();
		line.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
											// ：ValueShape.SQUARE
											// ValueShape.CIRCLE
											// ValueShape.SQUARE）
		line.setCubic(true);// 曲线是否平滑
		line.setFilled(true);// 是否填充曲线的面积
		// line.setHasLabels(true);//曲线的数据坐标是否加上备注
		line.setHasLabelsOnlyForSelected(true);// 点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);// 是否用直线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(true);// 是否显示圆点 如果为false 则没有原点只有点显示
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		// 坐标轴
		Axis axisX = new Axis(); // X轴
		axisX.setHasTiltedLabels(true);
		axisX.setTextColor(Color.WHITE); // 设置字体颜色
		axisX.setName("本周消费"); // 表格名称
		axisX.setTextSize(7);// 设置字体大小
		axisX.setMaxLabelChars(7); // 最多几个X轴坐标
		axisX.setValues(mAxisValues); // 填充X轴的坐标名称
		data.setAxisXBottom(axisX); // x 轴在底部
		// data.setAxisXTop(axisX); //x 轴在顶部

		Axis axisY = new Axis(); // Y轴
		axisY.setMaxLabelChars(7); // 默认是3，只能看最后三个数字
		axisY.setName("温度");// y轴标注
		axisY.setTextSize(7);// 设置字体大小

		data.setAxisYLeft(axisY); // Y轴设置在左边
		// data.setAxisYRight(axisY); //y轴设置在右边

		// 设置行为属性，支持缩放、滑动以及平移
		lineChart.setInteractive(true);
		lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
		lineChart.setContainerScrollEnabled(true,
				ContainerScrollType.HORIZONTAL);
		lineChart.setLineChartData(data);
		lineChart.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 初始化LineChart的一些设置
	 */
	private void initLineChart1() {
		Line line = new Line(mPointValues1).setColor(Color.WHITE)
				.setCubic(false); // 折线的颜色
		List<Line> lines = new ArrayList<Line>();
		line.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
											// ：ValueShape.SQUARE
											// ValueShape.CIRCLE
											// ValueShape.SQUARE）
		line.setCubic(true);// 曲线是否平滑
		line.setFilled(true);// 是否填充曲线的面积
		// line.setHasLabels(true);//曲线的数据坐标是否加上备注
		line.setHasLabelsOnlyForSelected(true);// 点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);// 是否用直线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(true);// 是否显示圆点 如果为false 则没有原点只有点显示
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		// 坐标轴
		Axis axisX = new Axis(); // X轴
		axisX.setHasTiltedLabels(true);
		axisX.setTextColor(Color.WHITE); // 设置字体颜色
		axisX.setName("本周收入"); // 表格名称
		axisX.setTextSize(7);// 设置字体大小
		axisX.setMaxLabelChars(7); // 最多几个X轴坐标
		axisX.setValues(mAxisValues1); // 填充X轴的坐标名称
		data.setAxisXBottom(axisX); // x 轴在底部
		// data.setAxisXTop(axisX); //x 轴在顶部

		Axis axisY = new Axis(); // Y轴
		axisY.setMaxLabelChars(7); // 默认是3，只能看最后三个数字
		axisY.setName("温度");// y轴标注
		axisY.setTextSize(7);// 设置字体大小

		data.setAxisYLeft(axisY); // Y轴设置在左边
		// data.setAxisYRight(axisY); //y轴设置在右边

		// 设置行为属性，支持缩放、滑动以及平移
		lineChart1.setInteractive(true);
		lineChart1.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
		lineChart1.setContainerScrollEnabled(true,
				ContainerScrollType.HORIZONTAL);
		lineChart1.setLineChartData(data);
		lineChart1.setVisibility(View.VISIBLE);
	}


	/**
	 * X 轴的显示
	 */
	private void getAxisLables() {
		for (int i = 0; i < weeks.length; i++) {
			mAxisValues.add(new AxisValue(i).setLabel(weeks[i]));
		}
	}

	/**
	 * 图表的每个点的显示
	 */
	private void getAxisPoints() {
		for (int i = 0; i < weather.length; i++) {
			mPointValues.add(new PointValue(i, weather[i]));
		}
	}
	
	/**
	 * X 轴的显示
	 */
	private void getAxisLables1() {
		for (int i = 0; i < weeks.length; i++) {
			mAxisValues1.add(new AxisValue(i).setLabel(weeks[i]));
		}
	}

	/**
	 * 图表的每个点的显示
	 */
	private void getAxisPoints1() {
		for (int i = 0; i < weather1.length; i++) {
			mPointValues1.add(new PointValue(i, weather1[i]));
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getWeekRevenues();
		getWeekInRevenues();
	}

	/**
	 * 获取列表
	 */
	public void getWeekRevenues() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "WeekOutServlet";
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
					String[] strArr = result.result.split(",");
					for(int i = 0; i<strArr.length; i++){
						weather[i] = Integer.parseInt(strArr[i]);
					}
					getAxisLables();// 获取x轴的标注
					getAxisPoints();// 获取坐标点
					initLineChart();// 初始化
				}
			}

		});
	}
	
	/**
	 * 获取列表
	 */
	public void getWeekInRevenues() {
		HttpUtils http = new HttpUtils();
		String url = Application.servlet + "WeekInServlet";
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
					String[] strArr = result.result.split(",");
					for(int i = 0; i<strArr.length; i++){
						weather1[i] = Integer.parseInt(strArr[i]);
					}
					getAxisLables1();// 获取x轴的标注
					getAxisPoints1();// 获取坐标点
					initLineChart1();// 初始化
				}
			}

		});
	}
}
