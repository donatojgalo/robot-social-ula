package com.search.wiki;
import java.applet.AudioClip;
import java.awt.Image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FetchData {

	private WebFile web;

	private int connectTimeOut = 20000;
	private int readTimeOut = 20000;
	private boolean instanceFollowRedirects = true;
	private String requestMethod = null;
	private String requestProperty = "User-agent";
	private String requestProperty2 = "spider";

	/****************************************************************************************/

	public FetchData() {
		// empty
	}

	/****************************************************************************************/

	public String getWebPage(String _url) {

		web = new WebFile(_url, //
				connectTimeOut, //
				readTimeOut, //
				instanceFollowRedirects, //
				requestMethod, //
				requestMethod, //
				requestProperty2);

		String MIME = web.getMIMEType();
		Object content = web.getContent();

		if (MIME.equals("text/html") && content instanceof String) {
			return (String) content;
		}

		return null;
	}

	/****************************************************************************************/

	public String getTextFromWebPage(String _url) {

		//return Jsoup.parse(getWebPage(_url)).text();
		//return Jsoup.parse(getWebPage(_url)).body().attr("a").fir;

		Document doc = Jsoup.parse(getWebPage(_url));
		//		Element contentDiv = doc.select("div[id=content]").first();
		//		return contentDiv.text();

		Elements paragraphs = doc.select(".mw-content-ltr p");
		Element firstParagraph = paragraphs.first();
		return firstParagraph.text();

		//		    Element lastParagraph = paragraphs.last();
		//		    Element p;
		//		    int i=1;
		//		    p=firstParagraph;
		//		    System.out.println(p.text());
		//		    while (p!=lastParagraph){
		//		        p=paragraphs.get(i);
		//		        System.out.println(p.text());
		//		        i++;
		//		    }

	}

	/****************************************************************************************/

	public Image getImageFromWebPage(String _url) {

		web = new WebFile(_url, //
				connectTimeOut, //
				readTimeOut, //
				instanceFollowRedirects, //
				requestMethod, //
				requestMethod, //
				requestProperty2);

		String MIME = web.getMIMEType();
		Object content = web.getContent();

		if (MIME.startsWith("image") && content instanceof Image) {
			return (Image) content;
		}

		return null;
	}

	/****************************************************************************************/

	public AudioClip getAudioFromWebPage(String _url) {

		web = new WebFile(_url, //
				connectTimeOut, //
				readTimeOut, //
				instanceFollowRedirects, //
				requestMethod, //
				requestMethod, //
				requestProperty2);

		String MIME = web.getMIMEType();
		Object content = web.getContent();

		if (MIME.startsWith("audio") && content instanceof AudioClip) {
			return (AudioClip) content;
		}

		return null;
	}

	/****************************************************************************************/

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public boolean isInstanceFollowRedirects() {
		return instanceFollowRedirects;
	}

	public void setInstanceFollowRedirects(boolean instanceFollowRedirects) {
		this.instanceFollowRedirects = instanceFollowRedirects;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestProperty() {
		return requestProperty;
	}

	public void setRequestProperty(String requestProperty) {
		this.requestProperty = requestProperty;
	}

	public String getRequestProperty2() {
		return requestProperty2;
	}

	public void setRequestProperty2(String requestProperty2) {
		this.requestProperty2 = requestProperty2;
	}

}
