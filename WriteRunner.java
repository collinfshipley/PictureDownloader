import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A runnable to write an image to a file on the disk.
 * 
 * @author Collin Shipley
 *
 */
public class WriteRunner implements Runnable {

	BufferedImage image;
	String filename;
	
	/**
	 * Constructor
	 * 
	 * @param image		image file to be written to the disk
	 * @param filename	name of the file to be written
	 */
	WriteRunner(BufferedImage image, String filename){
		this.image = image;
		this.filename = filename;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			GrayScaleConverter.convertGrayscale(image); //added
			String imgPath = "/home/rcp0795/images/" + filename + "";
			File output = new File(imgPath);
			String extension = filename.substring(filename.indexOf('.')+1, filename.length());
			ImageIO.write(image, extension, output);
		}
		catch(IOException e) {
			System.err.println("File write error!");
			e.printStackTrace();
		}
	}

}
