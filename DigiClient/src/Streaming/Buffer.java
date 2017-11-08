package Streaming;

public interface Buffer {
	public void setDownloadChunk(int value);

	public int getDownloadChunk();

	public void setPlayoutSeconds(int value);

	public int getPlayoutSeconds();

	public void setBuffer(int value);

	public int getBuffer(int value);

	public void setBitrate(long bw);

	public long getBitrate();

	public void setIsDownload(boolean flag);

	public boolean getIsDownload();

	public int getMaxBuffer();

	public int getCriticalPosition();

	public int getStartup();

}
