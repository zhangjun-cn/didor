package com.dali.didor.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dali.didor.R;
import com.dali.didor.model.ReminderItem;
import com.dali.didor.view.list.SlideListView;

public class ReminderFragment extends Fragment {
	
	private SlideListView mListView;
	private SlideAdapter adapter;
	private List<ReminderItem> listModels = new ArrayList<ReminderItem>();
	
	LayoutInflater inflater;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	this.inflater = inflater;
    	View result =  inflater.inflate(R.layout.tab_main_reminder, null);
        
        adapter = new SlideAdapter();
		
		mListView = (SlideListView) result.findViewById(R.id.listview_list);
		mListView.initSlideMode(SlideListView.MOD_BOTH);
		for (int i = 0; i < 40; i++) {
			ReminderItem item = new ReminderItem();
				item.setTitle(i + " title");
				item.setContent(i + " content");
				item.setTime(i + " time");
			listModels.add(item);
		}
		mListView.setAdapter(adapter);
		
		final Context context = this.getContext();
		
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(context, "click:" + position, Toast.LENGTH_SHORT).show();
//				
//			}
//			
//		});
		
		return result;
    }
    
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
            	convertView = inflater.inflate(R.layout.reminder_list_item, null);

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
            ReminderItem item = listModels.get(position);

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
