package com.dali.didor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.dali.didor.alarm.AlarmReceiver;
import com.dali.didor.db.AlarmTableDao;
import com.dali.didor.db.DatabaseHelper;
import com.dali.didor.model.AlarmItem;
import com.dali.didor.model.AlarmRepeatType;
import com.dali.didor.model.Datetime;
import com.dali.didor.utils.Constants;

public class AddReminderActivity extends Activity {

	private static final int PICK_DATETIME_REQUEST_CODE = 1;

	private Button pickDateBtn;

	private EditText content;

	private Datetime datetime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);
		getActionBar().setTitle(R.string.ac_ar_title);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayHomeAsUpEnabled(true);

		datetime = new Datetime();

		pickDateBtn = (Button) findViewById(R.id.ac_armd_pick_date);
		pickDateBtn.setText(datetime.display(this));
		pickDateBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(AddReminderActivity.this, PickDatetimeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("datetime", datetime.toString());
				intent.putExtras(bundle);
				startActivityForResult(intent, PICK_DATETIME_REQUEST_CODE);
			}
		});

		content = (EditText) findViewById(R.id.ac_armd_content);
		Button setBtn = (Button) findViewById(R.id.ac_armd_set);
		setBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				AlarmItem alarmItem = new AlarmItem();
				alarmItem.setContent(content.getText().toString());
				alarmItem.setStartTime(datetime);
				alarmItem.setEndTime(datetime);
				alarmItem.setCreatedTime(new Datetime());
				alarmItem.setRepeatType(AlarmRepeatType.OneTime);

				DatabaseHelper alarmDatabaseHelper = new DatabaseHelper(AddReminderActivity.this, Constants.DB_NAME);
				AlarmTableDao dao = new AlarmTableDao(alarmDatabaseHelper);
				long alarmId = dao.insertAlarmItem(alarmItem);

				AlarmManager mAlarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
				Intent intent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
				Bundle bundle = new Bundle();
				bundle.putInt("alarm_index", (int) alarmId);
				intent.putExtras(bundle);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, (int) alarmId, intent, 0);
				mAlarm.set(AlarmManager.RTC_WAKEUP, datetime.getTimeInMillis(), pendingIntent);

				Intent mWidgetIntent = new Intent();
				mWidgetIntent.setAction("com.dali.didor.alarm.widget");
				AddReminderActivity.this.sendBroadcast(mWidgetIntent);

				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_DATETIME_REQUEST_CODE) {
			if (resultCode == android.app.Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				datetime = Datetime.parse(bundle.getString("datetime"));
				pickDateBtn.setText(datetime.display(this));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.add_reminder, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }

		if (id == android.R.id.home) {
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
