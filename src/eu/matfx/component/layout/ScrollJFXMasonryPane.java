package eu.matfx.component.layout;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.scene.control.ScrollPane;

public class ScrollJFXMasonryPane extends ScrollPane
{
	private JFXMasonryPane masonryPane = new JFXMasonryPane();
	
	public ScrollJFXMasonryPane()
	{
		super();
	    this.setContent(masonryPane);
	    this.setFitToWidth(true);  // passt die Breite an
	    this.setPannable(true);    // erlaubt Ziehen mit Maus
	}
	
	public JFXMasonryPane getJFXMasonryPane()
	{
		return masonryPane;
	}

}
