package test.layout;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;

import eu.matfx.component.sensor.MixedValueComponent;
import eu.matfx.tools.LayoutBox;
import eu.matfx.tools.UIToolBox;
import eu.matfx.tools.Value_Color_Component;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import test.layout.model.DebugModel;

public class LayoutContainer extends Application
{
	private OwnLayoutPane layoutPane = new OwnLayoutPane();
	
	private StackPane stackPane = new StackPane();
	
	private final int SENSOR_COUNT_START = 5;
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		
		layoutPane.setDebug(true);
		layoutPane.setStyle("-fx-background-color: #784545");
	
		
		BorderPane borderPane = new BorderPane();
		borderPane.setStyle("-fx-background-color: #787845");

		layoutPane.getChildren().add(getBuildedMixedComponent(0, 110, 128));
		layoutPane.getChildren().add(getBuildedMixedComponent(1, 98, 127));
		layoutPane.getChildren().add(getBuildedMixedComponent(2, 80, 131));
		layoutPane.getChildren().add(getBuildedMixedComponent(3, 121, 153));
		layoutPane.getChildren().add(getBuildedMixedComponent(4, 129, 122));
		

		if(layoutPane.getChildren().size() == 0)
		{
			for(int i = 0; i < SENSOR_COUNT_START; i++)
			{
			    layoutPane.getChildren().add(getBuildedMixedComponent(i));
			}
		}
		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setContent(layoutPane);
		
		//Overlay, to control the findig area for the ui component
		Pane overlay = new Pane();
		//overlay.setStyle("-fx-background-color: #FF000044");
		overlay.setMinWidth(150);
		overlay.setMinHeight(150);
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(Color.web("#00000000"));
		
		
		
		rectangle.setStroke(Color.CYAN);
		rectangle.setStrokeWidth(3);
		
		
		rectangle.xProperty().bind(DebugModel.getInstance().getxProperty());
		rectangle.yProperty().bind(DebugModel.getInstance().getyProperty());
		rectangle.widthProperty().bind(DebugModel.getInstance().getwProperty());
		rectangle.heightProperty().bind(DebugModel.getInstance().gethProperty());
		
		//rectangle.setX(50);
		//rectangle.setY(50);
		//rectangle.setWidth(150);
		//rectangle.setHeight(150);
		
		
		overlay.getChildren().add(rectangle);
		
		stackPane.getChildren().add(0, scrollPane);
		stackPane.getChildren().add(1, overlay);
		
		borderPane.setCenter(stackPane);
		
		Scene scene = new Scene(borderPane, 800, 600);
		scene.getStylesheets().add("style.css");
		
		primaryStage.setTitle("Sensor Panel");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    

		Button button = new Button("add UI Component");
		button.setOnAction(new EventHandler<ActionEvent> ()
		{

			@Override
			public void handle(ActionEvent p0) {
				
			    layoutPane.getChildren().add(getBuildedMixedComponent(layoutPane.getChildren().size()));
				
			}

		});
		
		Button removeButton = new Button("remove last UI Component");
		removeButton.setOnAction(new EventHandler<ActionEvent> ()
		{

			@Override
			public void handle(ActionEvent p0) {
				
			    layoutPane.getChildren().remove(layoutPane.getChildren().size()-1);
				
			}

		});
		
		Button saveNodes = new Button("save Coordinates from Nodes");
		saveNodes.setOnAction(new EventHandler<ActionEvent> ()
		{

			@Override
			public void handle(ActionEvent p0) {
				
				File file = new File("savedNodes.txt");
				
				 try (  PrintWriter pw = new PrintWriter( new DataOutputStream(new FileOutputStream(new File("data"))));)
				 {
					 for(Node node : layoutPane.getChildren())
					 {
						 MixedValueComponent mixedValueComponent = (MixedValueComponent)node;
						 
						 LayoutBox layoutBox = layoutPane.getLayoutBox(node);
						 pw.append("layoutPane.getChildren().add(getBuildedMixedComponent(");
						 pw.append(""+mixedValueComponent.getTopValueProperty().get().getValue()+", ");
						 pw.append(""+Math.round(layoutBox.getWidth())+", "); 
						 pw.append(""+Math.round(layoutBox.getHeight())+"));");
						 pw.append("\n");
					 }
					 pw.close();
				 } 
				 catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 } 
				 catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				 }
				
				
				
			}

		});
		
		
		VBox vBoxBackgroundControl = new VBox(10);
	    vBoxBackgroundControl.setPadding(new Insets(5,5,5,5));
	    vBoxBackgroundControl.getChildren().addAll(button, removeButton, UIToolBox.createVerticalSpacer(), saveNodes);
	    borderPane.setLeft(vBoxBackgroundControl);
		
	}
	
	private Node getBuildedMixedComponent(int index, int prefWidth, int prefHeight) {
		MixedValueComponent mixedValueComponent = (MixedValueComponent) getBuildedMixedComponent(index);
		mixedValueComponent.setPrefWidth(prefWidth);
		mixedValueComponent.setPrefHeight(prefHeight);
		mixedValueComponent.setMaxWidth(prefWidth);
		mixedValueComponent.setMinHeight(prefHeight);
		return mixedValueComponent;
	}

	private Node getBuildedMixedComponent(int index)
	{
		MixedValueComponent sensorPanel = (MixedValueComponent) getBuildedMixedComponent();
		Value_Color_Component valueComponent = new Value_Color_Component(""+index);
		valueComponent.setColor(Color.web("#d6d6c2"));
		sensorPanel.getTopValueProperty().set(valueComponent);
		return sensorPanel;
	}

	private Node getBuildedMixedComponent() {
		Random rand = new Random();
		MixedValueComponent sensorPanel = new MixedValueComponent();
		int prefWidth = rand.nextInt(71) + 80;
		int prefHeight = rand.nextInt(70) + 100;
		
		sensorPanel.setPrefWidth(prefWidth);
	    sensorPanel.setPrefHeight(prefHeight);
	    sensorPanel.setMaxWidth(prefWidth);
	    sensorPanel.setMinHeight(prefHeight);
	    sensorPanel.boundsInParentProperty().addListener(new ChangeListener<Bounds>(){

			@Override
			public void changed(ObservableValue<? extends Bounds> p0, Bounds p1, Bounds newBounds) {
				
				if(!newBounds.isEmpty())
				{
					//System.out.println("newBounds " + newBounds);
					double widthComponent = Math.round(newBounds.getWidth() * 10.0) / 10.0;
					double heightComponent = Math.round(newBounds.getHeight()* 10.0) / 10.0;
					
					DecimalFormat df = new DecimalFormat("0.0");
					Value_Color_Component valueComponent = new Value_Color_Component(df.format(widthComponent) + " x " + df.format(heightComponent));
					   valueComponent.setColor(Color.web("#d6d6c2"));
					sensorPanel.getValueProperty().set(valueComponent);
					//sind die Größen die zu Beginn dem Objekt zugewiesen wurden.
					Value_Color_Component bottomValueComponent = new Value_Color_Component(sensorPanel.getLayoutBounds().getWidth() + " x " + sensorPanel.getLayoutBounds().getHeight());
					bottomValueComponent.setColor(Color.web("#d6d6c2"));
					sensorPanel.getBottomValueProperty().set(bottomValueComponent);
				}
			}
	    	
	    });
		return sensorPanel;
	}
	

}
