package com.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.gui.Globals.Emotion;
import com.main.events.GuiEventListener;
import com.main.events.GuiEventObject.GuiEvents;
import com.main.events.MainEventListener;
import com.main.events.MainEventObject;

@SuppressWarnings("serial")
public class FaceGUI extends JFrame implements MainEventListener, Runnable {

	private ArrayList<GuiEventListener> listeners;

	private int width;
	private int height;
	private int faceWidth;
	private int faceHeight;
	private Container container;
	//	private JLabel lblMessage;

	private Emotion emotion;
	private Emotion prevEmotion;
	private EmotionColor emotionColor;
	private Eyes eyes;
	private Mouth mouth;

	private Timer timer;
	private TimerTask task;
	private boolean talking;

	public FaceGUI(String title, int width, int height) {

		super(title);

		listeners = new ArrayList<GuiEventListener>();

		this.width = width;
		this.height = height;

		talking = false;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				fireEvent(GuiEvents.CloseApp);
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyManager(e);
			};
		});

	}

	private void initGui() {

		container = getContentPane();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		emotion = Emotion.NORMAL;
		prevEmotion = Emotion.NORMAL;
		emotionColor = new EmotionColor();

		faceWidth = Math.min(width, height) - Globals.BOTTOM_SIZE;
		faceHeight = faceWidth / 2;

		eyes = new Eyes(faceWidth, faceHeight);
		mouth = new Mouth(faceWidth, faceHeight);

		changeImage();

		container.add(eyes);
		container.add(mouth);

		JButton btnListen = new JButton(new ImageIcon(Globals.MIC_ICON));
		btnListen.setFocusable(false);
		btnListen.setBackground(new Color(0, 0, 0, 0));
		btnListen.setBorderPainted(false);
		btnListen.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnListen.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnListen.setFocusPainted(false);
		btnListen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				fireEvent(GuiEvents.OnListen);
			}
		});
		container.add(btnListen);

		//		lblMessage = new JLabel();
		//		lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		//		lblMessage.setAlignmentY(Component.CENTER_ALIGNMENT);
		//		container.add(lblMessage);

		setVisible(true);

		fireEvent(GuiEvents.GuiReady);

	}

	public synchronized void addGuiEventListener(GuiEventListener listener)  {
		listeners.add(listener);
	}

	public synchronized void removeGuiEventListener(GuiEventListener listener)   {
		listeners.remove(listener);
	}

	private synchronized void fireEvent(GuiEvents event) {

		Iterator<GuiEventListener> it = listeners.iterator();

		while (it.hasNext()) {

			GuiEventListener listener = (GuiEventListener) it.next();

			switch (event) {
			case GuiReady:
				listener.guiReady();
				break;
			case OnListen:
				listener.onListen();
				break;
			case CloseApp:
				listener.closeApp();
				break;
			default:
				break;
			}

		}

	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public TimerTask getTask() {
		return task;
	}

	public void setTask(TimerTask task) {
		this.task = task;
	}

	public boolean isTalking() {
		return talking;
	}

	public void setTalking(boolean talking) {
		this.talking = talking;
	}

	private void changeImage(Emotion emotion) {

		eyes.setImage(emotion);
		mouth.setImage(emotion);

		container.setBackground(emotionColor.getColor(emotion));

	}

	private void changeImage() {

		if (talking) {
			stopAnimation();
		}

		changeImage(emotion);

	}

	@Override
	public void run() {
		initGui();
	}

	//	private void changeMessage(String message) {
	//		lblMessage.setText(message);
	//	}

	public void changeEmotion(Emotion newEmotion) {

		if (talking) {
			return;
		}

		prevEmotion = emotion;
		emotion = newEmotion;

		changeEmotionAnimation();

	}

	@Override
	public void changeEmotion(MainEventObject args) {

		if (args == null) {
			return;
		}

		Emotion emotion = args.getEmotion();
		changeEmotion(emotion);

	}

	//	@Override
	//	public void listen() {
	//		changeMessage("Habla ahora...");
	//	}

	//	@Override
	//	public void thinking() {
	//		changeMessage("Pensando...");
	//	}

	@Override
	public void talk(MainEventObject args) {

		if (talking) {
			return;
		}

		Emotion aux = emotion;

		emotion = (args == null) ? Emotion.NORMAL : args.getEmotion();

		//		changeMessage("Hablando...");
		talkAnimation();

		emotion = aux;

	}

	@Override
	public void stopTalk() {

		if (!talking) {
			return;
		}

		stopAnimation();

	}

	@Override
	public void closeApp() {

		stopAnimation();

		setVisible(false);
		dispose();

	}

	private void keyManager(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			fireEvent(GuiEvents.OnListen);
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			talk(null);
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			stopTalk();
		} else if (e.getKeyCode() == KeyEvent.VK_N) {
			changeEmotion(Emotion.NORMAL);
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			changeEmotion(Emotion.ENTHUSIASM);
		} else if (e.getKeyCode() == KeyEvent.VK_J) {
			changeEmotion(Emotion.JOY);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			changeEmotion(Emotion.SURPRISE);
		} else if (e.getKeyCode() == KeyEvent.VK_T) {
			changeEmotion(Emotion.SADNESS);
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			changeEmotion(Emotion.ANGER);
		} else if (e.getKeyCode() == KeyEvent.VK_F) {
			changeEmotion(Emotion.FEAR);
		}

	}

	private void talkAnimation() {

		final ArrayList<ImageIcon> mouthList = mouth.getMouthsList(emotion);

		if (mouthList == null) {
			talking = false;
			return;
		}

		final int mouthListSize = mouthList.size();

		if (mouthListSize < 2) {
			talking = false;
			return;
		}

		talking = true;

		timer = new Timer();

		task = new TimerTask() {

			@Override
			public void run() {

				boolean ctl = false;
				int i = 0;

				do {

					mouth.setIcon(mouthList.get(i));

					i += ctl ? -1 : 1;

					if (i == mouthListSize) {
						i--;
						ctl = true;
					}

					try {
						Thread.sleep(Globals.DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} while (i >= 0);

				changeImage(emotion);

			}

		};

		timer.schedule(task, 0, Globals.SPEED);

	}

	private void changeEmotionAnimation() {

		if (emotion == prevEmotion) {
			return;
		} else if (emotion == Emotion.NORMAL) {
			emotionToNormalAnimation();
		} else if (prevEmotion == Emotion.NORMAL) {
			normalToEmotionAnimation();
		} else {
			emotionToEmotionAnimation();
		}

	}

	private void normalToEmotionAnimation() {

		final ArrayList<ImageIcon> mouthList = mouth.getChangesList(emotion);
		final ArrayList<ImageIcon> eyesList = eyes.getChangesList(emotion);

		if ((mouthList == null) || (eyesList == null)) {
			talking = false;
			return;
		}

		int mouthListSize = mouthList.size();
		int eyesListSize = eyesList.size();

		if ((mouthListSize < 2) || (eyesListSize < 2)) {
			talking = false;
			return;
		}

		final ArrayList<Color> colorsList = emotionColor.getColorList(emotion);

		talking = true;

		timer = new Timer();

		task = new TimerTask() {

			@Override
			public void run() {

				//				for (int i = 0; i < Globals.IMG_CANT; i++) {
				for (int i = 0, j = 0; i < EmotionColor.COLORS_CANT; i++) {

					if (!(i % 2 == 0)) {
						eyes.setIcon(eyesList.get(j));
						mouth.setIcon(mouthList.get(j));
						j++;
					}

					if (colorsList != null) {
						container.setBackground(colorsList.get(i));
					}

					try {
						Thread.sleep(Globals.DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				changeImage();

			}

		};

		timer.schedule(task, 0, Globals.SPEED);

	}

	private void emotionToNormalAnimation() {

		final ArrayList<ImageIcon> mouthList = mouth.getChangesList(prevEmotion);
		final ArrayList<ImageIcon> eyesList = eyes.getChangesList(prevEmotion);

		if ((mouthList == null) || (eyesList == null)) {
			talking = false;
			return;
		}

		int mouthListSize = mouthList.size();
		int eyesListSize = eyesList.size();

		if ((mouthListSize < 2) || (eyesListSize < 2)) {
			talking = false;
			return;
		}

		final ArrayList<Color> colorsList = emotionColor.getColorList(prevEmotion);

		talking = true;

		timer = new Timer();

		task = new TimerTask() {

			@Override
			public void run() {

				//				for (int i = Globals.IMG_CANT - 1; i >= 0 ; i--) {
				for (int i = EmotionColor.COLORS_CANT - 1, //
						j = Globals.IMG_CANT - 1; //
						i >= 0; i--) {

					if (i % 2 == 0) {
						eyes.setIcon(eyesList.get(j));
						mouth.setIcon(mouthList.get(j));
						j--;
					}

					if (colorsList != null) {
						container.setBackground(colorsList.get(i));
					}

					try {
						Thread.sleep(Globals.DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				changeImage();

			}

		};

		timer.schedule(task, 0, Globals.SPEED);

	}

	private void emotionToEmotionAnimation() {

		final ArrayList<ImageIcon> mouthList = mouth.getChangesList(emotion);
		final ArrayList<ImageIcon> prevMouthList = mouth.getChangesList(prevEmotion);
		final ArrayList<ImageIcon> eyesList = eyes.getChangesList(emotion);
		final ArrayList<ImageIcon> prevEyesList = eyes.getChangesList(prevEmotion);

		if ((mouthList == null) || (eyesList == null) //
				|| (prevMouthList == null) || (prevEyesList == null)) {
			talking = false;
			return;
		}

		int mouthListSize = mouthList.size();
		int prevMouthListSize = prevMouthList.size();
		int eyesListSize = eyesList.size();
		int prevEyesListSize = prevEyesList.size();

		if ((mouthListSize < 2) || (eyesListSize < 2) //
				|| (prevMouthListSize < 2) || (prevEyesListSize < 2)) {
			talking = false;
			return;
		}

		final ArrayList<Color> colorsList = emotionColor.getColorList(emotion);
		final ArrayList<Color> prevColorsList = emotionColor.getColorList(prevEmotion);

		talking = true;

		timer = new Timer();

		task = new TimerTask() {

			@Override
			public void run() {

				//					for (int i = Globals.IMG_CANT - 1; i >= 0 ; i--) {
				for (int i = EmotionColor.COLORS_CANT - 1, //
						j = Globals.IMG_CANT - 1; //
						i >= 0; i--) {

					if (i % 2 == 0) {
						eyes.setIcon(prevEyesList.get(j));
						mouth.setIcon(prevMouthList.get(j));
						j--;
					}

					if (colorsList != null) {
						container.setBackground(prevColorsList.get(i));
					}

					try {
						Thread.sleep(Globals.DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				changeImage(Emotion.NORMAL);

				try {
					Thread.sleep(Globals.DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				//				for (int i = 0; i < Globals.IMG_CANT; i++) {
				for (int i = 0, j = 0; i < EmotionColor.COLORS_CANT; i++) {

					if (!(i % 2 == 0)) {
						eyes.setIcon(eyesList.get(j));
						mouth.setIcon(mouthList.get(j));
						j++;
					}

					if (colorsList != null) {
						container.setBackground(colorsList.get(i));
					}

					try {
						Thread.sleep(Globals.DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

				changeImage();

			}

		};

		timer.schedule(task, 0, Globals.SPEED);

	}

	private void stopAnimation() {

		if (timer != null) {
			timer.cancel();
		}

		if (task != null) {
			task.cancel();
		}

		talking = false;
		//		changeMessage("");

	}

}
