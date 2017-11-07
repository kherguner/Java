package Streaming;

public class BufferSynchronized implements Buffer {

	@Override
	public void setDownloadSeconds(int value) {
		//producer thread
	}

	@Override
	public int getDownloadSeconds() {
		//producer thread
		return 0;
	}

	@Override
	public void setPlayoutSeconds(int value) {
		//consumer thread
	}

	@Override
	public int getPlayoutSeconds() {
		//consumer thread
		return 0;
	}

	@Override
	public void setBuffer(int value) {
		//SET download video chunk - play video chunk 
	}

	@Override
	public int getBuffer(int value) {
		//GET download video chunk - play video chunk
		return 0;
	}

	@Override
	public void setBitrate(long bw) {
		//SET latest download segment bandwith
	}

	@Override
	public long getBitrate() {
		//GET latest download segment bandwith
		return 0;
	}

	@Override
	public void setIsDownload(boolean flag) {
		//buffer station observe
	}

	@Override
	public boolean getIsDownload() {
		//buffer station observe
		return true;
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

}
