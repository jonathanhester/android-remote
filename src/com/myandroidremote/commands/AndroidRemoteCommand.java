package com.myandroidremote.commands;

import android.content.Context;

public abstract class AndroidRemoteCommand {
	
	private Long id;
	private int commandType;
	
	public AndroidRemoteCommand() {
		
	}
	
	public AndroidRemoteCommand(Long id, int commandType) {
		this.id = id;
		this.commandType = commandType;
	}
	
	abstract public void execute(Context context);

	public Long getId() {
		return id;
	}
}
