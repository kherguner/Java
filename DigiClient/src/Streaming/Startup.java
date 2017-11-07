package Streaming;

import java.io.IOException;

/*
 * mpd add720p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_720.mpd
 * mpd add1080p = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_720.mpd
 * chunck add = http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_2s_8000kbit/bunny_2s268.m4s
 */
public class Startup {
	public static FolderCreation folderCreation;
	public static Download request;
	public static String mpdAddress = "http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_1080.mpd";
	public static int hostId;
	public static String comment = "________";

	public static void main(String[] args) throws IOException {
		System.out.println(comment + "Dash video streaming version 1.0.0 created by KHergüner" + comment + "\n\n");
		hostId = Integer.parseInt(args[0]);
		folderCreation = new FolderCreation();
		eventManifestFile();
	}

	private static void eventManifestFile() {
		try {
			System.out.println(comment + "Mpd request send to CDN server" + comment);
			request = new Download(mpdAddress);
			System.out.println(comment + "Mpd parse running" + comment);
			MpdParser execute = new MpdParser();
			System.out.println("Manifest parsed");
			System.out.println("CDN address: " + execute.cdnAddress);
		} catch (IOException e) {
			System.err.println("MPD indirme esnasında hata oluştu:\t" + e.getMessage());
		}
	}
}