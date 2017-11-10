package Streaming;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download {
	String url = "";
	long startReqTime, bitrate, contentByte;
	double finishReqTime;

	public void httpGet(String url) throws IOException, InterruptedException {
		startReqTime = System.currentTimeMillis();
		this.url = url;
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
			contentByte = contentLength;

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
			finishReqTime = (System.currentTimeMillis() - startReqTime) / 1000.0;
			long byteToKbps = (long) ((contentLength * 8) / 1000);
			bitrate = (long) (byteToKbps / finishReqTime);
			outputStream.close();
			inputStream.close();
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
	}

	@Override
	public String toString() {
		return "Download info : [ video total byte=" + contentByte + ", bitrate=" + bitrate + ", Request finish Time="
				+ finishReqTime + "]\n";
	}
}
