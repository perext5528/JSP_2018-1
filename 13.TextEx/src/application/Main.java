package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();

			Text text = new Text();
			text.setText("JavaFX");
			text.setX(50);
			text.setY(100);
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 78));
			text.setFill(Color.RED);
			text.setStroke(Color.BLUE);
			text.setStrikethrough(true);
			text.setUnderline(true);
			root.getChildren().add(text);

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
