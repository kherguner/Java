package Streaming;

public class FolderPattern {
	private static final String layer4 = "./Clients";
	private static final String layer3 = layer4 + "/" + Startup.hostId;
	private static final String layer2 = layer3 + "/chunk";
	private static final String layer1 = layer2 + "/2";

	public static String getLayer4() {
		return layer4;
	}

	public static String getLayer3() {
		return layer3;
	}

	public static String getLayer2() {
		return layer2;
	}

	public static String getLayer1() {
		return layer1;
	}
}
