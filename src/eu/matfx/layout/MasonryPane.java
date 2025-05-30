package eu.matfx.layout;

import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class MasonryPane extends Pane
{
	 private final double spacing;

     public MasonryPane(double spacing) {
         this.spacing = spacing;
         widthProperty().addListener((Observable obs) -> requestLayout());
     }

     @Override
     protected void layoutChildren() {
         double contentWidth = getWidth();
         double x = 0;
         double y = 0;
         double rowHeight = 0;
         boolean offsetRow = false;

         int i = 0;
         while (i < getChildren().size()) {
             x = offsetRow ? spacing * 2 : 0;
             rowHeight = 0;

             while (i < getChildren().size()) {
                 Node child = getChildren().get(i);
                 double w = child.prefWidth(-1);
                 double h = child.prefHeight(-1);

                 if (x + w > contentWidth) break;

                 child.resizeRelocate(x, y, w, h);

                 x += w + spacing;
                 rowHeight = Math.max(rowHeight, h);
                 i++;
             }

             y += rowHeight + spacing;
             offsetRow = !offsetRow;
         }
     }

     @Override
     protected double computePrefHeight(double width) {
         double maxY = 0;
         for (Node child : getChildren()) {
             maxY = Math.max(maxY, child.getLayoutY() + child.prefHeight(-1));
         }
         return maxY + spacing;
     }

}
