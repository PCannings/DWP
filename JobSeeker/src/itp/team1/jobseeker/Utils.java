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

	public static String parseTime(long date){
		String timeSince = "";
		Calendar c = Calendar.getInstance();

		TimeZone z = c.getTimeZone();
		int offset = z.getRawOffset();
		if(z.inDaylightTime(new Date())){
			offset = offset + z.getDSTSavings();
		}

		int offsetHrs = offset / 1000 / 60 / 60;
		int offsetMins = offset / 1000 / 60 % 60;

		c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
		c.add(Calendar.MINUTE, (-offsetMins));

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
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
