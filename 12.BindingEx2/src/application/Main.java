package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = new AnchorPane();

			Circle c = new Circle();
			c.setCenterX(150);
			c.setCenterY(100);
			c.setRadius(50);
			c.setFill(Color.ORANGE);
			c.setStroke(Color.BLACK);
			c.centerXProperty().bind(Bindings.divide(root.widthProperty(), 2));
			c.centerYProperty().bind(Bindings.divide(root.heightProperty(), 2));
			c.radiusProperty().bind(Bindings.divide(root.heightProperty(), 4));

			root.getChildren().add(c);

			Scene scene = new Scene(root, 400, 400);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
