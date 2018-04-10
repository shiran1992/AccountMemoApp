package com.example.memoaccountapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTime {

	public static String format(String datastr){
		long datalong = Long.parseLong(datastr);
		Date date = new Date(datalong);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		return sdf.format(date)+"";
	}
	public static String format1(String datastr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		return sdf.parse(datastr).getTime()+"";
	}
	
	public static String YMDformat(String datastr) throws ParseException{
		long datalong = Long.parseLong(datastr);
		Date date = new Date(datalong);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		return sdf.parse(datastr).getTime()+"";
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(FormatTime.YMDformat("2016-5-1"));
	}
}
