import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Program to download all image files at a given url and save them to the disk
 * 
 * @author Collin Shipley
 *
 */
public class PictureDownloader {
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String source = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/";
			URL url = new URL(source);
			InputStream input = url.openStream();
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
			String line;
			ConcurrentLinkedQueue<Pair<BufferedImage, String>> imageQueue = new ConcurrentLinkedQueue<Pair<BufferedImage, String>>();
			int tasks = 0;
			
			ThreadPoolExecutor exec = new ThreadPoolExecutor(6, 6, 0, TimeUnit.MILLISECONDS, new BlockingStack<Runnable>());
			
			while((line = inputReader.readLine()) != null) {
				int start = line.indexOf("<a href=\"");
				int end	= line.indexOf("\">", start);
				if(start >= 0 && end >=0) {
					String subStr = line.substring(start + 9, end);
					int len = subStr.length();
					if(subStr.length() < 4)
						continue;
					if(subStr.substring(len-4, len).compareTo(".jpg") == 0){
						DownloadRunner task = new DownloadRunner(source + subStr, imageQueue);
						exec.execute(task);
						tasks++;
					}
				}
			}
			
			//Create a new task for writing whenever we finish a download
			while(tasks > 0) {
				if(!imageQueue.isEmpty()) {
					Pair<BufferedImage, String> pair = imageQueue.remove();					
					String filename = pair.right.substring(source.length(), pair.right.length());
					WriteRunner runner = new WriteRunner(pair.left, filename);
					exec.execute(runner);
					tasks--;
				}
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			exec.shutdown();
		}
		catch(IOException e) {
			System.err.println("IO error!");
			e.printStackTrace();
		}
	}
}
