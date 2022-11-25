package com.main.events;

import java.util.EventObject;

import com.gui.FaceGUI;

@SuppressWarnings("serial")
public class GuiEventObject extends EventObject {

	public enum GuiEvents {
		GuiReady,
		OnListen,
		CloseApp
	}

	private FaceGUI faceGUI;

	public GuiEventObject(Object source) {
		super(source);
		faceGUI = (FaceGUI) source;
	}

	public FaceGUI getFaceGUI() {
		return faceGUI;
	}

}
