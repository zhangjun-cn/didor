package com.dali.didor.alarm;

import com.dali.didor.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AlarmService extends Service {

	private MediaPlayer mediaPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer = MediaPlayer.create(AlarmService.this, R.raw.alarm);
			// mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		mediaPlayer.start();

		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				try {
					//mp.start();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {
				try {
					mp.release();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return false;
			}
		});

		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		mediaPlayer.stop();
		mediaPlayer.release();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
