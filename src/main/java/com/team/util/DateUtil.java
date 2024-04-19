package com.team.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 날짜 포맷 - 김시우
 * 
 */
public class DateUtil {
	
	// 일자 Formatter
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
	
	// 일자 리스트
	public static String[] dateFormatterList = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy년 MM월 dd일", "yyyy.MM.dd", "yyyy MM dd"};
	
	//시간 Formatter
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss", Locale.KOREA);
	
	//시간(밀리초) Formatter
	private static SimpleDateFormat militimeFormatter = new SimpleDateFormat("HHmmssSSS", Locale.KOREA);
	
	//일시 Formatter
	private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	
	//TimeZone
	private static TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
	
	static
	{
		dateFormatter.setTimeZone(tz);
		timeFormatter.setTimeZone(tz);
		militimeFormatter.setTimeZone(tz);
		dateTimeFormatter.setTimeZone(tz);
	}
	
	
	// 현재 날짜를 Date 형으로 가져옴
	public static Date getDate()
	{
		return getSystemDate();
	}
	
	
	// 시스템 날짜 가져옴
	private static Date getSystemDate()
	{
		return new Date(System.currentTimeMillis());
	}
	

}
