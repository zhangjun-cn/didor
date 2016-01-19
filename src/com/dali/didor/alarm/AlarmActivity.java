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
import com.dali.didor.db.AlarmDatabaseHelper;

public class AlarmActivity extends Activity {
	
	private AlarmDatabaseHelper alarmDatabaseHelper;
	
	private String alarmTime;
	private String alarmText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.err.println("hahahahahaha");
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		Intent intentSV = new Intent(AlarmActivity.this, AlarmService.class);
		startService(intentSV);
		
		alarmDatabaseHelper = new AlarmDatabaseHelper(AlarmActivity.this, "dali.didor.sql");
		
		Bundle bundle = this.getIntent().getExtras();
		int alarmIndedx = bundle.getInt("alarm_index");
		SQLiteDatabase db = alarmDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.query("alarm", null, "_id=?", new String[] { alarmIndedx + "" }, null, null, null);
		if (cursor.moveToFirst()) {
			alarmTime = cursor.getString(cursor.getColumnIndex("time"));
			alarmText = cursor.getString(cursor.getColumnIndex("message"));
		}
		System.out.println("alarmTime-->"+alarmTime+"alarmText-->"+alarmText);

		db.close();
		//addData();
		//deleteData(NoteActivity.Alarming_index);
		
		new AlertDialog.Builder(AlarmActivity.this)
				.setIcon(R.drawable.ai_reminder)
				.setTitle("It's time to do something.")
				.setMessage(alarmText)
				.setPositiveButton("Done",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
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
