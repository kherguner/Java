package Streaming;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * mpd add720p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_720.mpd
 * mpd add1080p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_1080.mpd
 * chunck add = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_2s_8000kbit/bunny_2s268.m4s
 */
public class Startup {
	public static FolderCreation folderCreation;
	public static Download request = new Download();
	public static ExecutorService exec = Executors.newCachedThreadPool();
	public static MpdParser parser;
	public static Buffer shareBuffer;
	public static PrintWriter bufferLog, monitorLog;
	public static String cdnAddress;
	public static int segmentId = 1, hostId;

	public static void main(String[] args) throws IOException {
		System.out.println("Dash video streaming version 1.1.1 created by KHergüner \n");
		hostId = Integer.parseInt(args[0]);
		cdnAddress = args[1];
		init();
		startup();
	}

	private static void init() throws FileNotFoundException {
		folderCreation = new FolderCreation();
		bufferLog = new PrintWriter(new File(FolderPattern.getLayer2()) + "/buffer.txt");
		monitorLog = new PrintWriter(new File(FolderPattern.getLayer2() + "/monitor.txt"));
		shareBuffer = new BufferSynchronized(bufferLog);
	}

	private static void startup() throws FileNotFoundException {
		eventManifestFile();
		dynamicSystem();
	}

	private static void eventManifestFile() {
		try {
			System.out.println("HostId:" + hostId + " CDN request for MPD  ");
			request.httpGet(cdnAddress);
			System.out.println(request.toString());
			System.out.println("Mpd parse running");
			parser = new MpdParser();
		} catch (IOException | InterruptedException e) {
			System.err.println("MPD dosyasını indirme esnasında hata oluştu:\t" + e.getMessage());
		}
	}

	private static void dynamicSystem() throws FileNotFoundException {
		exec.execute(new Producer(request, segmentId, parser, shareBuffer, monitorLog));
		exec.execute(new Consumer(shareBuffer));
		exec.shutdown();
	}
}