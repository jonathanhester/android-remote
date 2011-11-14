package com.myandroidremote.commands;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.myandroidremote.R;
import com.myandroidremote.Util;
import com.myandroidremote.shared.AndroidCommandProxy;
import com.myandroidremote.shared.MyAndroidRemoteRequestFactory;

public class AudioCommand extends AndroidRemoteCommand {

	public AudioCommand(Long id, int commandType) {
		super(id, commandType);
	}

	public void execute(final Context context) {
		AudioManagerThread audioManagerThread = AudioManagerThread.getInstance(context);
		audioManagerThread.start(this);
		
		MyAndroidRemoteRequestFactory requestFactory = Util.getRequestFactory(
				context, MyAndroidRemoteRequestFactory.class);

		requestFactory.androidCommandRequest().respondAndroidCommand(getId()).fire(
			new Receiver<AndroidCommandProxy>() {

				@Override
				public void onSuccess(AndroidCommandProxy arg0) {
					// TODO Auto-generated method stub

				}
			});
	}

}
