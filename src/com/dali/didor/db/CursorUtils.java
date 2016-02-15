package com.dali.didor.db;

import android.database.Cursor;

import com.dali.didor.model.AlarmRepeatType;
import com.dali.didor.model.Datetime;
import com.dali.didor.utils.StringUtils;

public class CursorUtils {

	public static String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	public static int getInt(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static long getLong(Cursor cursor, String columnName, long defaultValue) {
		return StringUtils.toLong(cursor.getString(cursor.getColumnIndex(columnName)), defaultValue);
	}

	public static Datetime getDatetime(Cursor cursor, String columnName) {
		long timeInMillis = CursorUtils.getLong(cursor, columnName, -1);
		return timeInMillis < 0 ? null : new Datetime(timeInMillis);
	}

	public static AlarmRepeatType getRepeatType(Cursor cursor, String columnName) {
		return AlarmRepeatType.valueOf(cursor.getInt(cursor.getColumnIndex(columnName)));
	}

}
