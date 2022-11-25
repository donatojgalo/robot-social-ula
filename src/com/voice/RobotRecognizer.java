package com.voice;

import java.io.File;
import java.io.InputStream;
import javaFlacEncoder.FLACFileWriter;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.RecognizerChunked;
import com.darkprograms.speech.synthesiser.Synthesizer;

public class RobotRecognizer implements SpeechRecognizer {

	private static final long WAIT_TIME = 4000;
	private static final String FLAC_FILE = "flacFile.flac";
	private static final String API_KEY = "AIzaSyBYOU5xCndeCDquB_WQMogvNoJpn1gxJsI";//Donato
	//private static final String API_KEY = "AIzaSyCFcFxEj-S2Y4ENwVgPfVKgo8IgJT37pRw";//Jesus
	private static final String LANG = Synthesizer.LANG_ES_SPANISH;

	private String apiKey;
	private String language;
	private RecognizerChunked recognizerChunked;
	private Microphone microphone;
	private File file;
	private String response;

	public RobotRecognizer() {
		init(API_KEY, LANG);
	}

	public RobotRecognizer(String apiKey) {
		init(apiKey, LANG);
	}

	public RobotRecognizer(String apiKey, String language) {
		init(apiKey, language);
	}

	private void init(String apiKey, String language) {

		this.apiKey = apiKey;
		this.language = language;

		microphone = new Microphone(FLACFileWriter.FLAC);

		file = new File(FLAC_FILE);

		recognizerChunked = new RecognizerChunked(this.apiKey, this.language);

		recognizerChunked.addResponseListener(new GSpeechResponseListener() {

			@Override
			public void onResponse(GoogleResponse gr) {
				response = gr.getResponse();
			}

		});

	}

	@Override
	public void listen() {

		//System.out.println("entro listen");
		response = null;

		try {
			microphone.captureAudioToFile(file);
		} catch (Exception ex) { //Microphone not available or some other error.
			System.out.println("ERROR: Microphone is not availible. " + ex.getMessage());
		}

		/* User records the voice here.
		 * Microphone starts a separate thread so do whatever you want in the mean time.
		 * Show a recording icon or whatever.
		 */
		try {

			System.out.println("Recording...");
			Thread.sleep(WAIT_TIME);
//			microphone.close();

		} catch (InterruptedException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}

		microphone.close(); //Ends recording and frees the resources
		System.out.println("Recording stopped.");
		//System.out.println("salio listen");
	}

	@Override
	public void recognize() {
		//System.out.println("entro recognize");

		try {

			recognizerChunked.getRecognizedDataForFlac( //
					file, (int) microphone.getAudioFormat().getSampleRate());

		} catch (Exception ex) {
			// TODO Handle how to respond if Google cannot be contacted
			System.out.println("ERROR: Google cannot be contacted. " + ex.getMessage());
//			ex.printStackTrace();
		}

		//file.deleteOnExit(); //Deletes the file as it is no longer necessary.
		//System.out.println("salio recognize");
	}

	@Override
	public String getResponse() {
		//System.out.println("entro getResponse");
		return response;
	}

	@Override
	public InputStream synthesize(String text) {

		InputStream inputStream;

		/* If you are unsure of this code,
		 * use the Translator class to automatically detect based off
		 * of either text from your language or your system settings.
		 */
		Synthesizer synth = new Synthesizer(language);

		try {

			inputStream = synth.getMP3Data(text);

		} catch (Exception e) {

			System.out.println("ERROR: " + e.getMessage());

			inputStream = null;

		}

		return inputStream;

	}

}
