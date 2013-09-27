package itp.team1.jobseeker.session.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.model.Job;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static String DATABASE_NAME = "JobSeekerDB";

	// Contacts table name
	private static final String TABLE_JOBS = "flaggedJobs";
	private static final String TABLE_ACTIVITY = "activityItems";

	// Jobs Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DB_ID = "dbId";
	private static final String KEY_DESCRIPTION = "desription";
	private static final String KEY_URL = "url";
	private static final String KEY_SOURCE = "source";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_LATITUDE = "lat";
	private static final String KEY_LONGITUDE = "lon";
	private static final String KEY_EMPLOYER = "employer";
	private static final String KEY_TITLE = "title";
	private static final String KEY_OPENING = "opening";
	private static final String KEY_CLOSING = "closing";
	private static final String KEY_TIMESTAMP = "timestamp";
	
	//Table Activity Column Names
	private static final String KEY_DATE = "date";
	private static final String KEY_VIEWED = "flagViewed";
	private static final String KEY_APPLIED = "flagApplied";
	private static final String KEY_NOTED = "flagNote";
	private static final String KEY_NOTES = "notes";
	private static final String KEY_DETAILS = "details";
	private static final String KEY_CAUTION = "flagCaution";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_JOBS_TABLE = "CREATE TABLE " + TABLE_JOBS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY autoincrement," 
				+ KEY_DB_ID	+ " TEXT not null, " 
				+ KEY_DESCRIPTION + " TEXT not null, " 
				+ KEY_URL + " TEXT not null," 
				+ KEY_SOURCE + " TEXT not null, " 
				+ KEY_LOCATION + " TEXT, " 
				+ KEY_LATITUDE + " TEXT, " 
				+ KEY_LONGITUDE + " TEXT, " 
				+ KEY_EMPLOYER + " TEXT, " 
				+ KEY_TITLE + " TEXT, " 
				+ KEY_OPENING + " TEXT, " 
				+ KEY_CLOSING + " TEXT, " 
				+ KEY_TIMESTAMP + " TEXT" 
				+ ")";

		db.execSQL(CREATE_JOBS_TABLE);
		
		

		String CREATE_ACTIVITY_TABLE = "CREATE TABLE " + TABLE_ACTIVITY + "(" 
				+ KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
				+ KEY_DB_ID	+ " TEXT not null, " 
				+ KEY_DATE + " TEXT not null, " 
				+ KEY_VIEWED + " INTEGER, " 
				+ KEY_APPLIED + " INTEGER, " 
				+ KEY_NOTED + " INTEGER, " 
				+ KEY_NOTES + " TEXT, " 
				+ KEY_DETAILS + " TEXT, " 
				+ KEY_CAUTION + " INTEGER"
				+ ")";
		db.execSQL(CREATE_ACTIVITY_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
		// Create tables again
		onCreate(db);
	}

	
	 // All CRUD(Create, Read, Update, Delete) Operations
	 

	// Adding new job
	public void addJob(Job item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DB_ID, item.id); 
		values.put(KEY_DESCRIPTION, item.description);
		values.put(KEY_URL, item.externalLink); 
		values.put(KEY_SOURCE, item.source); 
		values.put(KEY_LOCATION, item.location); 
		values.put(KEY_LATITUDE, String.valueOf(item.latitude)); 
		values.put(KEY_LONGITUDE, String.valueOf(item.longitude)); 
		values.put(KEY_EMPLOYER, item.employer); 
		values.put(KEY_TITLE, item.title); 
		values.put(KEY_OPENING, item.openingDate); 
		values.put(KEY_CLOSING, item.closingDate); 
		values.put(KEY_TIMESTAMP, item.time); 

		// Inserting Row
		db.insert(TABLE_JOBS, null, values);
		db.close(); // Closing database connection
	}

	public void addActivity(String story, String date, int viewed, int applied, int noted, String notes, String details, int caution) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DB_ID, story); 
		values.put(KEY_DATE, date); 
		values.put(KEY_VIEWED, viewed); 
		values.put(KEY_APPLIED, applied); 
		values.put(KEY_NOTED, noted); 
		values.put(KEY_NOTES, notes); 
		values.put(KEY_DETAILS, details); 
		values.put(KEY_CAUTION, caution); 

		// Inserting Row
		db.insert(TABLE_ACTIVITY, null, values);
		db.close(); // Closing database connection
	}

	// Getting single job item
	JobItem getJob(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_JOBS, new String[] { KEY_ID,
				KEY_DB_ID, KEY_DESCRIPTION, KEY_URL, KEY_SOURCE, 
				KEY_LOCATION, KEY_LATITUDE, KEY_LONGITUDE, 
				KEY_EMPLOYER, KEY_TITLE, KEY_OPENING, KEY_CLOSING, KEY_TIMESTAMP }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		JobItem contact = new JobItem(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), 
				cursor.getString(5), cursor.getString(6), cursor.getString(7),
				cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12));
		// return contact
		return contact;
	}

	// Getting single activity
	ActivityItem getActivity(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ACTIVITY, new String[] { KEY_ID,
				KEY_DB_ID, KEY_DATE, KEY_VIEWED, KEY_APPLIED, KEY_NOTED, KEY_NOTES, KEY_DETAILS, KEY_CAUTION }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ActivityItem contact = new ActivityItem(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), 
				Integer.parseInt(cursor.getString(4)), cursor.getString(5), cursor.getString(6),Integer.parseInt(cursor.getString(7)));
		// return contact
		return contact;
	}

	// Getting All Contacts
	public LinkedList<JobItem> getAllJobs() {
		LinkedList<JobItem> picureList = new LinkedList<JobItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_JOBS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				JobItem contact = new JobItem(
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_DB_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
						cursor.getString(cursor.getColumnIndex(KEY_URL)),
						cursor.getString(cursor.getColumnIndex(KEY_SOURCE)),
						cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
						cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
						cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),
						cursor.getString(cursor.getColumnIndex(KEY_EMPLOYER)),
						cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
						cursor.getString(cursor.getColumnIndex(KEY_OPENING)),
						cursor.getString(cursor.getColumnIndex(KEY_CLOSING)),
						cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));
				// Adding contact to list
				picureList.add(contact);
			} while (cursor.moveToNext());
		}
		return picureList;
	}

	public LinkedList<ActivityItem> getAllActivities() {
		LinkedList<ActivityItem> commentList = new LinkedList<ActivityItem>();
		String selectQ = "SELECT  * FROM " + TABLE_ACTIVITY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);

		if (cursor.moveToFirst()) {
			do {
				ActivityItem profile = new ActivityItem(
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_DB_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_DATE)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_VIEWED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_APPLIED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_NOTED))),
						cursor.getString(cursor.getColumnIndex(KEY_NOTES)),
						cursor.getString(cursor.getColumnIndex(KEY_DETAILS)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CAUTION))));
				commentList.add(profile);
			} while (cursor.moveToNext());
		}
		return commentList;
	}
	/*

	// Updating single job found by ID
	public int updateJob(JobItem pic) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, pic.getStoryID());
		values.put(KEY_PICTURE_URL, pic.getPictureUrl());

		// updating row
		return db.update(TABLE_PICTURES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(pic.getID()) });
	}

	// Updating single activity found by ID
	public int updateActivity(ActivityItem comment) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, comment.getStoryID());
		values.put(KEY_COMMENT_ID, comment.getCommentID());

		// updating row
		return db.update(TABLE_COMMENTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(comment.getID()) });
	}
	*/

	// Deleting single picture by id
	public void deleteJob(JobItem pic) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_JOBS, KEY_ID + " = ?",
				new String[] { String.valueOf(pic.getId()) });
		db.close();
	}

	// Deleting single comment by id
	public void deleteActivity(ActivityItem comment) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ACTIVITY, KEY_ID + " = ?",
				new String[] { String.valueOf(comment.getID()) });
		db.close();
	}

	public int getJobsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_JOBS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getActivityCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ACTIVITY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Getting All Contacts
	public List<JobItem> getPictureDownloaded(String story) {
		List<JobItem> picureList = new ArrayList<JobItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_JOBS+ " WHERE "
				+ KEY_DB_ID+ "=" + String.valueOf(story) + ";";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				JobItem contact = new JobItem(
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_DB_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
						cursor.getString(cursor.getColumnIndex(KEY_URL)),
						cursor.getString(cursor.getColumnIndex(KEY_SOURCE)),
						cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
						cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)),
						cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)),
						cursor.getString(cursor.getColumnIndex(KEY_EMPLOYER)),
						cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
						cursor.getString(cursor.getColumnIndex(KEY_OPENING)),
						cursor.getString(cursor.getColumnIndex(KEY_CLOSING)),
						cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));
				// Adding contact to list
				picureList.add(contact);
			} while (cursor.moveToNext());
		}
		return picureList;
	}

	public LinkedList<ActivityItem> getJobViewedActivity(String story, boolean viewed, boolean applied, boolean noted) {
		LinkedList<ActivityItem> commentList = new LinkedList<ActivityItem>();
		String selectQ = "SELECT  * FROM " + TABLE_ACTIVITY + " WHERE "
				+ KEY_DB_ID + "=" + String.valueOf(story) + " AND " 
				+ KEY_VIEWED + "=" + 1	+";";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);

		if (cursor.moveToFirst()) {
			do {
				ActivityItem profile = new ActivityItem(
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_DB_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_DATE)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_VIEWED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_APPLIED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_NOTED))),
						cursor.getString(cursor.getColumnIndex(KEY_NOTES)),
						cursor.getString(cursor.getColumnIndex(KEY_DETAILS)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CAUTION))));
				commentList.add(profile);
			} while (cursor.moveToNext());
		}
		return commentList;
	}
	
	public LinkedList<ActivityItem> getJobAppliedActivity(String story, boolean viewed, boolean applied, boolean noted) {
		LinkedList<ActivityItem> commentList = new LinkedList<ActivityItem>();
		String selectQ = "SELECT  * FROM " + TABLE_ACTIVITY + " WHERE "
				+ KEY_DB_ID + "=" + String.valueOf(story) + " AND " 
				+ KEY_APPLIED + "=" + 1	+";";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);

		if (cursor.moveToFirst()) {
			do {
				ActivityItem profile = new ActivityItem(
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_DB_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_DATE)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_VIEWED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_APPLIED))),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_NOTED))),
						cursor.getString(cursor.getColumnIndex(KEY_NOTES)),
						cursor.getString(cursor.getColumnIndex(KEY_DETAILS)),
						Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CAUTION))));
				commentList.add(profile);
			} while (cursor.moveToNext());
		}
		return commentList;
	}
	
}
