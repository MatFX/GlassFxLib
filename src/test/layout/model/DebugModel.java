package test.layout.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;

/**
 * collision helper
 * @author m.goerlich
 *
 */
public class DebugModel 
{
	
	protected static DebugModel debugModel = new DebugModel();
	
	private SimpleDoubleProperty xProperty = new SimpleDoubleProperty();
	
	private SimpleDoubleProperty yProperty = new SimpleDoubleProperty();
	
	private SimpleDoubleProperty wProperty = new SimpleDoubleProperty();
	
	private SimpleDoubleProperty hProperty = new SimpleDoubleProperty();
	
	
	private DebugModel()
	{
	}
	
	public static DebugModel getInstance()
	{
		return debugModel;
	}

	public SimpleDoubleProperty getxProperty() {
		return xProperty;
	}

	public SimpleDoubleProperty getyProperty() {
		return yProperty;
	}

	public SimpleDoubleProperty getwProperty() {
		return wProperty;
	}

	public SimpleDoubleProperty gethProperty() {
		return hProperty;
	}

	public void setBoundingBox(BoundingBox futureBoundsBox) 
	{
		xProperty.set(futureBoundsBox.getMinX());
		yProperty.set(futureBoundsBox.getMinY());
		wProperty.set(futureBoundsBox.getWidth());
		hProperty.set(futureBoundsBox.getHeight());
		
		
		
	}
	
	
	

}
