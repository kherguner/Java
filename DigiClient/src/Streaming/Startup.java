package Streaming;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * mpd add720p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_720.mpd
 * mpd add1080p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_720.mpd
 * chunck add = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_2s_8000kbit/bunny_2s268.m4s
 */
public class Startup {
	public static FolderCreation folderCreation;
	public static Download request = new Download();
	public static MpdParser parser;
	public static String mpdAddress = "http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_1080.mpd";
	public static int hostId;
	public static String comment = "________";
	static ExecutorService exec = Executors.newCachedThreadPool();
	static int segmentId = 0;

	public static void main(String[] args) throws IOException {
		System.out.println(comment + "Dash video streaming version 1.1.0 created by KHergüner" + comment + "\n");
		hostId = Integer.parseInt(args[0]);
		folderCreation = new FolderCreation();
		eventManifestFile();
		dynamicSystem();

	}

	private static void eventManifestFile() {
		try {
			System.out.println(comment + "Mpd request send to CDN server" + comment);
			request.httpGet(mpdAddress);
			System.out.println(request.toString());
			System.out.println(comment + "Mpd parse running" + comment);
			parser = new MpdParser();
			System.out.println("Manifest parsed");
			System.out.println("CDN address: " + parser.cdnAddress);
		} catch (IOException e) {
			System.err.println("MPD indirme esnasında hata oluştu:\t" + e.getMessage());
		}
	}

	private static void dynamicSystem() {
		System.out.println(comment + "Producer assign system" + comment);
		exec.execute(new Producer(request, segmentId, parser));
		// exec.execute(new Consumer());
		exec.shutdown();
	}
}