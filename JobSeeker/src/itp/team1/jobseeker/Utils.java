package itp.team1.jobseeker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	public static String parseTimeNew(long date){
		String timeSince = "";
		Calendar c = Calendar.getInstance();

		/*
		TimeZone z = c.getTimeZone();
		int offset = z.getRawOffset();
		if(z.inDaylightTime(new Date())){
			offset = offset + z.getDSTSavings();
		}

		int offsetHrs = offset / 1000 / 60 / 60;
		int offsetMins = offset / 1000 / 60 % 60;

		c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
		c.add(Calendar.MINUTE, (-offsetMins));
		*/

		long timeNow = c.getTimeInMillis() - date;
		long days = TimeUnit.MILLISECONDS.toDays(timeNow);
		timeSince = String.valueOf(days) + " days ago";
		if(days == 0) {
			days = TimeUnit.MILLISECONDS.toHours(timeNow);
			timeSince = String.valueOf(days) + " hours ago";
		}
		if(days == 0) {
			days = TimeUnit.MILLISECONDS.toMinutes(timeNow);
			timeSince = String.valueOf(days) + " minutes ago";
		}
		if(days == 0) {
			timeSince = "just now";
		}

		return timeSince;
	}
	
	public static String getFormattedDate(){
		Calendar calendar = Calendar.getInstance();
		
		String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.YEAR) + " " +
				calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
		
		return date;
	}
	
	public static String parseTime(long date){
		String timeSince = "";
		Calendar today = Calendar.getInstance();
		
		Calendar post = Calendar.getInstance();
		post.setTimeInMillis(date);
		
		if(today.get(Calendar.DAY_OF_MONTH)==post.get(Calendar.DAY_OF_MONTH)) {
			if(today.get(Calendar.HOUR_OF_DAY) > post.get(Calendar.HOUR_OF_DAY)) {
				timeSince = String.valueOf(today.get(Calendar.HOUR_OF_DAY) - post.get(Calendar.HOUR_OF_DAY)) + " hours ago";
			} else {
				if(today.get(Calendar.MINUTE) > post.get(Calendar.MINUTE)) {
					timeSince = String.valueOf(today.get(Calendar.MINUTE) - post.get(Calendar.MINUTE)) + " minutes ago";
				} else {
					timeSince = "just now";
				}
			}
		} else {
			timeSince = String.valueOf(today.get(Calendar.DAY_OF_MONTH) - post.get(Calendar.DAY_OF_MONTH)) + " days ago";
		}

		return timeSince;
	}
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
