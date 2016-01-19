package com.dali.didor;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class AddFriendActivity extends Activity {
	
	private EditText dateEt=null;
    private EditText timeEt=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);
		
		getActionBar().setTitle(R.string.add_reminder_action_title);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		dateEt=(EditText)findViewById(R.id.dateEt);
        timeEt=(EditText)findViewById(R.id.timeEt);
        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
        //datePicker.setCalendarViewShown(false);
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){

            public void onDateChanged(DatePicker view, int year,
                    int monthOfYear, int dayOfMonth) {
                dateEt.setText("您选择的日期是："+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日。");
            }
            
        });
        
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeEt.setText("您选择的时间是："+hourOfDay+"时"+minute+"分。");
            }
            
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friend, menu);
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
