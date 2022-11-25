package com.main.events;

import java.util.EventListener;

public interface MainEventListener extends EventListener {

	public void changeEmotion(MainEventObject args);

//	public void listen();

//	public void thinking();

	public void talk(MainEventObject args);

	public void stopTalk();

	public void closeApp();

}
