package com.search.wiki;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WebFile {

	private Map<String, List<String>> responseHeader;
	private URL responseURL;
	private int responseCode;
	private String MIMEtype;
	private String charset;
	private Object content;
	private Long responseDate;
	private int responseLength;

	/****************************************************************************************/

	public WebFile(String _urlString, 
			int _connectTimeOut, //
			int _readTimeOut, //
			boolean _instanceFollowRedirects, //
			String _requestMethod, //
			String _requestProperty, //
			String _requestProperty2) {

		responseHeader = null;
		responseURL = null;
		responseCode = -1;
		MIMEtype = null;
		charset = null;
		content = null;
		responseLength = 0;
		responseDate = null;

		try {
			HttpURLConnection connection = createConnection(_urlString);
			connection = setupRequest(connection, //
					_connectTimeOut, //
					_readTimeOut, //
					_instanceFollowRedirects, //
					_requestMethod, //
					_requestProperty, //
					_requestProperty2); 
			connection.connect();
			getResponse(connection);
			getContent(connection);
			connection.disconnect();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/****************************************************************************************/

	private HttpURLConnection createConnection(String _urlString) {

		URL url;
		URLConnection urlConnection = null;

		try {
			url = new URL(_urlString);
			urlConnection = url.openConnection();

			if (urlConnection instanceof HttpsURLConnection) {
				throw new IllegalArgumentException(
						"URL protocol must be HTTP.");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (HttpURLConnection) urlConnection;

	}

	/****************************************************************************************/

	private HttpURLConnection setupRequest(HttpURLConnection _connection,
			int _connectTimeOut, //
			int _readTimeOut, //
			boolean _instanceFollowRedirects, //
			String _requestMethod, //
			String _requestProperty, //
			String _requestProperty2) {

		try {
			if (_connectTimeOut != 0) {
				_connection.setConnectTimeout(_connectTimeOut);
			}

			if (_readTimeOut != 0) {
				_connection.setReadTimeout(_readTimeOut);
			}

			_connection.setInstanceFollowRedirects(_instanceFollowRedirects);

			if (_requestMethod != null) {
				_connection.setRequestMethod(_requestMethod);
			}

			if (_requestProperty != null && _requestProperty2 != null) {
				_connection.setRequestProperty(_requestProperty,
						_requestProperty2);
			}

		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		return _connection;

	}

	/****************************************************************************************/

	private void getResponse(HttpURLConnection _connection) {

		try {
			responseHeader = _connection.getHeaderFields();
			responseCode = _connection.getResponseCode();
			responseURL = _connection.getURL();
			responseDate = _connection.getDate();
			responseLength = _connection.getContentLength();
			final String type = _connection.getContentType();

			if (type != null) {

				String[] parts = type.split(";");
				MIMEtype = parts[0].trim();

				for (int i = 1; i < parts.length && charset == null; i++) {

					final String t = parts[i].trim();
					final int index = t.toLowerCase().indexOf("charset=");

					if (index != -1) {
						charset = t.substring(index + 8);
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/****************************************************************************************/

	private void getContent(HttpURLConnection _connection) {

		try {

			InputStream stream = _connection.getErrorStream();

			if (stream != null) {
				content = readStream(responseLength, stream);
			} else if ((content = _connection.getContent()) != null
					&& content instanceof java.io.InputStream) {
				content = readStream(responseLength,
						(java.io.InputStream) content);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/****************************************************************************************/

	/** Read stream bytes and transcode. */
	private Object readStream(int length, InputStream stream) {

		int buflen;
		byte[] bytes = null;

		try {
			buflen = Math.max(1024, Math.max(length, stream.available()));
			byte[] buf = new byte[buflen];

			for (int nRead = stream.read(buf); nRead != -1; nRead = stream.read(buf)) {

				if (bytes == null) {
					bytes = buf;
					buf = new byte[buflen];
					continue;
				}

				final byte[] newBytes = new byte[bytes.length + nRead];
				System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
				System.arraycopy(buf, 0, newBytes, bytes.length, nRead);
				bytes = newBytes;

			}

			if (charset == null) {
				return bytes;
			}

			return new String(bytes, charset);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return bytes;
	}

	/****************************************************************************************/

	public Object getContent() {
		return content;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public Map<String, List<String>> getHeaderFields() {
		return responseHeader;
	}

	public URL getURL() {
		return responseURL;
	}

	public String getMIMEType() {
		return MIMEtype;
	}

	public Long getResposeDate() {
		return responseDate;
	}

	public int getResponseLength() {
		return responseLength;
	}

}
