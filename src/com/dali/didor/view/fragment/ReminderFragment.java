package com.dali.didor.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dali.didor.AddReminderActivity;
import com.dali.didor.R;
import com.dali.didor.model.TestModel;
import com.dali.didor.view.SlideListView;
import com.dali.didor.view.SlideLock;
import com.dali.didor.view.SlideMenu;

public class ReminderFragment extends Fragment {
	
	private LayoutInflater layoutInflater;
	
	private SlideMenu slideMenu;
	
	private TextView btn1;
	
	private ImageView plusBtn;
	
	private PopupWindow popupwindow;
	
	private SlideListView mListView;
	private SlideAdapter adapter;
	private List<TestModel> listModels = new ArrayList<TestModel>();
	
	private SlideLock slideLock = new SlideLock();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	this.layoutInflater = inflater;
    	View result =  inflater.inflate(R.layout.tab_main_reminder, null);
    	
    	slideMenu = (SlideMenu) result.findViewById(R.id.slide_menu);
		ImageView menuImg = (ImageView) result.findViewById(R.id.title_bar_menu_btn);
		menuImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (slideMenu.isMainScreenShowing()) {
					slideMenu.openMenu();
				} else {
					slideMenu.closeMenu();
				}
			}
			
		});
		
		btn1 = (TextView) result.findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(layoutInflater.getContext(), "默认Toast样式",
					     Toast.LENGTH_SHORT).show();
			}
			
		});
		
		plusBtn = (ImageView) result.findViewById(R.id.title_bar_plus_btn);
		plusBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					return;
				} else {
					initmPopupWindowView();
					popupwindow.setFocusable(true);  
			        popupwindow.setOutsideTouchable(true); 
					popupwindow.showAsDropDown(v, -260, 26);
				}
				
			}});
		
		////////////////////////////////////////
		adapter = new SlideAdapter();
		
		mListView = (SlideListView) result.findViewById(R.id.listview_list);
		mListView.initSlideMode(SlideListView.MOD_RIGHT);
		for (int i = 0; i < 40; i++) {
			TestModel item = new TestModel();
				item.setTitle(i + "title");
				item.setContent(i + "content");
				item.setTime(i + "time");
			listModels.add(item);
		}
		mListView.setAdapter(adapter);
		//mListView.setOnItemClickListener(this);
		////////////////////////////////////////
		
		slideMenu.setSlideLock(slideLock);
		mListView.setSlideLock(slideLock);
		
		return result;
    }
    
	public void initmPopupWindowView() {

		View customView = layoutInflater.inflate(R.layout.menu_add, null, false);
		popupwindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//popupwindow.setAnimationStyle(R.style.AnimationFade);
		
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
		
		TextView addReminder = (TextView) customView.findViewById(R.id.item_reminder);
		addReminder.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				popupwindow.dismiss();
				popupwindow = null;
				Intent intent = new Intent();
				intent.setClass(layoutInflater.getContext(), AddReminderActivity.class);
				startActivity(intent);
			}
		});
		

	}
	
	
	////////////////////////////////////////////////////////////
	
	private static class ViewHolder {
        public TextView title;
        public TextView time;
        public TextView content;
        public RelativeLayout delete1;
        public RelativeLayout other1;
        public RelativeLayout delete2;
        public RelativeLayout other2;

    }

	private class SlideAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listModels.size();
        }

        @Override
        public Object getItem(int position) {
            return listModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
            	convertView = layoutInflater.inflate(R.layout.reminder_list_item, null);

                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                holder.delete1 = (RelativeLayout)convertView.findViewById(R.id.delete1);
                holder.other1 = (RelativeLayout)convertView.findViewById(R.id.other1);
                holder.delete2 = (RelativeLayout)convertView.findViewById(R.id.delete2);
                holder.other2 = (RelativeLayout)convertView.findViewById(R.id.other2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TestModel item = listModels.get(position);

            holder.title.setText(item.getTitle());
            holder.content.setText(item.getContent());
            holder.time.setText(item.getTime());
            holder.delete1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mListView.slideBack();
				}
			});
            holder.other1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mListView.slideBack();
				}
			});
            holder.delete2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mListView.slideBack();
				}
			});
            holder.other2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mListView.slideBack();
				}
			});
            
            return convertView;
        }

    }
	
}
