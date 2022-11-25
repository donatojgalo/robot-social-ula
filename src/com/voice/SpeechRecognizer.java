package com.voice;

import java.io.InputStream;

public interface SpeechRecognizer {

	public void listen();

	public void recognize();

	public String getResponse();

	public InputStream synthesize(String text);

}
