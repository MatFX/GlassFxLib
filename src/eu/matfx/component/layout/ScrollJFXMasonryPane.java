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
	    this.setId("scroll-jfxmasonrypane");
	}
	
	public JFXMasonryPane getJFXMasonryPane()
	{
		return masonryPane;
	}

}
