package com.dali.didor;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.PopupWindow;

public class AddQRActivity extends Activity {

	private PopupWindow popupwindow;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_qr);
		
		getActionBar().setTitle(R.string.ac_ar_title);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		button = (Button) findViewById(R.id.name);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (popupwindow != null&&popupwindow.isShowing()) {
					popupwindow.dismiss();
					return;
				} else {
					initmPopupWindowView();
					popupwindow.showAsDropDown(v, 0, 5);
				}
				
			}});
		
	}
	
	public void initmPopupWindowView() {

		// // ��ȡ�Զ��岼���ļ�pop.xml����ͼ
		View customView = getLayoutInflater().inflate(R.layout.menu_add,
				null, false);
		// ����PopupWindowʵ��,200,150�ֱ��ǿ�Ⱥ͸߶�
		popupwindow = new PopupWindow(customView, 500, 560);
		// ���ö���Ч�� [R.style.AnimationFade ���Լ����ȶ���õ�]
		//popupwindow.setAnimationStyle(R.style.AnimationFade);
		// �Զ���view��Ӵ����¼�
		customView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}

				return false;
			}
		});

		/** ���������ʵ���Զ�����ͼ�Ĺ��� */
//		Button btton2 = (Button) customView.findViewById(R.id.button2);
//		Button btton3 = (Button) customView.findViewById(R.id.button3);
//		Button btton4 = (Button) customView.findViewById(R.id.button4);
//		btton2.setOnClickListener(this);
//		btton3.setOnClickListener(this);
//		btton4.setOnClickListener(this);

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
