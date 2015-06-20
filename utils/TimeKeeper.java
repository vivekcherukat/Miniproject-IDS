package miniproject.utils;

import java.util.Date;

public class TimeKeeper {
	public static String getCurrentTime(){
		String timestamp =  new java.text.SimpleDateFormat("dd_MM_yyyy h_mm_ss a").format(new Date());
		return timestamp;
	}
}
