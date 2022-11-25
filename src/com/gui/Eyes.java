package com.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.gui.Globals.Emotion;

@SuppressWarnings("serial")
public class Eyes extends JLabel {

	private int eyesWidth;
	private int eyesHeight;

	private ArrayList<ImageIcon> normalEyes;
	private ArrayList<ImageIcon> enthusiasmEyes;
	private ArrayList<ImageIcon> joyEyes;
	private ArrayList<ImageIcon> sadnessEyes;
	private ArrayList<ImageIcon> angerEyes;
	private ArrayList<ImageIcon> fearEyes;
	private ArrayList<ImageIcon> surpriseEyes;

	private ArrayList<ImageIcon> normalToEnthusiasm;
	private ArrayList<ImageIcon> normalToJoy;
	private ArrayList<ImageIcon> normalToSurprise;
	private ArrayList<ImageIcon> normalToSadness;
	private ArrayList<ImageIcon> normalToAnger;
	private ArrayList<ImageIcon> normalToFear;

	private ArrayList<ImageIcon> eyesList;

	public Eyes(int width, int height) {

		eyesWidth = width;
		eyesHeight = height;

		setSize(eyesWidth, eyesHeight);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);

		loadImages();

	}

	public void setImage(Emotion emotion, int index) {
		eyesList = getEyesList(emotion);
		setIcon(eyesList.get(index));
	}

	public void setImage(Emotion emotion) {
		setImage(emotion, 0);
	}

	public int getEyesWidth() {
		return eyesWidth;
	}

	public void setEyesWidth(int width) {
		this.eyesWidth = width;
	}

	public int getEyesHeight() {
		return eyesHeight;
	}

	public void setEyesHeight(int height) {
		this.eyesHeight = height;
	}

	public void setSize(int width, int height) {
		this.eyesWidth = width;
		this.eyesHeight = height;
	}

	public ArrayList<ImageIcon> getEyesList(Emotion emotion) {

		switch (emotion) {
		case NORMAL:
			return normalEyes;
		case ENTHUSIASM:
			return enthusiasmEyes;
		case JOY:
			return joyEyes;
		case SURPRISE:
			return surpriseEyes;
		case SADNESS:
			return sadnessEyes;
		case ANGER:
			return angerEyes;
		case FEAR:
			return fearEyes;
		default:
			return normalEyes;
		}

	}

	private void loadImages() {

		normalEyes = loadEyesList(Globals.NORMAL_STR, 1);
		enthusiasmEyes = loadEyesList(Globals.ENTHUSIASM_STR, 1);
		joyEyes = loadEyesList(Globals.JOY_STR, 1);
		surpriseEyes = loadEyesList(Globals.SURPRISE_STR, 1);
		sadnessEyes = loadEyesList(Globals.SADNESS_STR, 1);
		angerEyes = loadEyesList(Globals.ANGER_STR, 1);
		fearEyes = loadEyesList(Globals.FEAR_STR, 1);

		normalToEnthusiasm = loadChangesList(Globals.ENTHUSIASM_STR, Globals.IMG_CANT);
		normalToJoy = loadChangesList(Globals.JOY_STR, Globals.IMG_CANT);
		normalToSurprise = loadChangesList(Globals.SURPRISE_STR, Globals.IMG_CANT);
		normalToSadness = loadChangesList(Globals.SADNESS_STR, Globals.IMG_CANT);
		normalToAnger = loadChangesList(Globals.ANGER_STR, Globals.IMG_CANT);
		normalToFear = loadChangesList(Globals.FEAR_STR, Globals.IMG_CANT);

	}

	private ArrayList<ImageIcon> loadEyesList(String emotion, int imageCant) {

		return Globals.loadImagesList( //
				Globals.PATH, emotion.toLowerCase(), //
				Globals.EYES, Globals.IMAGE_EXT, //
				eyesWidth, eyesHeight, imageCant);

	}

	private ArrayList<ImageIcon> loadChangesList(String emotion, int imageCant) {

		return Globals.loadImagesList( //
				Globals.PATH + "changes/normalTo", emotion, //
				Globals.EYES, Globals.IMAGE_EXT, //
				eyesWidth, eyesHeight, imageCant);

	}

	public ArrayList<ImageIcon> getChangesList(Emotion emotion) {

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

}
