package Streaming;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MpdParser {
	Scanner read;
	ArrayList<Integer> bandwith = new ArrayList<>();
	ArrayList<String> chunkUrl = new ArrayList<>();
	Map<Integer, ArrayList<String>> content = new HashMap<>();
	Map<Long, Long> bwList = new HashMap<>();
	String cdnAddress = "";
	static int chunkSize = 0, key = 1;

	public MpdParser() {
		readProcess();
		contentGenerate();
		generateBwList();
	}

	private void generateBwList() {
		Set<Integer> keys = this.content.keySet();
		for (Integer i : keys) {
			this.bwList.put((long) key++, (long) i);
		}
	}

	private void readProcess() {
		try {
			read = new Scanner(new File(FolderPattern.getLayer1() + "/bunny_ibmff_1080.mpd"));
			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.startsWith("<BaseURL>")) {
					this.cdnAddress = line.replace("<BaseURL>", "").replace("</BaseURL>", "").trim();
				}
				if (line.startsWith("<Representation")) {
					String[] a = line.split("\"");
					int kbit = Integer.parseInt(a[a.length - 2]) / 1000;
					bandwith.add(kbit);
				}
				if (line.startsWith("<SegmentURL")) {
					chunkSize++;
					String a = line.replace("<SegmentURL media=\"", "").replace("\"/>", "").trim();
					chunkUrl.add(a);
				}
			}
			chunkSize = chunkSize / bandwith.size();
		} catch (FileNotFoundException e) {
			System.err.println("Mpd parse error");
		}
	}

	private void contentGenerate() {
		int count = 0;
		int key;
		for (int j = 0; j < bandwith.size(); j++) {
			key = bandwith.get(j);
			for (int i = count; i < (count + chunkSize); i++) {
				String value = chunkUrl.get(i);
				if (!content.containsKey(key)) {
					ArrayList<String> swap = new ArrayList<>();
					swap.add(value);
					content.put(key, swap);
				} else {
					content.get(key).add(value);
				}
			}
			count += chunkSize;
		}
	}

	@Override
	public String toString() {
		return "MpdParser [content=" + content + "]";
	}
}
