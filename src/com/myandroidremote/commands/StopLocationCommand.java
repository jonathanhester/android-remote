package com.myandroidremote.commands;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.myandroidremote.Util;
import com.myandroidremote.shared.AndroidCommandProxy;
import com.myandroidremote.shared.MyAndroidRemoteRequestFactory;

public class StopLocationCommand extends AndroidRemoteCommand {

	
	public StopLocationCommand(Long id, int commandType) {
		super(id, commandType);
	}
	
	public void sendLocation(Location location, Context context) {
		MyAndroidRemoteRequestFactory requestFactory = Util.getRequestFactory(
				context, MyAndroidRemoteRequestFactory.class);

		requestFactory
				.androidCommandRequest()
				.respondLocationCommand(getId(), location.getLatitude(),
						location.getLongitude(), location.getTime())
				.fire(new Receiver<AndroidCommandProxy>() {

					@Override
					public void onSuccess(AndroidCommandProxy arg0) {
						

					}
				});
	}

	public void execute(final Context context) {
		LocationManagerThread locationManagerThread = LocationManagerThread.getInstance(context);
		locationManagerThread.stop();
	}

}
