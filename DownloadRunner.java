import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.imageio.ImageIO;

/**
 * A runnable to download an image from a given url and place it in a queue for further processing
 * 
 * @author Collin Shipley
 *
 */
public class DownloadRunner implements Runnable{
	String url;
	ConcurrentLinkedQueue<Pair<BufferedImage, String>> imageQueue;
	
	/**
	 * Constructor
	 * 
	 * @param url			the url to find the image
	 * @param imageQueue	a queue to store the image in after downloading
	 */
	DownloadRunner(String url, ConcurrentLinkedQueue<Pair<BufferedImage, String>> imageQueue){
		this.url = url;
		this.imageQueue = imageQueue;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			URL source = new URL(url);
			BufferedImage image = ImageIO.read(source);
			imageQueue.add(new Pair<BufferedImage, String>(image, url));
		}
		catch(IOException e) {
			System.err.println("Network error!");
			e.printStackTrace();
		}
	}

}
