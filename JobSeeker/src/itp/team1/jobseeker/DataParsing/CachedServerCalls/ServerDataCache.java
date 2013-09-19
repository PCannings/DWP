/**
 * This source file is copyright and proprietary of CIQUAL Ltd.
 */

package itp.team1.jobseeker.DataParsing.CachedServerCalls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The LocalDatabase class controls access to and has helper methods for the local database where test results and data
 * transfer counts are stored.
 */
public class ServerDataCache extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ServerDataCache.db";
	private static final int DATABASE_VERSION = 1;
	
	static String MAINTABLENAME  = "MAINTABLENAME";
	static String ID             = "ID";
	static String URL            = "URL";
	static String JSONSTRING     = "JSONSTRING";
	static String TIMESTAMP      = "TIMESTAMP";
	
	static String LOG_TAG        = "databaseError";
	
	private static ServerDataCache instance;

	/**
	 * This constructor has intentionally been made private so that instance creation can be controlled.
	 * 
	 * @param context The application context.
	 */
	private ServerDataCache(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Get an instance of the LocalDatabase class.
	 * 
	 * @param context The application context.
	 * @return An instance of LocalDatabase.
	 */
	public static ServerDataCache getInstance(final Context context) {
		if(instance == null) instance = new ServerDataCache(context);
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void onCreate(final SQLiteDatabase db) {
		createMainTable(db);
	}


	/**
	 * Create the main Table to hold all of our server calls
	 * 
	 * @param db
	 */
	private void createMainTable(final SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + MAINTABLENAME + " (" +
				ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				TIMESTAMP   + " NUMERIC NOT NULL, " +
				JSONSTRING  + " TEXT, " +
				URL         + " TEXT );");
	}



	public synchronized void addServerResponse(String url, String json){
		try{
			final SQLiteDatabase db = getWritableDatabase();
			final ContentValues cv = new ContentValues();
			
			cv.put(TIMESTAMP, System.currentTimeMillis());
			cv.put(URL, url);
			cv.put(JSONSTRING, json);
			
			db.insert(MAINTABLENAME, null, cv);
			}catch(SQLiteException e){
				Log.d(LOG_TAG, "SQLite exception in ServerDataCache.addServerResponse(): " + e.getMessage());
		}
	}

	public synchronized String getCachedJsonFor(String url){
		try{
			final SQLiteDatabase db = getWritableDatabase();
			final Cursor c = db.query( MAINTABLENAME, null, "URL = '"+ url+"'	", null, null, null, null);
			if(c.getCount()>0){
				c.moveToFirst();
				String jsonString = c.getString(c.getColumnIndex(JSONSTRING));
				return jsonString;
			}else{
				System.out.println("getCachedJson NULL");
				c.close();
				return null;
			}
		}catch(SQLiteException e) {
			Log.d(LOG_TAG, "SQLite exception in ServerDataCache.getCachedJsonFor(): " + e.getMessage());
			return null;
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}