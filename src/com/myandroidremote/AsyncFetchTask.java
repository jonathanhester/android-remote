package com.myandroidremote;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.myandroidremote.commands.AndroidRemoteCommand;
import com.myandroidremote.shared.AndroidCommandProxy;

public class AsyncFetchTask extends
		AsyncTask<Void, Void, Void> {

	private AndroidRemoteCommand command;
	private Context context;

	public AsyncFetchTask(Context context, AndroidRemoteCommand command) {
		super();
		this.context = context;
		this.command = command;
	}

	@Override
	protected Void doInBackground(Void... arguments) {
		//command.respond(context);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
	}
}
