package com.main;

import com.gui.FaceGUI;

public class Main {

	public static void main(String[] args) {

		MainApp mainApp = new MainApp();

		//		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//		Dimension dimension = toolkit.getScreenSize();
		//
		//		FaceGUI faceGUI = new FaceGUI("Robot Social ULA", //
		//				(int) dimension.getWidth(), (int) dimension.getHeight());

		FaceGUI faceGUI = new FaceGUI("Robot Social ULA", 800, 700);
		Thread thread = new Thread(faceGUI);
		thread.start();

		mainApp.addMainEventListener(faceGUI);
		faceGUI.addGuiEventListener(mainApp);

	}

}
