package com.dali.didor.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dali.didor.model.AlarmItem;
import com.dali.didor.utils.Constants;
import com.dali.didor.utils.StringUtils;

public class AlarmTableDao {
	private DatabaseHelper dbHelper;

	public AlarmTableDao(DatabaseHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public long insertAlarmItem(AlarmItem item) {
		long alarmId = -1;
		ContentValues values = new ContentValues();
		values.put(Constants.AlarmTable.COLUMN_USER_ID, String.valueOf(item.getUserId()));
		values.put(Constants.AlarmTable.COLUMN_TYPE, String.valueOf(item.getType()));
		values.put(Constants.AlarmTable.COLUMN_TITLE, StringUtils.defaultString(item.getTitle()));
		values.put(Constants.AlarmTable.COLUMN_CONTENT, StringUtils.defaultString(item.getContent()));
		values.put(Constants.AlarmTable.COLUMN_ATTACHMENT, StringUtils.defaultString(item.getAttachment()));
		values.put(Constants.AlarmTable.COLUMN_START_TIME, String.valueOf(item.getStartTime().getTimeInMillis()));
		values.put(Constants.AlarmTable.COLUMN_END_TIME, String.valueOf(item.getEndTime().getTimeInMillis()));
		values.put(Constants.AlarmTable.COLUMN_CREATE_TIME, String.valueOf(item.getCreatedTime().getTimeInMillis()));
		values.put(Constants.AlarmTable.COLUMN_REPEAT_TYPE, String.valueOf(item.getRepeatType().getValue()));
		values.put(Constants.AlarmTable.COLUMN_CREATOR_ID, String.valueOf(item.getCreatorId()));
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		alarmId = db.insert(Constants.AlarmTable.TABLE_NAME, null, values);
		db.close();
		item.setId((int)alarmId);
		return alarmId;
	}

	public AlarmItem getAlarmItem(int alarmId) {
		AlarmItem result = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(Constants.AlarmTable.TABLE_NAME, null, Constants.AlarmTable.COLUMN_ID + "=?",
				new String[] { alarmId + "" }, null, null, null);
		if (cursor.moveToFirst()) {
			result = createAlarmItemFromCursor(cursor);
		}
		return result;
	}

	public ArrayList<AlarmItem> getNotFinishedAlarmItems() {
		ArrayList<AlarmItem> list = new ArrayList<AlarmItem>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(Constants.AlarmTable.TABLE_NAME, null, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				AlarmItem item = createAlarmItemFromCursor(cursor);
				if (item.getEndTime().getTimeInMillis() > System.currentTimeMillis()) {
					list.add(item);
				}
			}

			Collections.sort(list, new Comparator<AlarmItem>() {
				@Override
				public int compare(AlarmItem lhs, AlarmItem rhs) {
					if (lhs.getStartTime().getTimeInMillis() < rhs.getStartTime().getTimeInMillis()) {
						return -1;
					} else if (lhs.getStartTime().getTimeInMillis() > rhs.getStartTime().getTimeInMillis()) {
						return 1;
					} else {
						return 0;
					}
				}
			});
		}
		
		return list;
	}

	private AlarmItem createAlarmItemFromCursor(Cursor cursor) {
		if(cursor == null) {
			return null;
		}

		AlarmItem result = new AlarmItem();
		result.setId(CursorUtils.getInt(cursor, Constants.AlarmTable.COLUMN_ID));
		result.setUserId(CursorUtils.getLong(cursor, Constants.AlarmTable.COLUMN_USER_ID, 0));
		result.setType(CursorUtils.getLong(cursor, Constants.AlarmTable.COLUMN_TYPE, 0));
		result.setTitle(CursorUtils.getString(cursor, Constants.AlarmTable.COLUMN_TITLE));
		result.setContent(CursorUtils.getString(cursor, Constants.AlarmTable.COLUMN_CONTENT));
		result.setAttachment(CursorUtils.getString(cursor, Constants.AlarmTable.COLUMN_ATTACHMENT));
		result.setStartTime(CursorUtils.getDatetime(cursor, Constants.AlarmTable.COLUMN_START_TIME));
		result.setEndTime(CursorUtils.getDatetime(cursor, Constants.AlarmTable.COLUMN_END_TIME));
		result.setCreatedTime(CursorUtils.getDatetime(cursor, Constants.AlarmTable.COLUMN_CREATE_TIME));
		result.setRepeatType(CursorUtils.getRepeatType(cursor, Constants.AlarmTable.COLUMN_REPEAT_TYPE));
		result.setCreatorId(CursorUtils.getLong(cursor, Constants.AlarmTable.COLUMN_CREATOR_ID, Constants.USER_NOT_EXIST));

		return result;
	}

}
