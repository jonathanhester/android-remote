package com.myandroidremote.commands;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.myandroidremote.R;

public class AudioManagerThread {
	private static AudioManagerThread audioManagerThread = null;
	private Context context;
	private MediaPlayer mediaPlayer;
	private Handler handler = new Handler();
	Thread t;

	public static AudioManagerThread getInstance(Context context) {
		if (audioManagerThread == null) {
			audioManagerThread = new AudioManagerThread(context);
		}
		return audioManagerThread;
	}

	public AudioManagerThread(Context context) {
		this.context = context;
	}

	private MediaPlayer getMediaPlayer(final Context context) {
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(context, R.raw.steppin_out);
		}
		return mediaPlayer;
	}

	public void start(AudioCommand audioCommand) {
		if (t != null)
			return;
		
		t = new Thread() {
			public void run() {
				MediaPlayer mediaPlayer = getMediaPlayer(context);
				if (!mediaPlayer.isPlaying()) {
					mediaPlayer.start(); // no need to call prepare(); create() does
											// that for you
				}
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (mediaPlayer.isPlaying()) {
					AudioManagerThread.this.stop();
				}
			}
		};
		t.start();		
	}

	public void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}

}
