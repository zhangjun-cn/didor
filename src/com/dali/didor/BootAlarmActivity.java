package com.dali.didor;

import com.dali.didor.alarm.AlarmReceiver;
import com.dali.didor.db.AlarmDatabaseHelper;
import com.dali.didor.model.Datetime;
import com.dali.didor.utils.Constants;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class BootAlarmActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Datetime now = new Datetime();
		AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(BootAlarmActivity.this, Constants.DB_NAME);
		SQLiteDatabase db = alarmDatabaseHelper.getReadableDatabase();
		Cursor cursor = db.query("alarm", null, null, null, null, null, null);

		while (cursor.moveToNext()) {
			Datetime alarmTime = Datetime.parse(cursor.getString(cursor
					.getColumnIndex("time")));
			if (alarmTime.getTimeInMillis() < now.getTimeInMillis()) {
				continue;
			}

			AlarmManager mAlarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
			Intent intent = new Intent(BootAlarmActivity.this,
					AlarmReceiver.class);
			Bundle bundle = new Bundle();
			bundle.putInt("alarm_index", cursor.getInt(0));
			intent.putExtras(bundle);
			PendingIntent pendingIntent = PendingIntent.getBroadcast( BootAlarmActivity.this, cursor.getInt(0), intent, 0);
			mAlarm.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
		}

		db.close();
		Intent intent = new Intent(BootAlarmActivity.this, AddReminderActivity.class);
		startActivity(intent);
		BootAlarmActivity.this.finish();
	}

}
