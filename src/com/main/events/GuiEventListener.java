package com.main.events;

import java.util.EventListener;

public interface GuiEventListener extends EventListener {

	public void guiReady();

	public void onListen();

	public void closeApp();

}
