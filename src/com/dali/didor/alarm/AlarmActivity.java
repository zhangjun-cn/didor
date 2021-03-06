package com.dali.didor.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;

import com.dali.didor.R;
import com.dali.didor.db.AlarmTableDao;
import com.dali.didor.db.DatabaseHelper;
import com.dali.didor.model.AlarmItem;
import com.dali.didor.utils.Constants;

public class AlarmActivity extends Activity {

	// private DatabaseHelper alarmDatabaseHelper;
	//
	// private String alarmTime;
	// private String alarmText;

	AlarmItem alarmItem;
	AlarmTableDao alarmTableDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		Intent intentSV = new Intent(AlarmActivity.this, AlarmService.class);
		startService(intentSV);

		Bundle bundle = this.getIntent().getExtras();
		int alarmIndedx = bundle.getInt("alarm_index");

		alarmTableDao = new AlarmTableDao(new DatabaseHelper(AlarmActivity.this, Constants.DB_NAME));
		alarmItem = alarmTableDao.getAlarmItem(alarmIndedx);

//		SQLiteDatabase db = alarmDatabaseHelper.getReadableDatabase();
//		Cursor cursor = db.query("alarm", null, "_id=?", new String[] { alarmIndedx + "" }, null, null, null);
//		if (cursor.moveToFirst()) {
//			alarmTime = cursor.getString(cursor.getColumnIndex("time"));
//			alarmText = cursor.getString(cursor.getColumnIndex("message"));
//		}

		//db.close();
		//addData();
		//deleteData(NoteActivity.Alarming_index);
		
		new AlertDialog.Builder(AlarmActivity.this)
				.setIcon(R.drawable.ai_reminder)
				.setTitle(R.string.alarm_title)
				.setMessage(alarmItem.getContent())
				.setPositiveButton(R.string.alarm_received,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								//deleteData(NoteActivity.Alarming_index);
								
								Intent intentSV = new Intent(AlarmActivity.this, AlarmService.class);
								stopService(intentSV);
								
								Intent mWidgetIntent = new Intent();
								mWidgetIntent.setAction("com.dali.didor.note.widget");
								AlarmActivity.this.sendBroadcast(mWidgetIntent);
								AlarmActivity.this.finish();
							}
						}).show();

		Intent mWidgetIntent = new Intent();
		mWidgetIntent.setAction("com.dali.didor.note.widget");
		AlarmActivity.this.sendBroadcast(mWidgetIntent);
	}
	
	/*
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Alarm.this, NoteActivity.class);
		startActivity(intent);
		Alarm.this.finish();
	}


	public void addData() {
		ContentValues values = new ContentValues();
		values.put("mtime", mtime);
		values.put("mtext", mtext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.insert("user_done", null, values);
		db.close();
	}

	public void deleteData(int index) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		// �ӱ���ɾ��ָ����һ�����
		db.execSQL("DELETE FROM " + "user" + " WHERE _id="
				+ Integer.toString(index));
		db.close();
	}
	*/

}
