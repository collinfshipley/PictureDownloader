import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

/**
 * Single threaded program that downloads all image files at a URL, converts those
 * images to gray scale, and then saves them to the disk
 * @author Rachel Paglia
 *
 */
public class singleThreadImageDownloader {

	private static final String webURL = "http://elvis.rowan.edu/~mckeep82/ccpsu17/Astronomy/";
	
	/**
	 * Main method, reads URL for images
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = new URL(webURL);
			URLConnection connection = url.openConnection();
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader buffRead = new BufferedReader(inputReader);
			
			String line;
			
			while((line = buffRead.readLine()) != null) {
				int startPoint = line.indexOf("<a href=\"");
				int endPoint = line.indexOf("\">", startPoint);
				if(startPoint >= 0 && endPoint >=0) {
					String subStr = line.substring(startPoint + 9, endPoint);
					int len = subStr.length();
					if(subStr.length() < 4)
						continue;
					if(subStr.substring(len-4, len).compareTo(".jpg") == 0){
						downloadImage(webURL, subStr);
						System.out.println(subStr + "\t\t\tDONE");
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Downloads the image, calls to convert to gray scale, saves image to hard drive
	 * @param URL
	 * @param img
	 */
	private static void downloadImage(String URL, String img) {
		  BufferedImage image = null;
		  
	        try {
	            if (!(img.startsWith("http"))) {
	                URL = URL + img;
	            } else {
	                URL = img;
	            }
	            img = img.substring(img.lastIndexOf("/") + 1);
	            String imgPath = null;
	            imgPath = "/home/rcp0795/imagesDownloaded/" + img + "";
	            URL imageUrl = new URL(URL);
	            image = ImageIO.read(imageUrl);
	            convertGrayscale(image);
	            
	            if (image != null) {
	                File file = new File(imgPath);
	                ImageIO.write(image, "jpg", file);
	            }
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}
	
	/**
	 * Converts image to gray scale and returns the new image
	 * @param img
	 * @return
	 */
	private static void convertGrayscale(BufferedImage img) {
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
		
	}
}
