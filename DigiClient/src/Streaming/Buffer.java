package Streaming;

public interface Buffer {
	public void setDownloadChunk(int value) throws InterruptedException;

	public int getDownloadChunk() throws InterruptedException;

	public void setPlayoutSeconds(int value) throws InterruptedException;

	public int getPlayoutSeconds() throws InterruptedException;

	public int getBuffer() throws InterruptedException;

	public void setBitrate(long bw) throws InterruptedException;

	public long getBitrate() throws InterruptedException;

	public void setIsDownload(boolean flag) throws InterruptedException;
	
	public boolean getIsDownload() throws InterruptedException;
	
	public void setIsPlayer(boolean flag) throws InterruptedException;
	
	public boolean getIsPlayer() throws InterruptedException;

	public int getMaxBuffer() throws InterruptedException;

	public int getCriticalPosition() throws InterruptedException;

	public int getStartup() throws InterruptedException;

}
