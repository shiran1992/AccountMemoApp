package com.example.memoaccountapp.adapter;

import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewHolder {

	Context context;

	View mConvertView;

	SparseArray<View> mViews;

	private ViewHolder(Context context, ViewGroup parent, int resoureId) {

		mViews = new SparseArray<View>();

		mConvertView = LayoutInflater.from(context).inflate(resoureId, parent,
				false);
		mConvertView.setTag(this);
		this.context = context;
	}

	public static ViewHolder get(Context context, ViewGroup parent,
			int resoureId, View convertView) {
		if (convertView == null) {

			return new ViewHolder(context, parent, resoureId);
		}

		return (ViewHolder) convertView.getTag();

	}

	public <T extends View> T getView(int viewId) {
		View v = mViews.get(viewId);

		if (v == null) {

			v = mConvertView.findViewById(viewId);

			mViews.put(viewId, v);
		}
		return (T) v;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public void setText(int viewId, String text) {

		TextView tv = getView(viewId);

		tv.setText(text);
	}

	public void setImageResource(int viewId, int drawableId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(drawableId);
	}

	public ViewHolder setBackGroundResource(int viewId, int drawableId)  
	   {  
		   RelativeLayout view = getView(viewId);  
		   view.setBackgroundResource(drawableId);  
		   return this;  
	   }  
	// 通过uri来对图片进行赋值
	public void setImageByUri(int viewId, String uri) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(getView(viewId), uri);

	}

	 /* 
     * 通过URL加载带缓存的网络图片
     */  
    public ViewHolder setImageByUrl(int viewId, String url)  
    {  
//    	BitmapUtils bitmapUtils=new BitmapUtils(mContext);
//        bitmapUtils.display(getView(viewId), url);
    	Picasso.with(context).load(url).into((ImageView) getView(viewId));
    //	Picasso.with(context).load(url).into((ImageView)getView(viewId));
        return this;
    }
    
	// 通过uri来对图片进行赋值
	public void setheadImageByUri(int viewId, String uri) {
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDefaultBitmapMaxSize(50, 50);
		bitmapUtils.display(getView(viewId), uri);

	}

	/*
	 * 屏幕适配
	 */
	public class CropSquareTransformation implements Transformation {
		@Override
		public Bitmap transform(Bitmap source) {
			System.out.println();
			System.out.println();
			System.out.println();
			WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			int screenWidth = wm.getDefaultDisplay().getWidth();
			int startX = source.getWidth();
			int startY = source.getHeight();
			int endY = (int) ((screenWidth / source.getWidth()) * source
					.getHeight());
			System.out.println();
			System.out.println();
			System.out.println();
			// int size = Math.max(source.getWidth(), source.getHeight());
			// int x = (source.getWidth() - size) / 2;
			// int y = (source.getHeight() - size) / 2;
			Bitmap result = Bitmap.createBitmap(source, 0, 0, 500, 600);
			if (result != source) {
				source.recycle();
			}
			return result;
		}

		@Override
		public String key() {
			return "square()";
		}
	}

}
