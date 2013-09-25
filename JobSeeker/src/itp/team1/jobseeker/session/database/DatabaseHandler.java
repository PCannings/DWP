package itp.team1.jobseeker.session.database;

import java.util.ArrayList;
import java.util.List;

import itp.team1.jobseeker.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler {//extends SQLiteOpenHelper {
	/*
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static String DATABASE_NAME = "JobSeekerDB";

	// Contacts table name
	private static final String TABLE_JOBS = "flaggedJobs";
	private static final String TABLE_ACTIVITY = "activityItems";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_VIEWED = "storyId";
	private static final String KEY_PICTURE_URL = "picUrl";
	private static final String KEY_COMMENT_ID = "commentId";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PICTURES_TABLE = "CREATE TABLE " + TABLE_PICTURES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY autoincrement," 
				+ KEY_STORY_ID	+ " TEXT not null," + KEY_PICTURE_URL + " TEXT not null" + ")";
		db.execSQL(CREATE_PICTURES_TABLE);

		String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
				+ KEY_STORY_ID + " TEXT not null," + KEY_COMMENT_ID + " TEXT not null" + ")";
		db.execSQL(CREATE_COMMENTS_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		// Create tables again
		onCreate(db);
	}

	
	 // All CRUD(Create, Read, Update, Delete) Operations
	 

	// Adding new picture
	public void addPicture(String story, String url) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, story); 
		values.put(KEY_PICTURE_URL, url); 

		// Inserting Row
		db.insert(TABLE_PICTURES, null, values);
		db.close(); // Closing database connection
	}

	public void addComment(String story, String commentId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, story); 
		values.put(KEY_COMMENT_ID, commentId); 

		// Inserting Row
		db.insert(TABLE_COMMENTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single picture
	DownloadedPicture getPicture(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PICTURES, new String[] { KEY_ID,
				KEY_STORY_ID, KEY_PICTURE_URL }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		DownloadedPicture contact = new DownloadedPicture(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}

	// Getting single picture
	FlaggedComment getComment(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_COMMENTS, new String[] { KEY_ID,
				KEY_STORY_ID, KEY_COMMENT_ID }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		FlaggedComment contact = new FlaggedComment(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}

	// Getting All Contacts
	public List<DownloadedPicture> getAllPictures() {
		List<DownloadedPicture> picureList = new ArrayList<DownloadedPicture>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PICTURES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DownloadedPicture contact = new DownloadedPicture(Integer.parseInt(cursor
						.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_STORY_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_PICTURE_URL)));
				// Adding contact to list
				picureList.add(contact);
			} while (cursor.moveToNext());
		}
		return picureList;
	}

	public List<FlaggedComment> getAllProfiles() {
		List<FlaggedComment> commentList = new ArrayList<FlaggedComment>();
		String selectQ = "SELECT  * FROM " + TABLE_COMMENTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);

		if (cursor.moveToFirst()) {
			do {
				FlaggedComment profile = new FlaggedComment(Integer.parseInt(cursor
						.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_STORY_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_COMMENT_ID)));
				commentList.add(profile);
			} while (cursor.moveToNext());
		}
		return commentList;
	}

	// Updating single picture found by ID
	public int updatePicture(DownloadedPicture pic) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, pic.getStoryID());
		values.put(KEY_PICTURE_URL, pic.getPictureUrl());

		// updating row
		return db.update(TABLE_PICTURES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(pic.getID()) });
	}

	// Updating single comment found by ID
	public int updateComment(FlaggedComment comment) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORY_ID, comment.getStoryID());
		values.put(KEY_COMMENT_ID, comment.getCommentID());

		// updating row
		return db.update(TABLE_COMMENTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(comment.getID()) });
	}

	// Deleting single picture by id
	public void deletePicture(DownloadedPicture pic) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PICTURES, KEY_ID + " = ?",
				new String[] { String.valueOf(pic.getID()) });
		db.close();
	}

	// Deleting single comment by id
	public void deleteComment(FlaggedComment comment) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COMMENTS, KEY_ID + " = ?",
				new String[] { String.valueOf(comment.getID()) });
		db.close();
	}

	public int getPicturesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PICTURES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	public int getCommentsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COMMENTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Getting All Contacts
	public List<DownloadedPicture> getPictureDownloaded(String story) {
		List<DownloadedPicture> picureList = new ArrayList<DownloadedPicture>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PICTURES + " WHERE "
				+ KEY_STORY_ID+ "=" + String.valueOf(story) + ";";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DownloadedPicture contact = new DownloadedPicture(Integer.parseInt(cursor
						.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_STORY_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_PICTURE_URL)));
				// Adding contact to list
				picureList.add(contact);
			} while (cursor.moveToNext());
		}
		return picureList;
	}

	public List<FlaggedComment> getCommentFlagged(String story, String comment) {
		List<FlaggedComment> commentList = new ArrayList<FlaggedComment>();
		String selectQ = "SELECT  * FROM " + TABLE_COMMENTS + " WHERE "
				+ KEY_STORY_ID+ "=" + String.valueOf(story) + " AND " 
				+ KEY_COMMENT_ID+ "=" + String.valueOf(comment) + ";";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQ, null);

		if (cursor.moveToFirst()) {
			do {
				FlaggedComment profile = new FlaggedComment(Integer.parseInt(cursor
						.getString(cursor.getColumnIndex(KEY_ID))),
						cursor.getString(cursor.getColumnIndex(KEY_STORY_ID)),
						cursor.getString(cursor.getColumnIndex(KEY_COMMENT_ID)));
				commentList.add(profile);
			} while (cursor.moveToNext());
		}
		return commentList;
	}
	*/
}
