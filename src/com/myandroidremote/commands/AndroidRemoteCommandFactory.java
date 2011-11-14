package com.myandroidremote.commands;

import com.myandroidremote.shared.RemoteCommands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AndroidRemoteCommandFactory {

	public static AndroidRemoteCommand parseC2DMMessage(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			int command = Integer.parseInt((String) extras
					.get(RemoteCommands.PARAM_COMMAND));
			long commandId = Long.parseLong((String) extras
					.get(RemoteCommands.PARAM_COMMAND_ID));
			switch (command) {
				case RemoteCommands.COMMAND_GET_LOCATION:
					Log.d("command", "get location");
					return new LocationCommand(commandId, command);
				case RemoteCommands.COMMAND_STOP_LOCATION:
					Log.d("command", "stop location");
					return new StopLocationCommand(commandId, command);
				case RemoteCommands.COMMAND_PING:
					Log.d("command", "ping phone");
					return new PingCommand(commandId, command);
				case RemoteCommands.COMMAND_OPEN_URI:
					Log.d("command", "open uri");
					String uri = ""; //(String) extras.get(RemoteCommands.PARAM_URI);
					return new OpenUriCommand(commandId, command, uri);
				case RemoteCommands.COMMAND_PLAY_AUDIO:
					return new AudioCommand(commandId, command);
				case RemoteCommands.COMMAND_STOP_AUDIO:
					return new StopAudioCommand(commandId, command);

			}
		}
		return null;
	}
}
