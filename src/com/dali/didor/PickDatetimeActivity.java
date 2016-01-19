package com.dali.didor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dali.didor.model.Datetime;

public class PickDatetimeActivity extends Activity {

	DatePicker datePicker;
	TimePicker timePicker;
	
	private Datetime datetime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_datetime);

		getActionBar().setTitle(R.string.add_reminder_action_title);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Bundle bundle = this.getIntent().getExtras();
		datetime = Datetime.parse(bundle.getString("datetime"));

		datePicker = (DatePicker) findViewById(R.id.datePicker);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		
		datePicker.init(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth(), null);
		timePicker.setCurrentHour(datetime.getHour());
		timePicker.setCurrentMinute(datetime.getMinute());
		
		final Button okBtn = (Button) findViewById(R.id.ok);
		okBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				datetime.setDate(datePicker.getYear(),
						datePicker.getMonth(),
						datePicker.getDayOfMonth(),
						timePicker.getCurrentHour(),
						timePicker.getCurrentMinute());
				Bundle bundle = new Bundle();
				bundle.putString("datetime", datetime.toString());
				setResult(android.app.Activity.RESULT_OK,
						(new Intent()).putExtras(bundle));
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_datetime, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == android.R.id.home) {
			finish();
            return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
