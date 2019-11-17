package io.swagger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.glassfish.jersey.internal.util.Base64;

public class Utility {

	public static Calendar convertStringToCalendar(String DateString) {
		return convertStringToCalendar(DateString, "yyyy-MM-dd hh:mm:ss");
	}
	
	public static DateFormat getSimpleDateFormat(String format) {
		return new SimpleDateFormat(format);
	}

	public static Calendar convertStringToCalendar(String DateString, String format) {
		Calendar calendarObj = Calendar.getInstance();
		try{
		   //Setting the date to the given date
			calendarObj.setTime(getSimpleDateFormat(format).parse(DateString));
		}catch(ParseException e){
			e.printStackTrace();
		 }
		return calendarObj;
	}
	
	public static String addDaysToDate(String DateString, int numberOfDays, String format) {
		Calendar calendarObj = convertStringToCalendar(DateString, format);
		calendarObj.add(Calendar.DAY_OF_MONTH, numberOfDays);
		return getSimpleDateFormat(format).format(calendarObj.getTime()); 
	}

	public static String addYearsToDate(String DateString, int numberOfYears, String format) {
		Calendar calendarObj = convertStringToCalendar(DateString, format);
		calendarObj.add(Calendar.YEAR, numberOfYears);
		return getSimpleDateFormat(format).format(calendarObj.getTime()); 
	}
	
	public static String addMonthToDate(String DateString, int numberOfMonths, String format) {
		Calendar calendarObj = convertStringToCalendar(DateString, format);
		calendarObj.add(Calendar.MONTH, numberOfMonths);
		return getSimpleDateFormat(format).format(calendarObj.getTime()); 
	}

	public static Integer convertObjectToInteger(Object obj) {
		Integer convertedObj = null;
		if(obj==null)
			return null;
		try {
			convertedObj = Integer.parseInt((String) obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return convertedObj;
	}

	public static Long convertObjectToLong(Object obj) {
		Long convertedObj = null;
		if(obj==null)
			return null;
		try {
			convertedObj = Long.parseLong((String) obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return convertedObj;
	}

	public static BigDecimal convertObjectToBigDecimal(Object obj) {
		BigDecimal convertedObj = null;
		if(obj==null)
			return null;
		try {
			convertedObj = BigDecimal.valueOf(Double.parseDouble((String) obj));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return convertedObj;
	}

	public static Object getUserRole(String userRole, Boolean requireDefault) {
		switch (userRole) {
		case "ADMIN":
			return 1;
		case "DOCTOR":
			return 2;
		case "PATIENT":
			return 3;
		default:
			return requireDefault? 3: null;
		}
	}
	
	public static String setBase64Token(String userName, String password) {
		String userPassword = userName + ":" + password;
		String token = Base64.encodeAsString(userPassword);
		return token;
	}
	
	public static String getBase64Token(String base64Token) {
		return Base64.decodeAsString(base64Token);
	}
	
}
