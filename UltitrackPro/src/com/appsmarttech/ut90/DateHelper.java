package com.appsmarttech.ut90;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	   public static String getDate(){

		   Date anotherCurDate = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		   String formattedDateString = formatter.format(anotherCurDate); 
		   
		   return formattedDateString;
		        
		   }
	   
	   public static Date makeDate(String sDate){ //converts the string back to a date object.
		   Date convertedDate = null;
		   SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		   try {
			   convertedDate = dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		   
		   return convertedDate;
		   
	   }
	
}
