package com.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.gui.Globals.Emotion;

@SuppressWarnings("serial")
public class Mouth extends JLabel {

	private int mouthWidth;
	private int mouthHeight;

	private ArrayList<ImageIcon> normalMouth;
	private ArrayList<ImageIcon> enthusiasmMouth;
	private ArrayList<ImageIcon> joyMouth;
	private ArrayList<ImageIcon> surpriseMouth;
	private ArrayList<ImageIcon> sadnessMouth;
	private ArrayList<ImageIcon> angerMouth;
	private ArrayList<ImageIcon> fearMouth;

	private ArrayList<ImageIcon> normalToEnthusiasm;
	private ArrayList<ImageIcon> normalToJoy;
	private ArrayList<ImageIcon> normalToSurprise;
	private ArrayList<ImageIcon> normalToSadness;
	private ArrayList<ImageIcon> normalToAnger;
	private ArrayList<ImageIcon> normalToFear;

	private ArrayList<ImageIcon> mouthList;

	public Mouth(int width, int height) {

		mouthWidth = width;
		mouthHeight = height;

		setSize(mouthWidth, mouthHeight);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);

		loadImages();

	}

	public void setImage(Emotion emotion, int index) {
		mouthList = getMouthsList(emotion);
		setIcon(mouthList.get(index));
	}

	public void setImage(Emotion emotion) {
		setImage(emotion, 0);
	}

	public int getMouthWidth() {
		return mouthWidth;
	}

	public void setMouthWidth(int width) {
		this.mouthWidth = width;
	}

	public int getMouthHeight() {
		return mouthHeight;
	}

	public void setMouthHeight(int height) {
		this.mouthHeight = height;
	}

	public void setFaceSize(int width, int height) {
		this.mouthWidth = width;
		this.mouthHeight = height;
	}

	public ArrayList<ImageIcon> getMouthsList(Emotion emotion) {

		switch (emotion) {
		case NORMAL:
			return normalMouth;
		case ENTHUSIASM:
			return enthusiasmMouth;
		case JOY:
			return joyMouth;
		case SURPRISE:
			return surpriseMouth;
		case SADNESS:
			return sadnessMouth;
		case ANGER:
			return angerMouth;
		case FEAR:
			return fearMouth;
		default:
			return normalMouth;
		}

	}

	private void loadImages() {

		normalMouth = loadMouthsList(Globals.NORMAL_STR, 5);
		enthusiasmMouth = loadMouthsList(Globals.ENTHUSIASM_STR, 1);
		joyMouth = loadMouthsList(Globals.JOY_STR, 1);
		surpriseMouth = loadMouthsList(Globals.SURPRISE_STR, 1);
		sadnessMouth = loadMouthsList(Globals.SADNESS_STR, 1);
		angerMouth = loadMouthsList(Globals.ANGER_STR, 1);
		fearMouth = loadMouthsList(Globals.FEAR_STR, 1);

		normalToEnthusiasm = loadChangesList(Globals.ENTHUSIASM_STR, Globals.IMG_CANT);
		normalToJoy = loadChangesList(Globals.JOY_STR, Globals.IMG_CANT);
		normalToSurprise = loadChangesList(Globals.SURPRISE_STR, Globals.IMG_CANT);
		normalToSadness = loadChangesList(Globals.SADNESS_STR, Globals.IMG_CANT);
		normalToAnger = loadChangesList(Globals.ANGER_STR, Globals.IMG_CANT);
		normalToFear = loadChangesList(Globals.FEAR_STR, Globals.IMG_CANT);

	}

	private ArrayList<ImageIcon> loadMouthsList(String emotion, int imageCant) {		

		return Globals.loadImagesList( //
				Globals.PATH, emotion.toLowerCase(), //
				Globals.MOUTH, Globals.IMAGE_EXT, //
				mouthWidth, mouthHeight, imageCant);

	}

	private ArrayList<ImageIcon> loadChangesList(String emotion, int imageCant) {

		return Globals.loadImagesList( //
				Globals.PATH + "changes/normalTo", emotion, //
				Globals.MOUTH, Globals.IMAGE_EXT, //
				mouthWidth, mouthHeight, imageCant);

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
