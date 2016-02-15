package com.dali.didor.db;

import com.dali.didor.utils.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String USER_CREATE_SQL = String.format("create table %s ("
																	+ "%s varchar(20), "
																	+ "%s varchar(100), "
																	+ "%s smallint, "
																	+ "%s varchar(20), "
																	+ "%s varchar(10), "
																	+ "%s varchar(10))",
																	Constants.UserTable.TABLE_NAME,
																	Constants.UserTable.COLUMN_ID,
																	Constants.UserTable.COLUMN_NAME,
																	Constants.UserTable.COLUMN_GENDER,
																	Constants.UserTable.COLUMN_BIRTHDAY,
																	Constants.UserTable.COLUMN_AREA,
																	Constants.UserTable.COLUMN_LOCALE);

	private static final String ALARM_CREATE_SQL = String.format("create table %s ("
																	+ "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
																	+ "%s varchar(20), "
																	+ "%s interger, "
																	+ "%s varchar(100), "
																	+ "%s varchar(200), "
																	+ "%s varchar(200), "
																	+ "%s varchar(20), "
																	+ "%s varchar(20), "
																	+ "%s varchar(20), "
																	+ "%s interger, "
																	+ "%s varchar(20))",
																	Constants.AlarmTable.TABLE_NAME,
																	Constants.AlarmTable.COLUMN_ID,
																	Constants.AlarmTable.COLUMN_USER_ID,
																	Constants.AlarmTable.COLUMN_TYPE,
																	Constants.AlarmTable.COLUMN_TITLE,
																	Constants.AlarmTable.COLUMN_CONTENT,
																	Constants.AlarmTable.COLUMN_ATTACHMENT,
																	Constants.AlarmTable.COLUMN_START_TIME,
																	Constants.AlarmTable.COLUMN_END_TIME,
																	Constants.AlarmTable.COLUMN_CREATE_TIME,
																	Constants.AlarmTable.COLUMN_REPEAT_TYPE,
																	Constants.AlarmTable.COLUMN_CREATOR_ID);

	private static final String CONTACT_CREATE_SQL = "create table contact(user_id varchar(20), "
																	+ "user_name varchar(20), "
																	+ "gender smallint, "
																	+ "birthday varchar(20), "
																	+ "area varchar(5), "
																	+ "relation interger)";

	public DatabaseHelper(Context context, String DATABASE_NAME) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_CREATE_SQL);
		db.execSQL(ALARM_CREATE_SQL);
		db.execSQL(CONTACT_CREATE_SQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
