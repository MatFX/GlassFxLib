package eu.matfx.tools;

import javafx.scene.image.Image;

/**
 * Image name in the string value variable.
 * @author Matthias
 *
 */
public class Image_Color_Component extends AColor_Component{

	public Image_Color_Component(String imageName) 
	{
		super(imageName);
	}
	
	/**
	 * Read out the image; over the default path or the path of the application
	 * @return the image for the view
	 */
	public Image getImageRaw()
	{
		Image image = getImageFromOwnerImplementation();
		if(image == null)
			image = ImageLoader.getImageFromIconFolder(value);
		return image;
	}

	/**
	 * To override the method in the application
	 * @param value
	 * @return image or null
	 */
	public Image getImageFromOwnerImplementation()
	{
		return null;
	}

	/**
	 * Get the scaled image; 
	 * <br>Reading from the path of the application or over the default path.
	 * @param newIconWidth
	 * @param newIconHeight
	 * @param b
	 * @param c
	 * @return scaled image for the view
	 */
	public Image getScaledImageIcon(double newIconWidth, double newIconHeight, boolean preservationRatio, boolean smooth) 
	{
	
		Image scaledImage = getScaledImageFromOwnerImplementation(newIconWidth, newIconHeight, preservationRatio, smooth);
		if(scaledImage == null)
			scaledImage = ImageLoader.getImageFromIconFolder(value, newIconWidth, newIconHeight, false, true);
		return scaledImage;
	}

	/**
	 * To override the method in the application.
	 * @param newIconWidth
	 * @param newIconHeight
	 * @param preservationRatio
	 * @param smooth
	 * @return scaled image or null
	 */
	public Image getScaledImageFromOwnerImplementation(double newIconWidth, double newIconHeight, boolean preservationRatio, boolean smooth) 
	{
		return null;
	}

}
