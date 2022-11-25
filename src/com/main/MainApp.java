package com.main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javazoom.jl.player.Player;

import com.gui.Globals;
import com.gui.Globals.Emotion;
import com.main.events.GuiEventListener;
import com.main.events.MainEventListener;
import com.main.events.MainEventObject;
import com.main.events.MainEventObject.MainEvents;
import com.search.wiki.HtmlUtils;
import com.voice.RobotRecognizer;
import com.voice.SpeechRecognizer;

public class MainApp implements GuiEventListener {

	private ArrayList<MainEventListener> listeners;

	private static final int STR_SIZE = 300;
	private static final int PORT = 5000;
	private static final String SERVER_DIR = "localhost";
	private static final String EXIT = "salir";
	private static final String SEARCH_WIKI = "buscar en Wikipedia";

	private SpeechRecognizer speechRecognizer;
	private Player player;

	private String toServer;
	private String fromServer;
	private String emotionFromServer;
	private static Socket clientSocket;
	private static PrintWriter outToServer;

	private Emotion emotion;
	private String message;

	public MainApp() {

		listeners = new ArrayList<MainEventListener>();

		emotion = Emotion.NORMAL;

		speechRecognizer = new RobotRecognizer();
		

	}

	public synchronized void addMainEventListener(MainEventListener listener)  {
		listeners.add(listener);
	}

	public synchronized void removeMainEventListener(MainEventListener listener)   {
		listeners.remove(listener);
	}

	private synchronized void fireEvent(MainEvents event) {

		MainEventObject mainEventObject = new MainEventObject(this);

		Iterator<MainEventListener> it = listeners.iterator();

		while (it.hasNext()) {

			MainEventListener listener = (MainEventListener) it.next();

			switch (event) {
			case ChangeEmotion:
				listener.changeEmotion(mainEventObject);
				break;
//			case Listen:
//				listener.listen();
//				break;
//			case Thinking:
//				listener.thinking();
//				break;
			case Talk:
				listener.talk(mainEventObject);
				break;
			case StopTalk:
				listener.stopTalk();
				break;
			case CloseApp:
				listener.closeApp();
				break;
			default:
				break;
			}

		}

	}

	private void connect() {

		try {
			clientSocket = new Socket(SERVER_DIR, PORT);
			outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void listening() {

//		fireEvent(MainEvents.Listen);

		speechRecognizer.listen();

		recognizing();

	}

	private void recognizing() {

//		fireEvent(MainEvents.Thinking);

		toServer = null;

		speechRecognizer.recognize();	
		
		//System.out.println(speechRecognizer.getResponse());

		//while (toServer == null) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toServer = speechRecognizer.getResponse();
		//}

				if (toServer == null) {
					toServer = "nulo";
		//			System.out.println("toServer = " + toServer);
		//			return;
				}

	    System.out.println("Recognition= -" + toServer+"-");
		if (toServer.contains(EXIT.subSequence(0, EXIT.length() - 1))) {
			close();
		} else if (toServer.contains( //
				SEARCH_WIKI.subSequence(0, SEARCH_WIKI.length() - 1))) {
			searchWikipedia();
		} else {
			System.out.println("toServer = -" + toServer+"-");
			outToServer.println(toServer);
			readFromServer();
		}
		//System.out.println("salio recogninizing");

	}

	private void searchWikipedia() {

		talk("¿Qué deseas buscar?");

		String search = null;
		String answer = null;
		String answer2 = null;
		char c;
		int cont=0;

		speechRecognizer.listen();
		speechRecognizer.recognize();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			search = speechRecognizer.getResponse();
			System.out.println("Recognition: -"+search+"-");
			if(search==null){
		      talk("Me da pereza buscar eso");		
			}else{
		
              try {
        	    answer = HtmlUtils.getTextFromWikipedia(search);	
              } catch(StringIndexOutOfBoundsException e){
        	    answer = "No quiero buscar nada sobre eso";        	
              } 
		
            System.out.println("+"+answer+"+");
            answer2=""; 
            for (int i = 0; i <answer.length (); i++) { 
        	  c = answer.charAt (i); 
        	  if (c=='.'){
        		cont=cont+1;
        	  }
        	  if(cont==1){
        		break;
        	  }else{
        	    answer2 = answer2 + c;
        	  }
        	
        	
        }
        System.out.println ("*"+answer2+"*");
		talk(answer2);
	    }
	}

	private void readFromServer() {

		InputStreamReader ins = null;
		try {
			ins = new InputStreamReader(clientSocket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		char[] data = new char[STR_SIZE];
		try {
			ins.read(data, 0, (STR_SIZE - 1));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		emotionFromServer = "";
		fromServer = "";

		for (int i = 0; i < 4; i++) {
			if (data[i] != '\0') {
				emotionFromServer += data[i];
			}
		}

		for (int i = 4; i < data.length; i++) {

			if (data[i] == '\0') {
				break;
			}

			fromServer += data[i];

		}

		System.out.println("Recibido: -" + fromServer + "-");

		changeEmotion();
		talk(fromServer);

	}

	private void changeEmotion() {

		if (emotionFromServer.equals(Globals.NORMAL_MSG)) {
			setEmotion(Emotion.NORMAL);
		} else if (emotionFromServer.equals(Globals.ENTHUSIASM_MSG)) {
			setEmotion(Emotion.ENTHUSIASM);
		} else if (emotionFromServer.equals(Globals.JOY_MSG)) {
			setEmotion(Emotion.JOY);
		} else if (emotionFromServer.equals(Globals.SURPRISE_MSG)) {
			setEmotion(Emotion.SURPRISE);
		} else if (emotionFromServer.equals(Globals.SADNESS_MSG)) {
			setEmotion(Emotion.SADNESS);
		} else if (emotionFromServer.equals(Globals.ANGER_MSG)) {
			setEmotion(Emotion.ANGER);
		} else if (emotionFromServer.equals(Globals.FEAR_MSG)) {
			setEmotion(Emotion.FEAR);
		} else {
			setEmotion(Emotion.NORMAL);
		}

		fireEvent(MainEvents.ChangeEmotion);

	}

	private void talk(String words) {

		fireEvent(MainEvents.Talk);

		try {

			player = new Player(speechRecognizer.synthesize(words));
			player.play();

		} catch (Exception e) {

			fireEvent(MainEvents.StopTalk);
			System.out.println("Error en sintetizador: " + e.toString());

		}

		fireEvent(MainEvents.StopTalk);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Emotion getEmotion() {
		return emotion;
	}

	public void setEmotion(Emotion emotion) {
		this.emotion = emotion;
	}

	private void close() {

		if (clientSocket.isConnected()) {
			outToServer.println(EXIT);
		}

		disconnect();
		fireEvent(MainEvents.CloseApp);

	}

	private void disconnect() {

		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void guiReady() {
		connect();
	}

	@Override
	public void onListen() {
		listening();
	}

	@Override
	public void closeApp() {
		close();
	}

}
