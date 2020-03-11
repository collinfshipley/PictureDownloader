import java.awt.image.BufferedImage;

/**
 * Class that converts an image to gray scale
 * @author Rachel Paglia
 *
 */
public class GrayScaleConverter {

	/**
	 * Converts image to gray scale and returns the new image
	 * @param img
	 * @return
	 */
	public static BufferedImage convertGrayscale(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		//goes through each pixel one by one to convert to appropriate gray color
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				int pixel = img.getRGB(j, i);
				int alpha = (pixel>>24)&0xff;
				int red = (pixel>>16)&0xff;
				int green = (pixel>>8)&0xff;
				int blue = pixel&0xff;
				
				int average = (red+green+blue)/3;
				
				pixel = (alpha<<24) | (average<<16) | (average<<8) | average;
				
				img.setRGB(j, i, pixel);
			}
		}
		
		return img;
	}
}
