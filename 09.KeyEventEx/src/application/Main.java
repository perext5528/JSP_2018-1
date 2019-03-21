package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	Label label = new Label("Hello");
	final int GAP = 10;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			root.setStyle("-fx-background-color:Yellow");

			label.setLayoutX(50);
			label.setLayoutY(50);
			root.getChildren().add(label);

			Scene scene = new Scene(root, 400, 400);

			/*
			 * scene.setOnKeyPressed(new EventHandler<KeyEvent>() { public void
			 * handle(KeyEvent e) { KeyCode code = e.getCode(); if (code == KeyCode.UP)
			 * label.setLayoutY(label.getLayoutY() - GAP); else if (code == KeyCode.DOWN)
			 * label.setLayoutY(label.getLayoutY() + GAP);
			 * 
			 * else if (code == KeyCode.LEFT) label.setLayoutX(label.getLayoutX() - GAP);
			 * else label.setLayoutX(label.getLayoutX() + GAP);
			 * 
			 * }; });
			 */
			
			//Lambda
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.UP)
					label.setLayoutY(label.getLayoutY() - GAP);
				else if (event.getCode() == KeyCode.DOWN)
					label.setLayoutY(label.getLayoutY() + GAP);

				else if (event.getCode() == KeyCode.LEFT)
					label.setLayoutX(label.getLayoutX() - GAP);
				else
					label.setLayoutX(label.getLayoutX() + GAP);
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
