package itp.team1.jobseeker.session;

import itp.team1.jobseeker.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	private static  String PREF_NAME = "";
	private static final String IS_LOGIN = "loggedIn";

	public static final String KEY_JOBS_VIEWED = "viewedUr";
	public static final String KEY_JOBS_APPLIED = "appliedUr";
	public static final String KEY_JOBS_CLICKED = "clickUr";
	public static final String KEY_JOBS_SEARCHED = "searchUr";

	public static final String KEY_TIME = "appointmentTime";


	// Constructor
	public SessionManager(Context context){
		PREF_NAME = context.getResources().getString(R.string.app_name);
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public boolean checkClearDiary(){
		long timeNow = System.currentTimeMillis();
		long time = getTime();
		if(time != 0) {
			if(timeNow  >= time + 1209600000) return true;
			else return false;
		} else return false;
	}
	
	public void clearDiary(){
		setViewed(0);
		setApplied(0);
		setClicked(0);
		setSearches(0);
	}

	public void setTime(){
		editor.putLong(KEY_TIME, System.currentTimeMillis());
		editor.commit();
	}

	public long getTime(){
		return pref.getLong(KEY_TIME, 0);
	}
	
	public void setViewed(long view){
		editor.putLong(KEY_JOBS_VIEWED, view);
		editor.commit();
	}
	
	public void incrementViewed(){
		long view = getViewed() + 1 ;
		editor.putLong(KEY_JOBS_VIEWED, view);
		editor.commit();
	}
	
	public long getViewed(){
		return pref.getLong(KEY_JOBS_VIEWED, 0);
	}
	
	public void setApplied(long view){
		editor.putLong(KEY_JOBS_APPLIED, view);
		editor.commit();
	}
	
	public void incrementApplied(){
		long view = getViewed() + 1 ;
		editor.putLong(KEY_JOBS_APPLIED, view);
		editor.commit();
	}
	
	public long getApplied(){
		return pref.getLong(KEY_JOBS_APPLIED, 0);
	}
	
	public void setClicked(long view){
		editor.putLong(KEY_JOBS_CLICKED, view);
		editor.commit();
	}
	
	public void incrementClicked(){
		long view = getViewed() + 1 ;
		editor.putLong(KEY_JOBS_CLICKED, view);
		editor.commit();
	}
	
	public long getClicked(){
		return pref.getLong(KEY_JOBS_CLICKED, 0);
	}

	public void setSearches(long view){
		editor.putLong(KEY_JOBS_SEARCHED, view);
		editor.commit();
	}
	
	public void incrementSearches(){
		long view = getViewed() + 1 ;
		editor.putLong(KEY_JOBS_SEARCHED, view);
		editor.commit();
	}
	
	public long getSearches(){
		return pref.getLong(KEY_JOBS_SEARCHED, 0);
	}
	

}