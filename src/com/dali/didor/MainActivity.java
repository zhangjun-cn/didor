package com.dali.didor;

import java.lang.reflect.Method;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dali.didor.view.fragment.ContactsFragment;
import com.dali.didor.view.fragment.DiscoverFragment;
import com.dali.didor.view.fragment.MeFragment;
import com.dali.didor.view.fragment.ReminderFragment;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_content, new ReminderFragment()).commit();

		RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
		myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Fragment f = null;

				switch (checkedId) {
				case R.id.rbReminder:
					f = new ReminderFragment();
					break;
				case R.id.rbContacts:
					f = new ContactsFragment();
					break;
				case R.id.rbDiscover:
					f = new DiscoverFragment();
					break;
				case R.id.rbMe:
					f = new MeFragment();
					break;
				default:
					break;
				}

				if (f != null) {
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.main_content, f).commit();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		setIconEnable(menu, true);

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);
			// 下面传入参数
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add_reminder) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddReminderActivity.class);
			startActivity(intent);
			// finish();
			return true;
		}
		if (id == R.id.action_add_qr) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddQRActivity.class);
			startActivity(intent);
			// finish();
			return true;
		}
		if (id == R.id.action_add_friend) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AddFriendActivity.class);
			startActivity(intent);
			// finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
