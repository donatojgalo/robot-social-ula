package com.search.wiki;

import java.applet.AudioClip;
import java.awt.Image;

public class HtmlUtils {

	/****************************************************************************************/

	public static String getWebPage(String _url) {
		FetchData data = new FetchData();
		return data.getWebPage(_url);
	}

	/****************************************************************************************/

	public static String getTextFromWebPage(String _url) {
		FetchData data = new FetchData();
		return data.getTextFromWebPage(_url);
	}

	/****************************************************************************************/

	public static String getTextFromWikipedia(String _search) {

		FetchData data = new FetchData();
		String[] split = _search.split(" ");

		if (split.length > 1) {
			_search = _search.replaceAll(" ", "_");
		}

		String url = "http://es.wikipedia.org/wiki/"+_search;

		String result = data.getTextFromWebPage(url);

		if (result.substring(0, 27).equals("Esta página ha sido borrada")) {
			return "No se ha encontrado ninguna información sobre lo que has preguntado";
		}

		return result;

	}

	/****************************************************************************************/

	public static Image getImageFromWebPage(String _url) {
		FetchData data = new FetchData();
		return data.getImageFromWebPage(_url);
	}

	/****************************************************************************************/

	public static AudioClip getAudioFromWebPage(String _url) {
		FetchData data = new FetchData();
		return data.getAudioFromWebPage(_url);
	}

	/****************************************************************************************/

	/*public static void main(String[] args) {

		//System.out.println(HtmlUtils.getWebPage("http://es.wikipedia.org/wiki/Sim%C3%B3n_Bol%C3%ADvar"));
		//System.out.println(HtmlUtils.getTextFromWebPage("http://es.wikipedia.org/wiki/Sim%C3%B3n_Bol%C3%ADvar"));
		System.out.println(HtmlUtils.getTextFromWikipedia("Hugo Chávez"));

	}*/

}
