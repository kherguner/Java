package Streaming;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Producer implements Runnable {
	Download request;
	MpdParser parser;
	Buffer sharedBuffer;
	Map<Integer, ArrayList<String>> segmentChunk;
	Map<Long, Long> bwList;
	PrintWriter monitorLog;
	static int segmentId, segCount;
	String cdnAddress, urlPattern;
	boolean flag = true;
	long slctRep, lastSegBw, minBw;

	public Producer(Download request, int segmentId, MpdParser parser, Buffer shareBuffer, PrintWriter monitorLog) {
		this.request = request;
		this.segmentId = segmentId;
		this.cdnAddress = parser.cdnAddress;
		this.segmentChunk = parser.content;
		this.segCount = parser.chunkSize;
		this.sharedBuffer = shareBuffer;
		this.bwList = parser.bwList;
		this.monitorLog = monitorLog;
	}

	@Override
	public void run() {
		try {
			while (this.sharedBuffer.getIsDownload()) {
				this.sharedBuffer.setDownloadChunk(segmentId);
				adaptationAlg();
				urlPattern = cdnAddress + this.segmentChunk.get((int) this.slctRep).get(segmentId - 1);
				this.request.httpGet(urlPattern);
				println("SegmentId:\t" + segmentId + "\tReguest:\t" + this.request.toString());
				segmentId++;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	private void adaptationAlg() {
		this.lastSegBw = this.request.bitrate;
		this.bwList = sortByValues(this.bwList);
		Set<Long> keys = this.bwList.keySet();
		if (segmentId == 1) {
			for (Long i : keys) {
				this.slctRep = this.bwList.get(i);
				minBw = this.slctRep;
				break;
			}
		} else {
			for (Long i : keys) {
				long value = this.bwList.get(i);
				if (this.lastSegBw >= value) {
					this.slctRep = value;
				}
				if (this.lastSegBw <= minBw) {
					this.slctRep = this.bwList.get(i);
					break;
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private HashMap<Long,Long> sortByValues(Map<Long, Long> sortBwList) {
		LinkedList list = new LinkedList(sortBwList.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	public void println(String data) {
		this.monitorLog.println(data);
		this.monitorLog.flush();
	}
}