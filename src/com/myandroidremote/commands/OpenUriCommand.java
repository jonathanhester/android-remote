package com.myandroidremote.commands;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.myandroidremote.Util;
import com.myandroidremote.shared.AndroidCommandProxy;
import com.myandroidremote.shared.MyAndroidRemoteRequestFactory;

public class OpenUriCommand extends AndroidRemoteCommand {
	
	String uri;
	
	public OpenUriCommand(Long id, int commandType, String uri) {
		super(id, commandType);
		this.uri = uri;
	}
	
	@Override
	public void execute(Context context) {
		Log.d("", uri);
		getUri(context);
		// TODO Auto-generated method stub

	}
	
	private void getUri(final Context context) {
		MyAndroidRemoteRequestFactory requestFactory = Util.getRequestFactory(
				context, MyAndroidRemoteRequestFactory.class);

		requestFactory
				.androidCommandRequest()
				.readOpenUriCommand(getId())
				.fire(new Receiver<AndroidCommandProxy>() {

					@Override
					public void onSuccess(AndroidCommandProxy uriCommand) {
						String uriString = uriCommand.getUri();
						Uri uri = Uri.parse(uriString);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				});
	}
}
