package Streaming;

public class Consumer implements Runnable {
	Buffer sharedBuffer;
	static int playSeconds = 0;

	public Consumer(Buffer sharedBuffer) {
		this.sharedBuffer = sharedBuffer;
	}

	@Override
	public void run() {
		try {
			while (true) {
				playSeconds += 1;
				this.sharedBuffer.setPlayoutSeconds(playSeconds);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			System.err.println("Consumer detect error");
			e.printStackTrace();
		}
	}
}
