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
	
	public Image getImageRaw()
	{
		Image image = getImageFromOwnerImplementation();
		if(image == null)
			image = ImageLoader.getImageFromIconFolder(value);
		return image;
	}

	/**
	 * to override the method in the application
	 * @param value
	 * @return
	 */
	public Image getImageFromOwnerImplementation()
	{
		return null;
	}

	/**
	 * 
	 * @param newIconWidth
	 * @param newIconHeight
	 * @param b
	 * @param c
	 * @return
	 */
	public Image getScaledImageIcon(double newIconWidth, double newIconHeight, boolean preservationRatio, boolean smooth) 
	{
	
		Image scaledImage = getScaledImageFromOwnerImplementation(newIconWidth, newIconHeight, preservationRatio, smooth);
		if(scaledImage == null)
			scaledImage = ImageLoader.getImageFromIconFolder(value, newIconWidth, newIconHeight, false, true);
		return scaledImage;
	}

	/**
	 * to override the method in the application
	 * @param newIconWidth
	 * @param newIconHeight
	 * @param preservationRatio
	 * @param smooth
	 * @return
	 */
	public Image getScaledImageFromOwnerImplementation(double newIconWidth, double newIconHeight, boolean preservationRatio, boolean smooth) 
	{
		return null;
	}

}
