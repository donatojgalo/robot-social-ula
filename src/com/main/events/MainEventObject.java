package com.main.events;

import java.util.EventObject;

import com.gui.Globals.Emotion;
import com.main.MainApp;

@SuppressWarnings("serial")
public class MainEventObject extends EventObject {

	public enum MainEvents {
		ChangeEmotion,
//		Listen,
//		Thinking,
		Talk,
		StopTalk,
		CloseApp
	}

	private MainApp mainApp;

	public MainEventObject(Object source) {
		super(source);		
		mainApp = (MainApp) source;
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public Emotion getEmotion() {
		return mainApp.getEmotion();
	}

	public String getMessage() {
		return mainApp.getMessage();
	}

}
