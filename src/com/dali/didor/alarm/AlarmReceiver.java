package com.dali.didor.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//Toast.makeText(context, "�����õ�����ʱ�䵽��", Toast.LENGTH_LONG).show();		
		
		Intent i = new Intent(context, AlarmActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Bundle bundle = intent.getExtras();
		int alarmIndedx = bundle.getInt("alarm_index");
		
		Bundle b=new Bundle();
	    b.putInt("alarm_index", alarmIndedx);
	    i.putExtras(b);
		
		context.startActivity(i);
		
	}
}
