package com.dali.didor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddQRActivity extends Activity {

	Spinner mySpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_qr);
		
		getActionBar().setTitle(R.string.add_reminder_action_title);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		Button button = (Button) findViewById(R.id.name);
		
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		button.setText(String.format(getResources().getString(R.string.date_template),
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH) + 1,
				calendar.get(Calendar.DAY_OF_WEEK) - 1,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE)
				));

		mySpinner = (Spinner) findViewById(R.id.sDates);
		List<Integer> lst = new ArrayList<Integer>();  
	    for (int i = 0; i < 10; i++) {  
	        lst.add(2016 + i);  
	    }
	    ArrayAdapter<Integer> myaAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, lst);
	    myaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	    mySpinner.setAdapter(myaAdapter);  
	    
	    mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	@Override
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {  
	            /* 
	             * ids是刚刚新建的list里面的ID 
	             */  
	            int ids = (Integer) mySpinner.getSelectedItem();  
	            System.out.println(ids);  
	            Toast.makeText(getApplicationContext(), String.valueOf(ids), Toast.LENGTH_LONG).show();  
	        }  
	  
	        @Override  
	        public void onNothingSelected(AdapterView<?> arg0) {  
	            // TODO Auto-generated method stub  
	              
	        }
	    });  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_qr, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
