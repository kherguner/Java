package Streaming;

import java.io.File;

public class FolderCreation {
	FolderPattern pattern;

	public FolderCreation() {
		run();
	}

	private void run() {
		layer4Folder();
		layer3Folder();
		layer2Folder();
		layer1Folder();
	}

	private void layer4Folder() {
		File dir = new File(pattern.getLayer4());
		controlFolder(dir);
	}

	private void layer3Folder() {
		File dir = new File(pattern.getLayer3());
		controlFolder(dir);
	}

	private void layer2Folder() {
		File dir = new File(pattern.getLayer2());
		controlFolder(dir);
	}

	private void layer1Folder() {
		File dir = new File(pattern.getLayer1());
		controlFolder(dir);
	}

	public void controlFolder(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
}
