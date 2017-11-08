package Streaming;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Producer implements Runnable {
	Download request;
	MpdParser parser;
	Map<Integer, ArrayList<String>> segmentChunk;
	int segmentId, segCount;
	String cdnAddress, urlPattern;
	boolean flag = true;

	public Producer(Download request, int segmentId, MpdParser parser) {
		this.request = request;
		this.segmentId = segmentId;
		this.cdnAddress = parser.cdnAddress;
		this.segmentChunk = parser.content;
		this.segCount = parser.chunkSize;
	}

	@Override
	public void run() {
		while (flag) {
			if (this.segmentId < this.segCount) {
				try {
					urlPattern = cdnAddress + this.segmentChunk.get(4219).get(segmentId);
					this.request.httpGet(urlPattern);
					System.out.println(this.request.toString());
					segmentId++;
				} catch (IOException e) {
					System.err.println("Producer parts error");
					e.printStackTrace();
				}
			} else {
				flag = false;
			}
		}
	}
}