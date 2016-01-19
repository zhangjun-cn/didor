package com.dali.didor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	
	String DATABASE_CREATE = "create table alarm(_id INTEGER PRIMARY KEY AUTOINCREMENT,time varchar(200),message varchar(200),audio varchar(200))";
	String DATABASE_CREATE_TEMP = "create table alarm_done(_id INTEGER PRIMARY KEY AUTOINCREMENT,time varchar(200),message varchar(200),audio varchar(200))";


	public AlarmDatabaseHelper(Context context, String DATABASE_NAME) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE_TEMP);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
