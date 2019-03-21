package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

//MouseEvent 일회성 사용
public class Main extends Application {
	Label label = new Label("Hello");

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			root.setStyle("-fx-background-color:Yellow");
			//JavaFX에서 CSS를 사용하는 경우 -fx-를 항상 포함해야 함

			label.setLayoutX(50);
			label.setLayoutY(50);
			root.getChildren().add(label);

			Scene scene = new Scene(root, 400, 400);
			scene.setOnMouseClicked(event -> {
				label.setLayoutX(event.getX());
				label.setLayoutY(event.getY());
			});
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
