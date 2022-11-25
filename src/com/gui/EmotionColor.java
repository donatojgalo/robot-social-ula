package com.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.gui.Globals.Emotion;

public class EmotionColor {

	public static final int COLORS_CANT = 10;

	private Color normal;
	private Color enthusiasm;
	private Color joy;
	private Color sadness;
	private Color anger;
	private Color fear;
	private Color surprise;

	private ArrayList<Color> normalToEnthusiasm;
	private ArrayList<Color> normalToJoy;
	private ArrayList<Color> normalToSurprise;
	private ArrayList<Color> normalToSadness;
	private ArrayList<Color> normalToAnger;
	private ArrayList<Color> normalToFear;

	public EmotionColor() {
		loadColors();
	}

	private void loadColors() {

		normal = new Color(220, 255, 255);		// ~White
		enthusiasm = new Color(0, 100, 255);	// Electric Blue
		joy = new Color(255, 205, 0);			// Dark Yellow
		surprise = new Color(255, 100, 0);		// Orange
		sadness = new Color(170, 180, 195);		// Light Gray
		anger = new Color(155, 0, 0);			// Dark Red
		fear = new Color(70, 70, 70);			// Purple

		try {
			readColors(Globals.COLORS_FILE);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void readColors(String colorsFile) throws IOException {

		File file = new File(colorsFile);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;
		Emotion emotion;

		while ((line = bufferedReader.readLine()) != null) {

			emotion = getEmotion(line);

			Color [] colors = new Color[COLORS_CANT];
			String colorLine;
			String [] rgbColor = new String[3];
			int [] rgb = new int[3];

			for (int i = 0; i < colors.length; i++) {

				colorLine = bufferedReader.readLine();
				colorLine.trim();

				for (int j = 0; j < rgbColor.length; j++) {
					rgbColor[j] = "";
				}

				for (int j = 0, k = 0; j < colorLine.length(); j++) {

					char cbuf = colorLine.charAt(j);

					if (cbuf == ',') {
						k++;
						continue;
					}

					rgbColor[k] += String.valueOf(cbuf);

				}

				for (int j = 0; j < rgbColor.length; j++) {
					rgb[j] = Integer.parseInt(rgbColor[j]);
				}

				colors[i] = new Color(rgb[0], rgb[1], rgb[2]);

			}

			loadColorsList(emotion, colors);

		}

		if (fileReader != null) {
			fileReader.close();
		}

	}

	private Emotion getEmotion(String line) {

		if (line.equals(Globals.ENTHUSIASM_MSG)) {
			return Emotion.ENTHUSIASM;
		} else if (line.equals(Globals.JOY_MSG)) {
			return Emotion.JOY;
		} else if (line.equals(Globals.SURPRISE_MSG)) {
			return Emotion.SURPRISE;
		} else if (line.equals(Globals.SADNESS_MSG)) {
			return Emotion.SADNESS;
		} else if (line.equals(Globals.ANGER_MSG)) {
			return Emotion.ANGER;
		} else if (line.equals(Globals.FEAR_MSG)) {
			return Emotion.FEAR;
		} else {
			return Emotion.NORMAL;
		}

	}

	private void loadColorsList(Emotion emotion, Color [] colors) {

		switch (emotion) {
		case NORMAL:
			break;
		case ENTHUSIASM:
			normalToEnthusiasm = createColorList(colors);
			break;
		case JOY:
			normalToJoy = createColorList(colors);
			break;
		case SURPRISE:
			normalToSurprise = createColorList(colors);
			break;
		case SADNESS:
			normalToSadness = createColorList(colors);
			break;
		case ANGER:
			normalToAnger = createColorList(colors);
			break;
		case FEAR:
			normalToFear = createColorList(colors);
			break;
		default:
			break;
		}

	}

	private ArrayList<Color> createColorList(Color [] color) {

		ArrayList<Color> list = new ArrayList<Color>();

		for (int i = 0; i < color.length; i++) {
			list.add(color[i]);
		}

		return list;

	}

	public ArrayList<Color> getColorList(Emotion emotion) {

		switch (emotion) {
		case NORMAL:
			return null;
		case ENTHUSIASM:
			return normalToEnthusiasm;
		case JOY:
			return normalToJoy;
		case SURPRISE:
			return normalToSurprise;
		case SADNESS:
			return normalToSadness;
		case ANGER:
			return normalToAnger;
		case FEAR:
			return normalToFear;
		default:
			return null;
		}

	}

	public Color getColor(Emotion emotion) {

		switch (emotion) {
		case NORMAL:
			return normal;
		case ENTHUSIASM:
			return enthusiasm;
		case JOY:
			return joy;
		case SURPRISE:
			return surprise;
		case SADNESS:
			return sadness;
		case ANGER:
			return anger;
		case FEAR:
			return fear;
		default:
			return normal;
		}

	}

}
