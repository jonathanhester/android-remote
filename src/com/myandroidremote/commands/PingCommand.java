package com.myandroidremote.commands;

import android.content.Context;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.myandroidremote.Util;
import com.myandroidremote.shared.AndroidCommandProxy;
import com.myandroidremote.shared.MyAndroidRemoteRequestFactory;

public class PingCommand extends AndroidRemoteCommand {

	public PingCommand(Long id, int commandType) {
		super(id, commandType);
	}
	
	public void execute(Context context) {
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
