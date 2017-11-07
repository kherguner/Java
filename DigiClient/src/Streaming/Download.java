package Streaming;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download {
	String url = "";

	public Download(String url) throws IOException {
		this.url = url;
		httpGet();
	}

	public void httpGet() throws IOException {
		int bytesRead = -1;
		byte[] buffer = new byte[4096];
		URL obj = new URL(this.url);
		System.out.println("Request:" + obj.toString());
		HttpURLConnection httpConn = (HttpURLConnection) obj.openConnection();
		httpConn.setDoOutput(true);
		int responseCode = httpConn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				fileName = this.url.substring(this.url.lastIndexOf("/") + 1, this.url.length());
			}

			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = FolderPattern.getLayer1() + "/" + fileName;
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
	}
}
