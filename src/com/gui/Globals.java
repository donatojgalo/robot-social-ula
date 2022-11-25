package com.gui;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Globals {

	public enum Emotion {
		NORMAL,
		ENTHUSIASM,
		JOY,
		SURPRISE,
		SADNESS,
		ANGER,
		FEAR
	}

	public static final int IMG_CANT = 5;
	public static final int BOTTOM_SIZE = 110;
	public static final long SPEED = 50;
	public static final long DELAY = 75;

	public static final String COLORS_FILE = "colors.txt";
	public static final String PATH = "src/com/gui/images/";
	public static final String IMAGE_EXT = ".png";
	public static final String MIC_ICON = PATH + "mic" + IMAGE_EXT;
	public static final String EYES = "Eyes";
	public static final String MOUTH = "Mouth";

	public static final String NORMAL_STR = "Normal";
	public static final String ENTHUSIASM_STR = "Enthusiasm";
	public static final String JOY_STR = "Joy";
	public static final String SURPRISE_STR = "Surprise";
	public static final String SADNESS_STR = "Sadness";
	public static final String ANGER_STR = "Anger";
	public static final String FEAR_STR = "Fear";

	public static final String NORMAL_MSG = "nor:";
	public static final String ENTHUSIASM_MSG = "ent:";
	public static final String JOY_MSG = "joy:";
	public static final String SURPRISE_MSG = "sur:";
	public static final String SADNESS_MSG = "sad:";
	public static final String ANGER_MSG = "ang:";
	public static final String FEAR_MSG = "fea:";

	private Globals() {
		/* empty */
	}

	public static ArrayList<ImageIcon> loadImagesList( //
			String path, String emotion, String part, String extention, //
			int width, int height, int imageCant) {

		ImageIcon image;
		ArrayList<ImageIcon> list = new ArrayList<ImageIcon>();

		for (int i = 0; i < imageCant; i++) {

			System.out.println(path + emotion + part + i + extention);

			image = new ImageIcon(path + emotion + part + i + extention);
			image = new ImageIcon(image.getImage().getScaledInstance( //
					width, height, Image.SCALE_DEFAULT));

			list.add(image);

		}

		return list;

	}

}
