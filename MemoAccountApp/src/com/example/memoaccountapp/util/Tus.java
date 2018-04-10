package com.example.memoaccountapp.util;

import android.content.Context;
import android.widget.Toast;

public class Tus {

	Context context;
	
	public Tus(Context context) {
		super();
		this.context = context;
	}

	public void toast(String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
