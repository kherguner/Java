package Streaming;

import java.io.PrintWriter;
import java.util.Date;

public class BufferSynchronized implements Buffer {
	PrintWriter bufferLog;
	boolean occupied = false, isDownload = true, isPlay = false;
	int videoTotalSeconds = MpdParser.chunkSize * 2;
	int segmentTime, playTime, bw;

	public BufferSynchronized(PrintWriter bufferLog) {
		this.bufferLog = bufferLog;
	}

	@Override
	public synchronized void setDownloadChunk(int value) throws InterruptedException {
		// producer thread
		segmentTime = value * 2;

		if (getBuffer() >= getMaxBuffer()) {
			occupied = true;
		}

		while (occupied) {
			printfile("Buffer full...");
			System.out.println("Producer waiting");
			wait();
		}

		if (getStartup() == getDownloadChunk()) {
			setIsPlayer(true);
		}

		if (getDownloadChunk() == videoTotalSeconds) {
			occupied = true;
			isDownload = false;
		}

		this.printfile("Producer:\t" + toString());
		notifyAll();
	}

	@Override
	public int getDownloadChunk() {
		return segmentTime;
	}

	@Override
	public synchronized void setPlayoutSeconds(int value) throws InterruptedException {
		while (!getIsPlayer() ) {
			printfile("Consumer waitting");
			System.out.println("Consumer waitting");
			wait();
		}
		playTime = value;
		if (getBuffer() < getMaxBuffer()) {
			occupied = false;
		}
		if (!isDownload || getBuffer() == 0) {
			setIsPlayer(false);
		}
		this.printfile("Consumer:\t" + toString());
		notifyAll();
	}

	@Override
	public int getPlayoutSeconds() {
		return playTime;
	}

	@Override
	public void setIsPlayer(boolean flag) throws InterruptedException {
		isPlay = flag;
	}

	@Override
	public boolean getIsPlayer() throws InterruptedException {
		return isPlay;
	}

	@Override
	public int getBuffer() {
		return getDownloadChunk() - getPlayoutSeconds();
	}

	@Override
	public synchronized void setBitrate(long bw) {
		this.bw = (int) bw;
	}

	@Override
	public long getBitrate() {
		return bw;
	}

	@Override
	public void setIsDownload(boolean flag) {
		isDownload = flag;
	}

	@Override
	public boolean getIsDownload() {
		return isDownload;
	}

	@Override
	public int getMaxBuffer() {
		return 30;
	}

	@Override
	public int getCriticalPosition() {
		return 4;
	}

	@Override
	public int getStartup() {
		return 8;
	}

	private synchronized void printfile(String data) {
		this.bufferLog.println(data);
		this.bufferLog.flush();
	}

	@Override
	public String toString() {
		return "[Download=" + getDownloadChunk() + ", Playout=" + getPlayoutSeconds() + ", Buffer=" + getBuffer()
				+ "]	";
	}
}
