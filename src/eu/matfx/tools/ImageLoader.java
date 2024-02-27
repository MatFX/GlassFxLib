package eu.matfx.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;

/**
 * Load images from the definied resource folder; only needed for the examples.
 * @author m.goerlich
 */
public class ImageLoader 
{
	public static String SUFFIX_FILE = ".png";
	
	/**
	 * Subfolders, starting from the project folder.
	 */
	public static String ICONS = "/resources/icon";
	
	public static InputStream getResourceStream(String pkname, String fname) throws FileNotFoundException
	{
		String resname = "" + pkname + "/" + fname;
		File file = new File("");
		
		file = new File(file.getAbsoluteFile() + resname);
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
		return inputStream;
	}
	
	/**
	 * pure image from the folder
	 * @param fileName
	 * @return
	 */
	public static Image getImageFromIconFolder(String fileName)
	{
		Image image = null;
	
		fileName = checkSuffixFromImageFilename(fileName);
		
		try
		{
			InputStream ins = ImageLoader.getResourceStream(ICONS, fileName);
			image = new Image(ins);
			ins.close();
		}
		catch(Exception e)
		{
			//TODO logger
			return getDefaultImageFromIconFolder();
		}
		return image;
	}

	/**
	 * manipulated image from the folder
	 * @param fileName
	 * @param newW
	 * @param newH
	 * @param preservationRatio
	 * @param smooth
	 * @return
	 */
	public static Image getImageFromIconFolder(String fileName, double newW, double newH, boolean preservationRatio, boolean smooth)
	{
		Image image = null;
		
		fileName = checkSuffixFromImageFilename(fileName);
		
		try
		{
			InputStream ins = ImageLoader.getResourceStream(ICONS, fileName);
			image = new Image(ins, newW, newH, preservationRatio, smooth);
			ins.close();
		}
		catch(Exception e)
		{
			//TODO logger
			return getDefaultImageFromIconFolder();
		}
		return image;
	}
	
	
	
	private static Image getDefaultImageFromIconFolder() 
	{
		Image image = new Image("/eu/matfx/tools/spacer.png", 1, 1, false, false);
		return image;
	}

	protected static String checkSuffixFromImageFilename(String fileName)
	{
		if(!fileName.contains(ImageLoader.SUFFIX_FILE))
			fileName = fileName + ImageLoader.SUFFIX_FILE;
		
		return fileName;
	}


}
