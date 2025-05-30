package test.layout;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MasonryDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MasonryPane masonryPane = new MasonryPane(10); // Abstand 10px

        // ✅ Kacheln mit zufälliger Größe und Farbe
        for (int i = 0; i < 40; i++) {
            double width = 80 + Math.random() * 80;
            double height = 60 + Math.random() * 60;

            Tile tile = new Tile(width, height);
            masonryPane.getChildren().add(tile);
        }

        ScrollPane scrollPane = new ScrollPane(masonryPane);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(masonryPane, 800, 600);
        primaryStage.setTitle("JavaFX Masonry Layout (sichtbar!)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 🔧 Custom Pane für Masonry Layout
    static class MasonryPane extends Pane {
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
            layoutChildren();
            double maxY = 0;
            for (Node child : getChildren()) {
                maxY = Math.max(maxY, child.getLayoutY() + child.prefHeight(-1));
            }
            return maxY + spacing;
        }
    }

    // 🧱 Visuelle Kachel-Komponente (mit Region)
    static class Tile extends Region {
        public Tile(double width, double height) {
            setPrefSize(width, height);
            setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            setStyle("-fx-background-color: hsb(" + (Math.random() * 360) + ", 60%, 80%);"
                    + "-fx-background-radius: 10;"
                    + "-fx-border-radius: 10;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1;");
        }
    }
}
